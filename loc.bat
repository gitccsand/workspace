@echo off
for /l %%i in (1,2,400) do curl http://127.0.0.1:35950/ds/SetCookies.do?username=%%i

pause>nul