package com.itnan.service;

import com.itnan.domain.FileState;
import org.apache.hadoop.fs.FileStatus;

import java.util.List;

/**
 * hadoopDao
 *
 * @author itnan
 * @date 2023/03/31
 */
public interface HadoopService {


    /**
     * 复制文件
     * 上传文件到hadoop上
     *
     * @param local 本地路径
     */
    public boolean copyFile(String local);


    /**
     * 下载到本地文件
     *
     * @param remote 远程
     * @param local  当地
     */
    public boolean download(String remote, String local);


    /**
     * 删除从hdfs
     *
     * @param deletePath 删除路径
     * @return boolean
     */
    public boolean deleteFromHdfs(String deletePath);

    /**
     * 从hdfs获取目录
     *
     * @return {@link FileStatus[]}
     */
    public List<FileState> getDirectoryFromHdfs();
}
