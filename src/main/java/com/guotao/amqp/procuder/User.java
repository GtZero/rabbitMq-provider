package com.guotao.amqp.procuder;

import java.io.Serializable;

public class User implements Serializable{
    private String name;
    private String id;
    private String number;

    @Override
    public String toString() {
        return "toString method User{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", number='" + number + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
