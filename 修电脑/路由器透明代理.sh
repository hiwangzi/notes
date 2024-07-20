# 切换到log目录
cd /root/log

# 启动shadowsocks
nohup ss-redir -s xx.xx.xx.xx -p 446 -l 7079 -m aes-256-cfb -k secret-key &
nohup ss-tunnel -s xx.xx.xx.xx -p 446 -l 7078 -m aes-256-cfb -k secret-key -L 8.8.8.8:53 -u &

# 解决dns投毒问题
nohup chinadns -c /etc/chinadns_chnroute.txt -p 5353 -s 223.5.5.5,127.0.0.1:7078 &
/etc/init.d/dnsmasq stop
nohup dnsmasq --no-resolv --server 127.0.0.1#5353 &

# 路由配置
ipset -N china_routes hash:net maxelem 99999
( while read ip; do ipset add china_routes "$ip"; done ) < /etc/chinadns_chnroute.txt
iptables -t nat -N SHADOWSOCKS
iptables -t nat -A SHADOWSOCKS -d xx.xx.xx.xx -j RETURN
iptables -t nat -A SHADOWSOCKS -d 0.0.0.0/8 -j RETURN
iptables -t nat -A SHADOWSOCKS -d 10.0.0.0/8 -j RETURN
iptables -t nat -A SHADOWSOCKS -d 127.0.0.0/8 -j RETURN
iptables -t nat -A SHADOWSOCKS -d 169.254.0.0/16 -j RETURN
iptables -t nat -A SHADOWSOCKS -d 172.16.0.0/12 -j RETURN
iptables -t nat -A SHADOWSOCKS -d 192.168.0.0/16 -j RETURN
iptables -t nat -A SHADOWSOCKS -d 224.0.0.0/4 -j RETURN
iptables -t nat -A SHADOWSOCKS -d 240.0.0.0/4 -j RETURN
iptables -t nat -A SHADOWSOCKS -p tcp -m set --match-set china_routes dst -j RETURN
iptables -t nat -A SHADOWSOCKS -p tcp -j REDIRECT --to-port 7079
iptables -t nat -A OUTPUT -p tcp -j SHADOWSOCKS
iptables -t nat -A PREROUTING -p tcp -j SHADOWSOCKS

