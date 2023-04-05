package com.itnan;

import com.itnan.dao.UserDao;
import com.itnan.domain.FileState;
import com.itnan.domain.User;
import com.itnan.service.HadoopService;
import com.itnan.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.FileStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static jdk.nashorn.internal.objects.NativeMath.log;

@SpringBootTest(classes = HadoopApplication.class)
@Slf4j
class HadoopApplicationTests {

    @Autowired
    private HadoopService hadoopService;
    @Autowired
    private UserService userService;

    @Test
    public void copyFile(){
        boolean flag = hadoopService.copyFile("G:/2.txt");
        HadoopApplicationTests.log.debug("上传结果：{}",flag);
    }
    @Test
    public void download(){
        boolean flag = hadoopService.download("/user/hadoop/aaa/1.txt", "G:/");
        HadoopApplicationTests.log.debug("从hadoop下载到本地结果：{}",flag);
    }

    @Test
    public void deleteFromHdfs() {
        boolean flag = hadoopService.deleteFromHdfs("/user/hadoop/aaa/1.txt");
        HadoopApplicationTests.log.debug("删除结果：{}",flag);
    }

    @Test
    public void getDirectoryFromHdfs() {
        List<FileState> directoryFromHdfs = hadoopService.getDirectoryFromHdfs();
        HadoopApplicationTests.log.debug("遍历所有文件结果：{}",directoryFromHdfs);
    }





    @Test
    public void login(){
        boolean userState = userService.login("zhangsan", "123");
        log.debug("{}", userState);
    }

}
