# sjug-httpclient
Silesia JUG - lightning talk about Java 11 HTTP Client.

## Runtime environment

 - JDK 11 - client
 - node.js - server

## How to start

 - Go to server directory and execute command `npm install` and next `npm start` to start http server. Server uses 4 ports http -> 3080, https -> 3443, http2 -> 3445, webSocket -> 3446.
 - Optional - test http server: `curl -v http://localhost:3080/` https: `curl -v --cacert ssl/server.crt https://localhost:3443/`
 - Import localhost certificate to your java 11 global keystore. Example command: `~/sjug-httpclient/server/ssl$ keytool -import -alias localhost -keystore /usr/lib/jvm/jdk-11.0.1/lib/security/cacerts -file server.crt` and use password `changeit`
 - Now You're ready to open client project in Your favorite IDE and run tests.
 
## Author
Jacek Gzel
