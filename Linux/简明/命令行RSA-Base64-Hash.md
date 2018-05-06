# 命令行 RSA, Base64, Hash

```bash
# -n 可以去掉换行符
echo -n '777777'
```

## RSA算法

* 加密
    ```bash
    # 利用管道命令传递字符串加密
    echo -n '777777' | openssl rsautl -encrypt -pubin -inkey public_key.pem > message.encrypted

    # （或）利用文件传递字符串加密
    echo -n '777777' > message.txt
    openssl rsautl -encrypt -pubin -inkey public_key.pem -in message.txt > message.encrypted
    ```

* 解密
    ```bash
    openssl rsautl -decrypt -inkey private_key.pem -in message.encrypted -out message.decrypted
    ```

## Base64

* 加密
    ```bash
    openssl enc -base64 -e -in message.txt > message.base64e

    # 或使用 base64
    echo -n '777777' | base64
    ```

* 解密
    ```bash
    openssl enc -base64 -d -in message.base64 > message.base64d

    # 或使用 base64
    echo -n 'Nzc3Nzc3' | base64 -d
    ```

### Hash

* MD5
    ```bash
    echo -n '777777' | md5sum
    ```
