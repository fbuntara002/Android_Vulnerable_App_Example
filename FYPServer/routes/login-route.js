const express = require('express');
const login = require('../models/login');
const htmlresponse = require('../utils/HTMLresponse');

var router = express.Router();

router.route('/')
    .post((req, res) => {
        const username = req.body.username;
        const password = req.body.password;
        login.authenticate(username, password, (err, success) => {
            if (err) {
                if (err === 'BADREQUEST') {
                    res.status(400)
                        .json(htmlresponse.error(err, 'POST /login'));
                }
                else if (err === 'INVALID') {
                    res.status(401)
                        .json(htmlresponse.error(err, 'POST /login'));
                    return;
                }
            }
            res.status(200)
                .json(htmlresponse.success(200, success, 'POST /user'));
        });
    });

module.exports = router;