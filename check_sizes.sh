#!/bin/bash

path='src/main/resources/reactions/*';
for i in $path; do
    for img in "$i"/*; do
        size="$(identify -format '%wx%h' "$img")";

        width="$(cut -d'x' -f1 <<< "$size")";
        height="$(cut -d'x' -f2 <<< "$size")";
        if [ "$width" -lt 400 ] || [ "$height" -lt 200 ]; then
            echo "$width"'x'"$height";
        fi
    done
done