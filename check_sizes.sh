#!/bin/bash

path='src/main/resources/reactions/*';
for i in $path; do
    for img in "$i"/*.gif; do
        size="$(identify -format '%wx%h' "$img")";

        width="$(cut -d'x' -f1 <<< "$size")";
        height="$(cut -d'x' -f2 <<< "$size")";
        if [ "$width" -lt 400 ] || [ "$height" -lt 200 ]; then
            echo "$height";
        fi
    done
done