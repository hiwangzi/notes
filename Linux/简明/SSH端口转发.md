# SSH端口转发

> 参考：[实战 SSH 端口转发](https://www.ibm.com/developerworks/cn/linux/l-cn-sshforward/index.html)

## 本地转发

```shell
ssh -L local_socket:remote_socket <SSH hostname>
```

* 将发向本地某个端口的数据转发到远程机器的某个端口

## 远程转发

```shell
ssh -R remote_socket:local_socket <SSH hostname>
```

* 将发向远程机器的某个端口的数据转发到本地某个端口

## 动态转发

```shell
ssh -D [bind_address:]port <SSH hostname>
```

* 将发向本地某个端口的数据转发到远程机器（不固定端口）
