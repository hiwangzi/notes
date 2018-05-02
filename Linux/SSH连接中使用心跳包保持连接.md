# SSH 连接中使用心跳包保持连接

## 问题现象

用 ssh 命令连接服务器之后，如果一段时间不操作，再次进入 Terminal 时会有一段时间没有响应，然后就出现错误提示：

```bash
Write failed: Broken pipe
```

只能重新用 ssh 命令进行连接。

## 解决方法

* 方法一：如果您有多台服务器，不想在每台服务器上设置，只需在客户端的 `~/.ssh/` 文件夹中添加 `config` 文件，并添加下面的配置：
    ```bash
    ServerAliveInterval 60
    ```

* 方法二：如果多个人管理同一台服务器，不想在每个客户端进行设置，只需在服务器的 `/etc/ssh/sshd_config` 中添加如下的配置：
    ```bash
    ClientAliveInterval 60
    ```

* 方法三：如果您只想让当前的 ssh 保持连接，可以使用以下的命令：
    ```bash
    $ ssh -o ServerAliveInterval=60 user@sshserver
    ```
* 解释
  * 以上的设置表示 ssh 客户端每隔 60 秒给远程主机发送一个 no-op 包，no-op 是无任何操作的意思，这样远程主机就不会关闭这个 SSH 会话。

## 参考

* 本文复制自 [解决 ssh 的"Write failed: Broken pipe"问题 - dudu - 博客园](http://www.cnblogs.com/dudu/archive/2013/02/07/ssh-write-failed-broken-pipe.html)，稍做了调整。
