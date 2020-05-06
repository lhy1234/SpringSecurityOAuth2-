package com.farinfo.controller;

import com.farinfo.api.result.Result;
import com.farinfo.utils.FileUtil;
import com.github.tobato.fastdfs.domain.fdfs.FileInfo;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.domain.upload.FastImageFile;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * Created by: 李浩洋 on 2020-04-27
 **/
@Api(tags = "文件上传")
@Slf4j
@RestController
@RequestMapping("/oss")
public class UploadFileController {

    @Autowired
    protected FastFileStorageClient storageClient;
    @Autowired
    private ThumbImageConfig thumbImageConfig;

    /**
     * 文件上传，并设置MetaData
     */
    @ApiOperation("文件上传，并设置MetaData")
    @PostMapping("fileupload1")
    public Result fileupload1(@RequestParam("fileName") MultipartFile file) throws Exception{

        log.debug("-------> 文件上传，并设置MetaData --------> ");
        //元数据
        Set<MetaData> metaData = new HashSet<>();
        metaData.add(new MetaData("author","李浩洋"));
        metaData.add(new MetaData("createDate","2020-04-28"));
        //扩展名
        //FilenameUtils.getExtension()
        String ext = FileUtil.getExtName(file.getOriginalFilename());
        // 上传文件
        StorePath path = storageClient.uploadFile(file.getInputStream(), file.getSize(),ext,metaData);
        System.err.println(ReflectionToStringBuilder.toString(path));
        // 验证获取MetaData
        log.debug("##获取Metadata##");
        Set<MetaData> fetchMetaData = storageClient.getMetadata(path.getGroup(), path.getPath());
        return Result.ok(path.getFullPath());
    }


    /**
     * 文件上传，不设置MetaData
     */
    @ApiOperation("文件上传 不设置MetaData")
    @PostMapping("fileupload2")
    public Result upload2(@RequestParam("fileName") MultipartFile file) throws Exception{
        log.debug("-------> 文件上传 不设置MetaData --------> ");
        //扩展名
        String ext = FileUtil.getExtName(file.getOriginalFilename());
        // 上传文件
        StorePath path = storageClient.uploadFile(file.getInputStream(), file.getSize(),ext,null);
        System.err.println(ReflectionToStringBuilder.toString(path));
        return Result.ok(path.getFullPath());
    }

    /**
     * 上传图片，并且生成缩略图、
     * 【生成的缩略图大小是配置文件配置的固定的，不能由前端指定】
     *
     * <pre>
     *     缩略图为上传文件名+缩略图后缀 _150x150,如 xxx.jpg,缩略图为 xxx_150x150.jpg  宽x高
     *
     *
     * 实际样例如下
     *
     * http://localhost:8098/M00/00/17/rBEAAl33pQaAWNQNAAHYvQQn-YE374.jpg
     * http://localhost:8098/M00/00/17/rBEAAl33pQaAWNQNAAHYvQQn-YE374_150x150.jpg
     * </pre>
     */
    @ApiOperation("上传图片，并且生成缩略图")
    @PostMapping("uploadWithThumbImg")
    public Result uploadWithThumbImg(@RequestParam("fileName") MultipartFile file) throws Exception{

//        new FastImageFile.Builder()   这种方式元数据不能为空
//                .withThumbImage()
//                .withFile(in, file.length(), fileExtName)
//                .withMetaData(metaDataSet)
//                .build();


        //扩展名
        String ext = FileUtil.getExtName(file.getOriginalFilename());
        StorePath storePath = storageClient.uploadImageAndCrtThumbImage(file.getInputStream(),file.getSize(),ext,null);

        // 这里需要一个获取从文件名的能力，所以从文件名配置以后就最好不要改了
        //返回缩略图的路径 ：M00/00/00/rBUADV6nnuWAfp1AAABNJszOugA640_150x150.jpg
        String slavePath = thumbImageConfig.getThumbImagePath(storePath.getPath());
        log.info("##缩略图路径 ##{}", slavePath);
        // 或者由客户端再记录一下从文件的前缀 -
        FileInfo slaveFile = storageClient.queryFileInfo(storePath.getGroup(), slavePath);
        log.info("##获取到从文件##{}", slaveFile);

        Map<String,String> map = new HashMap<>();
        map.put("fullPath",storePath.getFullPath());
        map.put("thumPath",slavePath);
        return Result.ok(map);
    }


    /**
     * 上传图片，按设定尺寸方式生成缩略图
     * @param file
     * @return
     * @throws Exception
     */
    @ApiOperation("上传图片 按设定尺寸方式生成缩略图")
    @PostMapping("thumbBySize")
    public Result thumbBySize(@RequestParam("fileName") MultipartFile file,
                              @RequestParam(name = "width",defaultValue = "150",required = false)int width,
                              @RequestParam(name = "height",defaultValue = "150",required = false)int height) throws Exception{

        //自定义缩略图
        FastImageFile fileBySize = new FastImageFile.Builder()
                .withThumbImage(width, height)
                .withFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()))
                .withMetaData(createMetaData()) //元数据，不能为空
                .build();
        //上传
        StorePath storePath = storageClient.uploadImage(fileBySize);
        String slavePath = FileUtil.getThumbPath(storePath.getFullPath(),width,height);
        //获取元数据
        //Set<MetaData> fetchMetaData = storageClient.getMetadata(storePath.getGroup(), storePath.getPath());
        Map<String,String> map = new HashMap<>();
        map.put("fullPath",storePath.getFullPath());
        map.put("thumPath",slavePath);
        return Result.ok(map);
    }


    /**
     * 上传图片， 按缩略图比例上传
     * @param file
     * @return
     * @throws Exception
     */
    @ApiOperation("上传图片 按缩略图比例上传")
    @PostMapping("thumbByScale")
    public Result thumbByScale(@RequestParam("fileName") MultipartFile file,
                              @RequestParam(name = "scale",defaultValue = "1",required = false)double scale) throws Exception{

        //自定义缩略图
        FastImageFile fileBySize = new FastImageFile.Builder()
                .withThumbImage(0.5) //50%比例
                .withFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()))
                .withMetaData(createMetaData()) //元数据，不能为空
                .build();
        //上传
        StorePath storePath = storageClient.uploadImage(fileBySize);
        //获取元数据
        //Set<MetaData> fetchMetaData = storageClient.getMetadata(storePath.getGroup(), storePath.getPath());
        String slavePath = thumbImageConfig.getThumbImagePath(storePath.getPath());

        Map<String,String> map = new HashMap<>();
        map.put("fullPath",storePath.getFullPath());
        map.put("thumPath",slavePath);

        return Result.ok(map);
    }


    /**
     * 元数据
     *
     * @return
     */
    private Set<MetaData> createMetaData() {

        Set<MetaData> metaDataSet = new HashSet<MetaData>();
        metaDataSet.add(new MetaData("Author", "lhy"));
        metaDataSet.add(new MetaData("CreateDate", "2020-04-28"));
        return metaDataSet;
    }


}
