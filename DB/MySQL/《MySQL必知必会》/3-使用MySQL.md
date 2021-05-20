# 使用 MySQL

## 数据库

* 列出数据库：`show databases`
* 选择数据库：`use <database_name>`（`<>`表示变量）

## 表

* 列出表：`show tables`
* 列出列：`show columns from <table_name>` 或 `describe <table_name>`(`desc <table_name>`)

## 其他

* 显示服务状态信息：`show status`
* 显示创建数据库的SQL语句：`show create database <database_name>`
* 显示创建表的SQL语句：`show create table <table_name>`
* 显示授予用户的权限：`show grants`
* 显示服务器错误信息：`show errors`
* 显示服务器警告信息：`show warnings`
