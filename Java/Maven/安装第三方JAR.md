# 安装第三方 JAR

* 安装 JAR 文件到本地 Maven 仓库中，可以将 JAR 存放到本地仓库的合适位置之中，但相对麻烦。
* 可以使用如下命令，来简化操作（此处的`<packaging>`为`jar`）：
    ```shell
    mvn install:install-file -Dfile=<path-to-file> -DgroupId=<group-id> \
       -DartifactId=<artifact-id> -Dversion=<version> -Dpackaging=<packaging>
    ```
* 如果有`pom`文件，可以直接使用如下命令
    ```shell
    mvn install:install-file -Dfile=<path-to-file> -DpomFile=<path-to-pomfile>
    ```
* 当使用的`maven-install-plugin`版本在2.5以上时，可以更加简化（前提是：被安装的JAR文件是通过Maven构建的，其中`META-INF`子目录包含了`pom.xml`）
    ```shell
    mvn install:install-file -Dfile=<path-to-file>
    ```
