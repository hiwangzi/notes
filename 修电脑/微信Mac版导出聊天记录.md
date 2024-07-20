# 微信Mac版导出聊天记录

* 转载来源：[土办法导出 Mac 版微信聊天记录 - V2EX](https://www.v2ex.com/t/466053)

macOS 微信在本地存了加密的 sqlite3 数据库，本地数据库的是一系列 `*.db` 文件，可以用如下命令查看

```
ls -alh ~/Library/Containers/com.tencent.xinWeChat/Data/Library/Application\ Support/com.tencent.xinWeChat/*/*/Message/*.db
```

经过观察，微信存数据使用的是开源的 sqlcipher，所以还是有办法导出微信在 Mac 本机的数据库的，该方法依赖 lldb， 步骤如下：

* 打开微信
* 命令行运行 `lldb -p $(pgrep WeChat)`
* 在 lldb 中输入 `br set -n sqlite3_key`， 回车
* 还是在 lldb 中，输入 `c`, 回车
* 扫码登录微信
* 这时候回到 lldb 界面, 输入 `memory read --size 1 --format x --count 32 $rsi`, 回车，应该会输出类似于如下的数据
    ```
    0x000000000000: 0xab 0xcd 0xef 0xab 0xcd 0xef 0xab 0xcd
    0x000000000008: 0xab 0xcd 0xef 0xab 0xcd 0xef 0xab 0xcd
    0x000000000010: 0xab 0xcd 0xef 0xab 0xcd 0xef 0xab 0xcd
    0x000000000018: 0xab 0xcd 0xef 0xab 0xcd 0xef 0xab 0xcd
    ```
* 忽略左边的地址（ `0x000000000000:`, `0x000000000008:`），从左到右，从上到下，把形如 `0xab 0xcd` 的数据拼起来，然后去掉所有的 "0x"和空格、换行, 得到 64 个字符的字符串，这就是微信数据库的 key ；（参考 https://github.com/sqlcipher/sqlcipher ）
* 然后, 可以下一个 https://sqlitebrowser.org/ ，用来浏览之前提到的*.db 文件（每个 db 都使用的相同的 key，加密方法选择 SQLCipher 3 default），注意：打开数据库的时候选择(raw key), 然后输入 0x，再输入刚才那 64 个字符


## 补充

* `~/Library/Containers/com.tencent.xinWeChat/Data/Library/Application\ Support/com.tencent.xinWeChat/*/*/Contact/*.db`，里面是所有联系人，联系人的 m_nsUsrName 做一次 md5 后对应聊天记录 db 里以 Chat_xxx 开头的表。
