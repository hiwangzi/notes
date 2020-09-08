# 修改 view 的 definer

* 参考链接：[mysql - Modify DEFINER on Many Views - Database Administrators Stack Exchange](https://dba.stackexchange.com/questions/4129/modify-definer-on-many-views)

```sql
SELECT
  CONCAT(
    "ALTER DEFINER=`new_user_name`@`%` VIEW ",
    table_name,
    " AS ",
    view_definition,
    ";"
  )
FROM information_schema.views
WHERE
  table_schema = 'your_db_name';
```

得到 DDL SQL，然后执行即可。
