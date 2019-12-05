# Linux 安装软件的几种方式

## 几种安装方式

### 源代码编译安装

源代码包的安装一般为下载软件源代码，然后编译安装。常见的 C 程序软件的安装步骤是 configure, make, make install 三部曲（```./configure && make && make install```）。
一句话来解释整个过程：
> 根据源码包中 Makefile.in 文件的指示，configure 脚本检查当前的系统环境和配置选项，在当前目录中生成 Makefile 文件(还有其它本文无需关心的文件)，然后 make 程序就按照当前目录中的 Makefile 文件的指示将源代码编译为二进制文件，最后将这些二进制文件移动(即安装)到指定的地方(仍然按照 Makefile 文件的指示)。

### 借助软件包管理器安装

例如借助 ```yum```、```apt-get``` 等管理软件进行安装。
在 Ubuntu 系统上，软件包的格式是 deb，相比于「源代码编译安装」，deb 包这类的二进制包是依赖硬件和软件平台的。
顺便提一点，```apt-get```只是```dpkg```的一个前端而已，```dpkg``` 是Debian软件包管理器的基础。而上层的工具，像是 [APT](https://zh.wikipedia.org/wiki/%E9%AB%98%E7%BA%A7%E5%8C%85%E8%A3%85%E5%B7%A5%E5%85%B7)，用于从远程获取软件包以及处理复杂的[软件包关系](https://zh.wikipedia.org/wiki/%E9%AB%98%E7%BA%A7%E5%8C%85%E8%A3%85%E5%B7%A5%E5%85%B7#.E4.BE.9D.E8.B3.B4.E9.97.9C.E4.BF.82.E8.99.95.E7.90.86)。

### 二进制格式安装

编译好的文件，类似于 Windows 下的 exe，后缀一般为 bin，如 jdk 就有 bin 后缀（虽然 Linux 下没有后缀的概念，但为了好区分，一般文件名都加后缀）。安装就是先给它可执行权限，然后执行，例如：

```shell
chmod 777 xxx.bin && ./xxx.bin
```

## 总结

+ 源代码编译安装最为灵活自由
+ 借助包管理器最为方便
+ 二进制格式安装方式一般被一些闭源的驱动和预编译的安装包所采用

## 参考

+ [源代码包 - deepin Wiki](https://wiki.deepin.org/index.php?title=%E6%BA%90%E4%BB%A3%E7%A0%81%E5%8C%85)
+ [深入理解软件包的配置、编译与安装](http://www.jinbuguo.com/linux/understand_package_install.html)
+ [在 Linux 下安装软件的方法有哪些？各有什么优劣？](https://zhihu.com/question/20126212/)
