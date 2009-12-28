#!/bin/bash

# modify PATH (e.g. for "dot")
#PATH=/opt/graphviz/bin:$PATH

# set another java path
JAVA="java"
#JAVA="/opt/sun-jdk-1.6.0.05/bin/java"

# the *maximal* amount of memory to use (must not exceed system memory)
MEMORY=1024m

############# do not change anything below this line ########################

DIR=${0%/*}
if [[ -z "$DIR" ]]; then DIR="."; fi

$JAVA -Xmx$MEMORY -jar $DIR/LTSA.jar

