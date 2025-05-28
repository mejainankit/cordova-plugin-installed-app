var exec = require('cordova/exec');

exports.getInstalledAppList = function (success, error) {
    exec(success, error, 'CordovaInstalledAppPlugin', 'getInstalledAppList', []);
};
