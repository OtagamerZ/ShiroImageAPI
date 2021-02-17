#!/bin/bash

path='src/main/resources/reactions/*'
shopt -s nullglob
for i in $path; do
	shopt -s nullglob
	for img in "$i"/*.gif; do
		filename="$(basename img)"
		dir="$(dirname img)"
		size="$({
			convert "$img" -print "%wx%h" /dev/null
		} || {
			echo 'fail'
		})"

		if [[ "$size" == 'fail' ]]; then
			echo 'Invalid '"$img"
			rm "$img"
		else
			width="$(cut -d'x' -f1 <<<"$size")"
			height="$(cut -d'x' -f2 <<<"$size")"
			if [ "$width" -lt 400 ] || [ "$height" -lt 200 ]; then
				echo 'Removing '"$img"
				rm "$img"
			else
				echo 'Checked '"$img"
				mv "$img" "$dir"/"$filename".rev
			fi
		fi
	done

	shopt -s nullglob
	for img in "$i"/*.rev; do
		dir="$(dirname img)"
		i=1

		mv "$img" "$(printf "$dir/%0.3d.gif" $i)"
		i=$((i + 1))
	done
done
