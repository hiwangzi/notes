# macOS安装优盘制作

* 参考：[How to Make a Bootable MacOS Mojave Installer Drive](http://osxdaily.com/2018/09/26/make-macos-mojave-boot-usb-installer/)

## 前提

* 16GB及以上大小的优盘一枚
* macOS安装包（App Store下载）

## 操作（Mojava为例）

* 确认`/Applications`下有了完整的`Install macOS Mojave.app`
* 插入优盘，执行`ls /Volumes`查看优盘的挂载目录名称
* 执行 `sudo /Applications/Install\ macOS\ Mojave.app/Contents/Resources/createinstallmedia --volume /Volumes/{第二步中的挂载目录名称} --nointeraction && say Mojave Drive Created`

## 使用（尚未尝试）

* 重启时按住`Option`键，之后选择安装系统即可
