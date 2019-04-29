# Git 设置代理

## HTTP 协议

### 设置

* `git` 命令方式设置
    ```shell
    # 除了socks代理，同样支持http代理
    git config --global http.proxy 'socks5://127.0.0.1:1080'
    ```

* 或者直接编辑 `~/.gitconfig`
    ```config
    [http]
      proxy = socks5://127.0.0.1:1080
    ```

### 取消设置

* `git` 命令方式设置

    ```shell
    git config --global --unset http.proxy
    ```

* 或者直接编辑 `~/.gitconfig` 去除代理相关设置

## SSH 协议

* 上面http协议的设置相对简单，但如果希望通过 `ssh` 协议 clone 项目的同时，也使用代理服务，需要更改 ssh 的配置（即针对某个域名，配置 ssh 使用 socks 或 http 代理）

    ```
    # 编辑该文件 ~/.ssh/config
    # 如果没有，可以新建
    Host github.com
        ProxyCommand nc -X 5 -x 127.0.0.1:1080 %h %p
    ```

* 如果使用上述命令，出现 `invalid option -- 'X'` 错误（或者类似的错误信息），则说明使用的 `nc` （netcat）命令不是 OpenBSD 版本。此时可以考虑使用 `corkscrew` 代替（只支持HTTP代理SSH），使用方法如下：

    ```
    # 假设本地1081端口为HTTP代理
    Host github.com
        ProxyCommand corkscrew 127.0.0.1 1081 %h %p
    ```

## 参考链接

* [设置 Git 代理 - Cielpark](https://imciel.com/2016/06/28/git-proxy/)
* [Git - git-config Documentation](https://git-scm.com/docs/git-config)
* [Tunneling SSH over PageKite: /bin/nc: invalid option -- 'X'](https://pagekite.net/wiki/Howto/SshOverPageKite/#wrongnetcat)
