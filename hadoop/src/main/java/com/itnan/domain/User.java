package com.itnan.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_tb")
public class User {
    @TableId(type = IdType.AUTO)
    private  Long id;
    @TableField("user_name")
    private  String userName;
    @TableField(select = false)
    private  String password;
}
