//for simplicity
const user = {'fuhankb': 'Buntara', 'chens': 'Sen'};

module.exports.authenticate = (username, password, res) => {
    try {
        if ((username === undefined) || (password === undefined)) {
            return res('BADREQUEST', null);
        }
        const c_password = user[username];
        if (c_password === undefined) {
            return res('INVALID', null);
        } else if (c_password !== password) {
            return res('INVALID', null);
        } else {
            return res(null, 'AUTHENTICATED');
        }

    } catch (e) {
        return res(e, null);
    }
}


