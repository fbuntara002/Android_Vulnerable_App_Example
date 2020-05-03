# Android Vulnerable App Example
This demonstrate a vulnerable Android Application, as this application:
1. CWE-312: store important data in SharedPreferences unencrypted
2. CWE-319: send important data without using TLS/SSL
3. CWE-532: log important information into the logcat

Please please please don't ever do this! The exploit is the proof.

Instruction:
1. Run the node.js server by go the server folder and run:
```
node server.js
```
2. Root the android device
3. Change the url in the vulnerable app to the current server url
4. Register your desired username and password in the server code and the app side to test, or use the available user.
NOTE: check the server side to know the current valid username and password
5. Run the application in an emulator or actual device.
6. Run the exploit app in an emulator or actual device to collect the stored username and password
