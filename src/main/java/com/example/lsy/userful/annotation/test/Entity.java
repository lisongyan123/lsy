package com.example.lsy.userful.annotation.test;


/**
 * @author jiangqiang
 * @version 2.0.1
 * @date 2018/4/25
 **/
public class Entity {
    @FieldAnnotation(length = 2,description = "名称")
    private String name;
    @FieldAnnotation(length = 5,description = "描述")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}