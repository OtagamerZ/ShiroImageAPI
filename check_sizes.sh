#!/bin/bash

for i in "../src/main/resources/reactions"; do
    for img in i; do
        echo identify -format "%wx%h" img;
    done
done