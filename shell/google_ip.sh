#!/bin/bash

awk '$8 ~ /google.com/ {print $1}' acc_log1 > google_ip
