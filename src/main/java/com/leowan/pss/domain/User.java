package com.leowan.pss.domain;

public class User {
    private String name;
    private Long age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {

        this.age = age;
    }

    public void add(){
        System.out.println("leowan");
    }
}
