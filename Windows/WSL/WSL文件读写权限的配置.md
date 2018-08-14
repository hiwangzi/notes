# WSL文件读写权限的配置

> 参考：[WSL文件读写权限的配置方法](http://zuyunfei.com/2018/06/15/file-system-configuration-in-wsl/)

* 早期版本所有Windows文件的owner和group都会设置为root，读写权限均为777。目前虽默认owner和group为WSL安装时设置的用户，但读写权限仍为777。
* chmod、chown对于这些文件无作用。

## DrvFs

* WSL的文件权限目前可以支持更多的Metadata。[Chmod/Chown WSL Improvements](https://blogs.msdn.microsoft.com/commandline/2018/01/12/chmod-chown-wsl-improvements/)
    ```shell
    sudo mount -t drvfs C: /mnt/c -o metadata,uid=1000,gid=1000,umask=22,fmask=111
    ```

## /etc/wsl.conf

* [Automatically Configuring WSL](https://blogs.msdn.microsoft.com/commandline/2018/02/07/automatically-configuring-wsl/)
    ```shell
    [automount]
    enabled = true
    root = /mnt/
    options = "metadata,umask=22,fmask=11"
    mountFsTab = false
    ```

## WSL下umask的一个bug

* 如果这时用mkdir命令创建一个空文件夹，就会发现新的文件夹还是777权限。这可能是wsl的一个bug，console默认的umask值仍然是0000。
* 临时解决办法：在.profile、.bashrc、.zshrc或者其他shell配置文件中重新设置一下umask。
    ```shell
    # Fix mkdir command has wrong permissions
    if grep -q Microsoft /proc/version; then
        umask 0022
    fi
    ```
