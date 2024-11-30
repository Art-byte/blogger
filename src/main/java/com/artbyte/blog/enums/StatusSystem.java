package com.artbyte.blog.enums;

public enum StatusSystem {

    ACTIVE("active", "User is active on system"),
    INACTIVE("inactive", "User is inactive on system");

    private String statusName;
    private String statusDescription;

    StatusSystem(String statusName, String statusDescription){
        this.statusName = statusName;
        this.statusDescription = statusDescription;
    }
}
