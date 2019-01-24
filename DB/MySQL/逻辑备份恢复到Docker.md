# 逻辑备份恢复到Docker

* 创建实例（将本目录映射到Docker实例根目录`/mysql`下）
  ```shell
  docker run -v `pwd`:/mysql -p 3306:3306 --name z-mysql -e MYSQL_ROOT_PASSWORD=root -d mysql:5.6
  ```

* 进入实例
  ```shell
  docker exec -it z-mysql bash
  ```

  * 进入MySQL交互环境下（`mysql -uroot -p`）
    ```sql
    -- 创建用于还原数据的数据库
    create database zill;
    ```

  * 退出MySQL交互环境，Docker实例内执行
    ```shell
    # backup.sql 在宿主机工作目录
    # 在第一步创建实例时，目录被映射到Docker实例根目录/mysql下
    mysql -u root -p zill < /mysql/backup.sql
    ```
