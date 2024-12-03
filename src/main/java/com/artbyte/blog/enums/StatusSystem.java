package com.artbyte.blog.enums;

public enum StatusSystem {

    ACTIVE("active", "User is active in the system"),
    INACTIVE("inactive", "User is inactive in the system");

    private String statusName;
    private String statusDescription;

    StatusSystem(String statusName, String statusDescription){
        this.statusName = statusName;
        this.statusDescription = statusDescription;
    }
}
