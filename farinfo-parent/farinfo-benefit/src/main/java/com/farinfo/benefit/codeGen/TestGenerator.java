package com.farinfo.benefit.codeGen;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/*
 * 代码生成器
 * Created by yuanzhijun on 2019/06/12
 */
public class TestGenerator {


    public void testGenerator(){
        //全局配置
        GlobalConfig globalConfig=new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        globalConfig.setActiveRecord(false); //开启 ActiveRecord 模式
        globalConfig.setAuthor("");
        //globalConfig.setOutputDir(projectPath+"/src/main/java"); //生成文件的输出目录
        globalConfig.setOutputDir("C:/ZZZ_Farinfo/genCode");//生成文件的输出目录
        globalConfig.setFileOverride(false); //是否覆盖已有文件
        globalConfig.setIdType(IdType.AUTO);
        globalConfig.setServiceName("%sService");
        globalConfig.setEnableCache(false);//是否开启mybatis二级缓冲
        globalConfig.setBaseResultMap(true);//mapper.xml表字段与实体属性映射
        globalConfig.setBaseColumnList(true);//mapper.xml基本查询列
        globalConfig.setSwagger2(true);




        //数据源配置
        DataSourceConfig dataSourceConfig=new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);
        String url = "jdbc:mysql://127.0.0.1:3306/db_oauth?useSSL=true&useUnicode=true&characterEncoding=UTF8&serverTimezone=GMT%2B8";
        dataSourceConfig.setUrl(url);
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("lhy1234");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");

        //策略配置
        StrategyConfig strategyConfig=new StrategyConfig();
        strategyConfig.setCapitalMode(true);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setNaming(NamingStrategy.underline_to_camel); // 表名生成策略 下划线转驼峰命名

        strategyConfig.setInclude("sys_user");


        strategyConfig.setTablePrefix(""); // 此处可以修改为您的表前缀
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setRestControllerStyle(true);


        //包配置
        PackageConfig packageConfig=new PackageConfig();
        packageConfig.setParent("com.farinfo.auth");
        packageConfig.setEntity("entity");
        packageConfig.setMapper("mapper");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");
        packageConfig.setController("controller");

        //模板配置
//        TemplateConfig templateConfig=new TemplateConfig();
//        templateConfig.setEntity("/templates/entity.java.vm");
//        templateConfig.setXml("/templates/mapper.xml.vm");
//        templateConfig.setMapper("/templates/mapper.java.vm");
//        templateConfig.setService("/templates/service.java.vm");
//        templateConfig.setServiceImpl("/templates/serviceImpl.java.vm");
//        templateConfig.setController("/templates/controller.java.vm");

        //自定义设置
        List<FileOutConfig> focList=new ArrayList<>();
        InjectionConfig injectionConfig=new InjectionConfig() {
            @Override
            public void initMap() {

            }
        };

        FileOutConfig fileOutConfig=new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath+"/src/main/resources/mapper/"+tableInfo.getEntityName()+"Mapper.xml";
            }
        };
        focList.add(fileOutConfig);
        injectionConfig.setFileOutConfigList(focList);


        //自动生成执行器
        AutoGenerator autoGenerator=new AutoGenerator();
        autoGenerator.setGlobalConfig(globalConfig);
        autoGenerator.setDataSource(dataSourceConfig);
        autoGenerator.setStrategy(strategyConfig);
        autoGenerator.setPackageInfo(packageConfig);
        //autoGenerator.setTemplate(templateConfig);
        autoGenerator.setCfg(injectionConfig);
        autoGenerator.execute();
    }

    public static void main(String[] args) {
        new TestGenerator().testGenerator();
    }
}
