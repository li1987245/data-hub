const express = require('express')

const hostname = '127.0.0.1';
const port = 3000;
const env = process.env.NODE_ENV || 'development'
const app = express()

app.use(express.static('./dist'))

server.listen(port, hostname, () => {
    console.log(`Server running at http://${hostname}:${port}/`);
});