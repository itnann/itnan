package com.itnan.utils;

import org.apache.hadoop.conf.Configuration;

public abstract class HadoopConfigurationFactory {
    private static Configuration conf = new Configuration();

    public static Configuration getHpConfiguration(){
        conf.set("fs.defaultFS", "hdfs://node1:8020");
        conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        System.setProperty("HADOOP_USER_NAME","root");
        return  conf;
    }
}
