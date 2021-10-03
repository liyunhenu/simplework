package com.vector.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 目前支持的数据源信息
 *
 * @author idea
 * @data 2020/3/7
 */
@AllArgsConstructor
@Getter
public enum SupportDatasourceEnum {

    PROD_DB("jdbc:mysql://localhost:3306/db-prod?useUnicode=true&characterEncoding=utf8","root","root","db-prod"),

    DEV_DB("jdbc:mysql://localhost:3306/db-dev?useUnicode=true&characterEncoding=utf8","root","root","db-dev"),

    PRE_DB("jdbc:mysql://localhost:3306/db-pre?useUnicode=true&characterEncoding=utf8","root","root","db-pre");

    String url;
    String username;
    String password;
    String databaseName;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public static void main(String[] args) {
        System.out.println(SupportDatasourceEnum.DEV_DB.toString());
    }

}
