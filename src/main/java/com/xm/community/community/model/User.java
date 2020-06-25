package com.xm.community.community.model;

import lombok.Data;

import javax.annotation.sql.DataSourceDefinition;

@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;

}
