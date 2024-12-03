package com.artbyte.blog.enums;

public enum BlogsEnum {

    BLOG_ENABLE("blog_enable", "This blog is enabled"),
    BLOG_DISABLE("blog_disable", "This blog is disabled");

    private String statusName;
    private String statusDescription;

     BlogsEnum(String statusName, String statusDescription){
        this.statusName = statusName;
        this.statusDescription = statusDescription;
    }
}
