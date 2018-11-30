# sjug-httpclient
Silesia JUG - lightning talk about Java 11 HTTP Client.

## Runtime environment

 - JDK 11 - client
 - node.js - server

## How to start

 - Go to server directory and execute command `npm start` to start http server. Server use 2 ports 3080 for http and 3443 for https.
 - Optional - test http server: `curl -v http://localhost:3080/` https: `curl -v --cacert ssl/server.crt https://localhost:3443/`
 - Import localhost certificate to your java 11 global keystore. Example command: `~/sjug-httpclient/server/ssl$ keytool -import -alias localhost -keystore /usr/lib/jvm/jdk-11.0.1/lib/security/cacerts -file server.crt` and use password `changeit`
 - Now You're ready to open client project in Your favorite IDE and build it using JDK 11.
 - Now run Main class. Example command: `~/sjug-httpclient/client/out/production/httpclient$ /usr/lib/jvm/jdk-11.0.1/bin/java com.jgprogram.sjug.Main`
 
## Author
Jacek Gzel
