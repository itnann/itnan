package com.itnan.controller;

import com.itnan.domain.FileState;
import com.itnan.domain.Path;
import com.itnan.service.HadoopService;
import org.apache.hadoop.fs.FileStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/baidu")
public class HadoopController {

    @Autowired
    private HadoopService hadoopDao;

    @PostMapping
    public Result copyFile(@RequestBody Path path){
        boolean flag = hadoopDao.copyFile(path.getUri());
        return new Result(flag ? Code.SAVE_OK : Code.SAVE_ERR, flag);
    }

    @PutMapping
    public Result download(@RequestBody Path path){
        boolean flag = hadoopDao.download(path.getRemote(), path.getLocal());
        return  new Result(flag ? Code.UPDATE_OK : Code.UPDATE_ERR, flag);
    }


    @DeleteMapping
    public Result deleteFromHdfs(@RequestBody Path path){
        boolean flag = hadoopDao.deleteFromHdfs(path.getUri());
        return new Result(flag ? Code.DELETE_OK:Code.DELETE_ERR, flag);

    }

    @GetMapping
    public Result getDirectory(){
        List<FileState> directoryFromHdfs = hadoopDao.getDirectoryFromHdfs();
        System.out.println(directoryFromHdfs);
        Integer code = directoryFromHdfs != null ? Code.GET_OK : Code.GET_ERR;
        String msg = directoryFromHdfs != null ? "" : "文件查询失败，请重试";
        return new Result(code, directoryFromHdfs, msg);
    }
}
