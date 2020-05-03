const http = require('http');
const express = require('express');
const bodyParser = require('body-parser');
const request = require('request');

const login = require('./routes/login-route');
//prepare for express
const port = process.env.PORT || 3000;
var app = express();
var server = http.createServer(app);

//this will let us get the data from a POST
app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());
app.use('/api/login', login);

//start the server
server.listen(port, () => {
    console.log('Server has started on port ' + port);
});