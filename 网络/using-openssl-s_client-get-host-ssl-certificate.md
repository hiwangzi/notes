## show certifactes
openssl s_client -showcerts -servername hiwangzi.com -connect hiwangzi.com:443

## show validity
openssl s_client -showcerts -servername hiwangzi.com -connect hiwangzi.com:443 | openssl x509 -dates -noout
