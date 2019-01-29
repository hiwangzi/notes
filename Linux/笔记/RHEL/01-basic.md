# 基础

## 软件安装

* rpm：解决手动编译的复杂性
* yum：解决包依赖的复杂性
  * `yum repolist all`：列出所有仓库
  * `yum list all`：列出仓库中的所有软件包
  * `yum info 软件包名称`：查看软件包信息
  * `yum install 软件包名称`
  * `yum reinstall 软件包名称`
  * `yum update 软件包名称`
  * `yum remove 软件包名称`
  * `yum clean all`：清除所有仓库缓存
  * `yum check-update`：检查可更新的软件包
  * `yum grouplist`：查看系统中已经安装的软件包组
  * `yum groupinstall 软件包组`
  * `yum groupremove 软件包组`
  * `yum groupinfo 软件包组`

## systemd

* RHEL 7 系统开始使用 systemd 初始化进程服务，不再有“运行级别”这个概念，而是将启动时要进行的大量初始化工作分别看作一个一个的单元（Unit），systemd用目标（target）代替了System V init中运行级别的概念。

    |System V init运行级别|systemd目标名称|作用|
    |--------------------|--------------|---|
    |0|runlevel0.target, poweroff.target|关机|
    |1|runlevel1.target, rescue.target|单用户模式|
    |2|runlevel2.target, multi-user.target|多用户CLI|
    |3|runlevel3.target, multi-user.target|多用户CLI|
    |4|runlevel4.target, multi-user.target|多用户CLI|
    |5|runlevel5.target, graphical.target|多用户GUI|
    |6|runlevel6.target, reboot.target|重启|
    |emergency|emergency.target|紧急Shell|

* 例如，以下命令可以将系统默认运行目标修改为“多用户，无图形”模式（软链接模式目标文件到`/etc/systemd/system`目录）
  ```shell
  [root@linuxprobe ~]# ln -sf /lib/systemd/system/multi-user.target /etc/systemd/system/default.target
  ```

### systemctl常用命令

* 管理服务的启动、重启、停止、重载、查看状态

    |System V init命令（RHEL6）|systemctl命令（RHEL7）|作用|
    |--------------------|--------------|---|
    |service foo start|systemctl start foo.service|启动foo服务|
    |service foo restart|systemctl restart foo.service|重启foo服务|
    |service foo stop|systemctl stop foo.service|停止foo服务|
    |service foo reload|systemctl reload foo.service|重新加载配置文件（不终止服务）|
    |service foo status|systemctl status foo.service|查看foo服务状态|

* 设置服务的开机启动、不启动、查看各级别下服务启动状态

    |System V init命令（RHEL6）|systemctl命令（RHEL7）|作用|
    |--------------------|--------------|---|
    |chkconfig foo on|systemctl enable foo.service|开机自动启动|
    |chkconfig foo off|systemctl disable foo.service|开机不自动启动|
    |chkconfig foo|systemctl is-enabled foo.service|查看特定服务是否为开机自启动|
    |chkconfig --list|systemctl list-unit-files --type=service|查看各个级别下服务的启动与禁用情况|
