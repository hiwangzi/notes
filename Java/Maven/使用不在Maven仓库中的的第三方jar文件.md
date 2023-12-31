# 使用不在Maven仓库中的的第三方jar文件

* 安装 JAR 文件到本地 Maven 仓库中，可以手动将 JAR 存放到本地仓库的合适位置之中，但相对麻烦。为了简化操作，可以使用以下三种方式：
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
* 还有一种方式，[System Dependencies](https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#system-dependencies)，不过已经标记为废弃了。
    ```xml
    <project>
      ...
      <dependencies>
        <dependency>
          <groupId>javax.sql</groupId>
          <artifactId>jdbc-stdext</artifactId>
          <version>2.0</version>
          <scope>system</scope>
          <systemPath>${java.home}/lib/rt.jar</systemPath>
        </dependency>
      </dependencies>
      ...
    </project>
    ```