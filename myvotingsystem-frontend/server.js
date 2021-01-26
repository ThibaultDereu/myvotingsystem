const express = require('express');
const path = require('path');
 
const server = express();
 
// built in middleware to serve static content just as images, css, html etc
server.use(express.static(path.join(__dirname, 'dist/votingsystem')));
 
// all get requests will point to angular's index.html in dist folder
server.get('/*', async (req, res) => {
    res.sendFile(path.resolve(__dirname, 'dist/votingsystem', 'index.html'));
});

server.listen(3000, () => console.log('App Running on port 3000'));
