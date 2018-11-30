const express = require('express')
const http = require('http')
const https = require('https')
const app = express()
var fs = require('fs')
const privateKey = fs.readFileSync('./ssl/server.key')
const certificate = fs.readFileSync('./ssl/server.crt')
const portHttp = 3080, portHttps = 3443

app.get('', (req, res) => res.send('Hi JUG!'))

http.createServer(app).listen(portHttp)
https.createServer({
    key: privateKey,
    cert: certificate
}, app).listen(portHttps)
