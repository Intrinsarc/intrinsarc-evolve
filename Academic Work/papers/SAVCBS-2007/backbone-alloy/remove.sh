#!/bin/bash

for var in $@
do
	echo $var
	grep -v /// $var > stripped/$var
done

