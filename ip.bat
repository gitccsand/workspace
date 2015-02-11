@echo off
for /l %%i in (0,2,400) do curl http://172.16.255.4:35950/ds/SetCookies.do?username=%%i
pause>nul
