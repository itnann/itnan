package com.itnan.service.Impl;


import com.itnan.domain.FileState;
import com.itnan.utils.HadoopConfigurationFactory;
import com.itnan.service.HadoopService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HadoopServiceImpl implements HadoopService {
    private static final String HDFS_PATH = "/user/hadoop/aaa";
    private static Configuration conf = HadoopConfigurationFactory.getHpConfiguration();

    @Override
    public boolean copyFile(String local) {
        try (FileSystem fs = FileSystem.get(conf)) {
            fs.copyFromLocalFile(new Path(local), new Path(HDFS_PATH));
           return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean download(String remote, String local) {
        try (FileSystem fs = FileSystem.get(conf)) {
            fs.copyToLocalFile(false,new Path(remote),new Path(local));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteFromHdfs(String deletePath) {
        try (FileSystem fs = FileSystem.get(conf)) {
            Path path = new Path(deletePath);
            boolean delete = fs.delete(path, true);
            return delete;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<FileState> getDirectoryFromHdfs() {
        List<FileState> list = new ArrayList<>();
        try (FileSystem fs = FileSystem.get(conf)){
            FileStatus[] fileStatuses = fs.listStatus(new Path(HDFS_PATH));
            for (int i = 0; i < fileStatuses.length; i++) {
                FileState fileState = new FileState();
                fileState.setFilePath(fileStatuses[i].getPath().toString());
                fileState.setPermission(fileStatuses[i].getPermission().toString());
                fileState.setOwner(fileStatuses[i].getOwner());
                fileState.setSize(fileStatuses[i].getBlockSize());
                list.add(fileState);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
