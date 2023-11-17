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

### 系统变量

```sql
-- SHOW VARIABLES 命令用于列出MySQL的所有系统变量，例如：

-- 全局的事务隔离级别
SHOW GLOBAL VARIABLES LIKE 'transaction_isolation';
-- 会话的事务隔离级别
SHOW SESSION VARIABLES LIKE 'transaction_isolation';
```

```sql
-- @@是一个特殊的符号，用于引用系统变量，例如：

-- 全局的事务隔离级别
SELECT @@GLOBAL.transaction_isolation;
-- 会话的事务隔离级别
SELECT @@transaction_isolation;
```

```sql
-- 可以使用 SET 命令修改系统变量的值
-- 要修改全局变量，需要拥有 SUPER 权限；修改会话变量通常不需要特别的权限。
-- 修改全局变量只会影响新的连接，已经存在的连接不会受到影响。而修改会话变量只会影响当前连接的行为，其他连接不会受到影响。

-- 全局系统变量
SET GLOBAL autocommit = 0;
SET @@global.autocommit = 0;
-- 会话系统变量
SET SESSION autocommit = 0;
SET @@autocommit = 0;
```
