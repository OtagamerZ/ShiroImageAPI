#!/bin/bash

path='src/main/resources/reactions/*';
for i in $path; do
    for img in "$i"/*; do
        size="$(identify -format '%wx%h' "$img")";
        values="${!size[x]}";
        if [ "${values[0]}" -lt 400 ] || [ "${values[1]}" -lt 200 ]; then
            echo "$values";
        fi
    done
done