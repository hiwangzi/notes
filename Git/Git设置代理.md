# Git 设置代理

* 参考链接：[设置 Git 代理 - Cielpark](https://imciel.com/2016/06/28/git-proxy/)

## http协议

### 设置

* git命令方式设置
  ```shell
  # 除了socks代理，同样支持http代理
  git config --global http.proxy 'socks5://127.0.0.1:1080'
  git config --global https.proxy 'socks5://127.0.0.1:1080'
  ```

* 或者直接编辑 `~/.gitconfig`
  ```config
  [http]
    proxy = socks5://127.0.0.1:1080
  [https]
    proxy = socks5://127.0.0.1:1080
  ```

### 取消设置（同样可以直接去编辑配置文件）

```shell
git config --global --unset http.proxy
git config --global --unset https.proxy
```

## ssh协议

* 上面http协议的设置相对简单，但如果希望通过ssh协议clone项目的同时，也使用代理服务，需要更改ssh的配置（即针对某个域名，配置ssh使用socks代理）
  ```
  # 编辑该文件 ~/.ssh/config
  # 如果没有，可以新建
  Host github.com
      ProxyCommand nc -X 5 -x 127.0.0.1:1080 %h %p
  ```
