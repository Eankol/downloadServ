package com.shxt.download.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class filesProvider {

    public static filesProvider f = new filesProvider();

    private Map<String,File> map=new HashMap<>();

    public Map<String,File> addFile(String downloadCode,File file){
        this.map.put(downloadCode,file);
        return this.map;
    }

    public Map<String,File> removeFile(String downloadCode){
        this.map.remove(downloadCode);
        return this.map;
    }

    public File getFile(String downloadCode){
        return this.map.get(downloadCode);
    }

    public int hasFlie(String downloadCode){
        if(this.map.size()>0){
            File f = this.map.get(downloadCode);
            if (f!=null){
                return 1;
            }else {
                return 0;
            }
        }else {
            return 0;
        }

    }


}
