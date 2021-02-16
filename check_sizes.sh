#!/bin/bash

path='src/main/resources/reactions/*';
for i in $path; do
    for img in "$i"/*.gif; do
        size="$(convert "$img" -print "%wx%h" /dev/null)";

        width="$(cut -d'x' -f1 <<< "$size")";
        height="$(cut -d'x' -f2 <<< "$size")";
        if [ "$width" -lt 400 ] || [ "$height" -lt 200 ]; then
            rm "$img";
        else
            mv "$img" "$img"_rev
        fi
    done
done