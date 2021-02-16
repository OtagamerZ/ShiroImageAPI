#!/bin/sh

path='src/main/resources/reactions';
for i in $path; do
    for img in $path/$i; do
        echo $img;
    done
done