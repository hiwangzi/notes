# netsh

## 1. 通过命令提示符（cmd）命令连接 Wi-Fi

### 1.1 连接曾经连接过的 Wi-Fi

```cmd
:: 查看配置的列表（::表示注释）
netsh wlan show profile
:: 连接
netsh wlan connect ssid=你的SSID名字(简单可以理解为Wi-Fi名) name=你的配置名字
```

### 1.2 连接从未连接过的 Wi-Fi

```cmd
:: 先增加一项 Wi-Fi 配置，注意要在配置文件所在目录执行
netsh wlan add profile filename="你的配置.xml"
:: 查看配置的列表，检查是否添加成功
netsh wlan show profile
:: 连接
netsh wlan connect ssid=你的SSID名字 name=你的配置名字
```

* 示例配置文件

```xml
<?xml version="1.0"?>
<WLANProfile xmlns="http://www.microsoft.com/networking/WLAN/profile/v1">
    <name>你的配置名字（与SSID名字相同即可）</name>
    <SSIDConfig>
        <SSID>
            <name>你的SSID名字</name>
        </SSID>
    </SSIDConfig>
    <connectionType>ESS</connectionType>
    <connectionMode>auto</connectionMode>
    <MSM>
        <security>
            <authEncryption>
                <authentication>WPA2PSK</authentication>
                <encryption>AES</encryption>
                <useOneX>false</useOneX>
            </authEncryption>
            <sharedKey>
                <keyType>passPhrase</keyType>
                <protected>false</protected>
                <keyMaterial>你的WiFi密码</keyMaterial>
            </sharedKey>
        </security>
    </MSM>
    <MacRandomization xmlns="http://www.microsoft.com/networking/WLAN/profile/v3">
        <enableRandomization>false</enableRandomization>
    </MacRandomization>
</WLANProfile>
```

## 2. 查看连接过的 Wi-Fi 密码

```cmd
:: 查看特定
netsh wlan show profile 配置名称 key=clear

:: 查看所有
for /f "skip=9 tokens=1,2 delims=:" %i in ('netsh wlan show profiles') do  @echo %j | findstr -i -v echo | netsh wlan show profiles %j key=clear
```

## 3. 查看、禁用、启用网络接口（网卡）

```cmd
:: 查看网络接口的列表
netsh interface show interface
:: 禁用网络接口
netsh interface set interface 接口名称 disabled
:: 启用网络接口
netsh interface set interface 接口名称 enabled
```

---

> 此文原地址：http://www.cnblogs.com/hiwangzi/p/7475121.html
>
> #1楼[楼主] 2017-12-16 14:09 hiwangzi
>
> DHCP：
>
> netsh interface ipv4 set address name="Wi-Fi" source=dhcp
>
> 静态 IP：
>
> netsh interface ipv4 set address name="Wi-Fi" source="static" addr="10.0.0.177" mask="255.255.255.0" gateway="10.0.0.1"