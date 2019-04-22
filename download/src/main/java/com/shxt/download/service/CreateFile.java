package com.shxt.download.service;
/**
* 该类用于生成临时下载文档，word,pdf
 * created by Ean 2019-04-11
* **/

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Service
public class CreateFile {

    public File createWordFile(Map map){
        //临时文件
        File f = null;
        //需要组装的数据
        Map<String,Object> dataMap = map;
        //装载数据
        //dataMap.put("title","测试生成文档"+(int)(Math.random()*200000));//标题；
        //图片，base64编码
        //dataMap.put("imgUrl","");
        Configuration configuration = new Configuration();

        //设置编码
        configuration.setDefaultEncoding("UTF-8");

        //ftl模板文件
        configuration.setClassForTemplateLoading(CreateFile.class,"/");

        //获取模板
        try {
            //新建文件
            f = File.createTempFile("tmpw",".doc");
            //装载模板
            Template template = configuration.getTemplate("model.ftl");
            //写入临时文件
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f),"UTF-8"));
            //生成文件
            try {
                template.process(dataMap,out);
            } catch (TemplateException e) {
                e.printStackTrace();
            }
            //关闭流
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }


}
