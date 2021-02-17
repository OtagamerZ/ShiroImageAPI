#!/bin/bash

path='src/main/resources/reactions/*'
checked=0
removed=0
failed=0

shopt -s nullglob
for i in $path; do
	if [ ! -s "$i"/.checked ]; then
		echo 'Folder already checked. Skipping'
		continue
	fi

	shopt -s nullglob
	for img in "$i"/*.gif; do
		filename="$(basename "$img" .gif)"
		dir="$(dirname "$img")"
		size="$(convert "$img" -print "%wx%h" /dev/null || echo 'fail')"

		if [[ "$size" == *"fail"* ]]; then
			echo 'Invalid '"$img"
			rm "$img"
			failed=$((failed + 1))
		else
			width="$(cut -d'x' -f1 <<<"$size")"
			height="$(cut -d'x' -f2 <<<"$size")"
			if [ "$width" -lt 400 ] || [ "$height" -lt 200 ]; then
				echo 'Removing '"$img"
				rm "$img"
				removed=$((removed + 1))
			else
				echo 'Checked '"$img"
				mv "$img" "$dir"/"$filename".rev
				checked=$((checked + 1))
			fi
		fi
	done

	shopt -s nullglob
	for img in "$i"/*.rev; do
		dir="$(dirname "$img")"
		i=0

		mv "$img" "$(printf "$dir/%0.3d.gif" $i)"
		i=$((i + 1))
	done

	echo >> .checked
done

echo 'Checked: '"$checked"' files'
echo 'Removed: '"$removed"' files'
echo 'Failed: '"$failed"' files'

echo 'Do you want to commit those files? (s/n)'
read -r commit

while [[ "$commit" != "s" ]] && [[ "$commit" != "n" ]]; do
	echo 'Please write either ''s'' or ''n'''
	read -r commit
done

if [[ "$commit" == "s" ]]; then
	git add .
	git commit -m "Update"
	git push
fi