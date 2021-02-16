#!/bin/bash

path='src/main/resources/reactions/*';
for i in $path; do
    for img in $i/*; do
        echo $img;
    done
done