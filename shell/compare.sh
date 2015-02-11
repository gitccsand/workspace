#!/bin/bash

for i in `sort google_ip|sort|uniq|sort`
do
	for j in `sort baidu_ip|sort|uniq|sort`
	do
		#if [[ "$i" = "$j" ]]; 
		if [[ $i = $j ]]; 
			then echo $i;
		fi
	done
done
