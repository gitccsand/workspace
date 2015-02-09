#!/bin/bash

awk '$8 ~ /baidu.com/ {print $1}' acc_log1 > baidu_ip
