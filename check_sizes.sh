#!/bin/bash

path='src/main/resources/reactions/*';
for i in $path; do
    for img in "$i"/*.gif; do
        size="$(identify -format '%w;%h' "$img")";

        width="$(cut -d';' -f1 <<< "$size")";
        height="$(cut -d';' -f2 <<< "$size")";
        if [ "$width" -lt 400 ] || [ "$height" -lt 200 ]; then
            echo "$size";
        fi
    done
done