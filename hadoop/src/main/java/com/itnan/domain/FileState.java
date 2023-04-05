package com.itnan.domain;

import lombok.Data;

@Data
public class FileState {
    private String filePath;
    private String permission;
    private String owner;
    private long size;
}
