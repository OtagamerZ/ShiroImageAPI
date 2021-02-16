#!/bin/bash

path='src/main/resources/reactions/*';
for i in $path; do
    for img in "$i"/*; do
        size="${$(identify -format '%wx%h' $img)[x]}";
        if [ "${size[0]}" -lt 400 ] || [ "${size[1]}" -lt 200 ]; then
            echo $size;
        fi
    done
done