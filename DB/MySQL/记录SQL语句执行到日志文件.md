# 记录SQL语句执行到日志文件

* 进入MySQL交互环境下（`mysql -uroot -p`）
  ```sql
  -- 查看日志功能是否开启
  SHOW VARIABLES LIKE 'general%';
  -- 开启
  SET GLOBAL general_log='ON';
  -- 再次查看
  SHOW VARIABLES LIKE 'general%';
  ```

![general_log](./resources/general_log.png)
