const express = require('express');
const path = require('path');
 
const server = express();
 
// built in middleware to serve static content just as images, css, html etc
server.use(express.static(path.join(__dirname, 'dist/votingsystem')));
 
// all get requests will point to angular's index.html in dist folder
server.get('/*', async (req, res) => {
    res.sendFile(path.resolve(__dirname, 'dist/votingsystem', 'index.html'));
});

const PORT = process.env.PORT || 3000
server.listen(PORT, () => console.log('App Running on port ' + PORT));
