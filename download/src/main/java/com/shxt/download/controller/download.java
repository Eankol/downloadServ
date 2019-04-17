package com.shxt.download.controller;

import com.shxt.download.service.CreateFile;
import com.shxt.download.service.filesProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
public class download {
    @Autowired
    CreateFile cf;

    @GetMapping("/getFile")
    public void getFile(String code,String name,HttpServletResponse response){
        File f = filesProvider.f.getFile(code);
        //System.out.println(name);
        if (f!=null){
            //下载的文件携带这个名称
            try {
                response.addHeader("Content-Disposition", "attachment;filename=" + new String((name+"."+f.getName().split("\\.")[1]).getBytes("utf-8"),"ISO8859-1"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //文件下载类型--二进制文件
            response.setContentType("application/octet-stream");
            try {
                FileInputStream fis = new FileInputStream(f);
                byte[] content = new byte[fis.available()];
                fis.read(content);
                fis.close();
                ServletOutputStream out = response.getOutputStream();
                out.write(content);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                filesProvider.f.removeFile(code);
                if (f.exists()){
                    f.delete();
                }
            }

        }
    }
    @GetMapping("/getUrl")
    public String getUrl(String fileName){
        File f = null;
        String code="";
        try {
            //新建临时文件；
            f =  cf.createWordFile();
            //生成下载码；
            code = ((int)(Math.random()*100000))+"";
            while(filesProvider.f.hasFlie(code)==1){
                System.out.println(code);
                System.out.println(filesProvider.f.hasFlie(code));
                code = (int)Math.random()*100000+"";
            }
            filesProvider.f.addFile(code,f);//添加下载文件到下载队列；
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }
}
