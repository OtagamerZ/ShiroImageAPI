#!/bin/bash

path='src/main/resources/reactions/*';
shopt -s nullglob
for i in $path; do
    shopt -s nullglob
    for img in "$i"/*.gif; do
        size="$(convert "$img" -print "%wx%h" /dev/null)";

        width="$(cut -d'x' -f1 <<< "$size")";
        height="$(cut -d'x' -f2 <<< "$size")";
        echo "$img";
        #if [ "$width" -lt 400 ] || [ "$height" -lt 200 ]; then
        #    rm "$img";
        #else
        #    mv "$img" "$img"_rev
        #fi
    done

    shopt -s nullglob
    for img in "$i"/*.gif; do
        i=1;

        #mv "$img" "$(printf "$path/%0.3d.gif" $i)";
        i=$((i + 1))
    done
done