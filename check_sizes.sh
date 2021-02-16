#!/bin/bash

path='../src/main/resources/reactions';
for i in $path; do
    for img in $path/$i; do
        echo identify -format '%wx%h' $img;
    done
done