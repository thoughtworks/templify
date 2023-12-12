#!/bin/sh

test(){
    # for s in `git diff --name-only HEAD~`
    for s in `git diff --name-only`
    do
        classes=""
        if [[ $s == *".java" ]]; then
            s=$(echo $s | tr '/' '.')
            s=$(echo ${s%.java})
            s=${s/.java/}
        if [[ $s != *"Test.java" ]]; then
            s=${s/src./}Test
        fi
            s=$(echo "$s" | sed 's/.*\.//')
            classes=$classes,$s
        fi
    done
    if [ -n "$classes" ]; then
        echo " -> Test Suite: ${classes:1}"
        mvn test -Dtest="${classes:1}" -DunitOnly=true
    else
        echo " -> No modified java files found"
    fi
}

KEY='y'
while [ $KEY == 'y' ]
do
    test
    read -n1 -p  "You are in watch mode, re-run tests? [y/n]" -s KEY
done
