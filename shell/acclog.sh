for ((i=104001; i<=104100;i++))
do
curl http://192.168.169.2:35950/ds/SetCookies.do?username=$i
done
