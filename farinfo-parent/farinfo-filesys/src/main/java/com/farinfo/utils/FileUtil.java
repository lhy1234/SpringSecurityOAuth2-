package com.farinfo.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by: 李浩洋 on 2020-04-27
 **/
public class FileUtil {



    public static String getExtName(String fileName){

        if(StringUtils.isBlank(fileName)){
            return null;
        }
        String ext =  StringUtils.substring(fileName,fileName.lastIndexOf(".")+1);
        return ext;
    }

    /**
     * 得到缩略图得路径
     * @param fullPath
     * @param width
     * @param height
     * @return
     */
    public static String getThumbPath(String fullPath,int width,int height){
        if (fullPath == null) {
            StringBuilder buffer = new StringBuilder();
            buffer.append("_").append(width).append("x").append(height);
            fullPath = new String(buffer);
        }
        return fullPath;
    }

    public static void main(String[] args) {
        String ext =  FileUtil.getExtName("xxx.jpg");
        System.err.println(ext);
    }



}
