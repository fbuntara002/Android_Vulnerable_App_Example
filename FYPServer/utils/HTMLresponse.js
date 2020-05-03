const moment = require('moment');

module.exports.error = (err, source) => {
    if (err === 'BADREQUEST') {
        return ({
            "timestamp" : moment.utc().format(),
            "error": "400 Bad Request",
            "path": source
        });
    }
    else if (err === 'INVALID') {
        return ({
            "timestamp" : moment.utc().format(),
            "error": "401 Unauthorized",
            "path" : source
        });
    }
}

module.exports.success = (request, message, path) => {
    if (request === 200) {
        return ({
            "timestamp": moment().utc().format(),
            "status": 200,
            "success": "OK",
            "message": message,
            "path": path
        });
    }
}