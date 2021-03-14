/*
 * This file is part of Shiro J Bot.
 *
 *     Shiro J Bot is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Shiro J Bot is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Shiro J Bot.  If not, see <https://www.gnu.org/licenses/>
 */

package api.handler;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ReactionEndpoint {
	private static final String BASE_PATH = "https://raw.githubusercontent.com/OtagamerZ/ShiroImageAPI/master/src/main/resources/reactions/%s/%s";

	@RequestMapping(value = "/reaction", method = RequestMethod.GET)
	public ResponseEntity<byte[]> reaction(@RequestParam(value = "type", defaultValue = "") String type, @RequestParam(value = "id", defaultValue = "") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			if (type.isBlank()) {
				URL pageUrl = this.getClass().getClassLoader().getResource("template.html");
				URL reactUrl = this.getClass().getClassLoader().getResource("reactions");
				if (pageUrl == null || reactUrl == null) throw new IllegalArgumentException();

				File reactDir = new File(reactUrl.toURI());
				if (!reactDir.isDirectory()) throw new IllegalArgumentException();

				String page = Files.readString(Path.of(pageUrl.toURI()), StandardCharsets.UTF_8);
				String item = "<li><a href=\"%s\">%s</a></li>\n";
				StringBuilder sb = new StringBuilder();

				String[] available = reactDir.list();
				if (available == null) available = new String[0];
				for (String s : available) {
					sb.append(item.formatted("?type=" + s, s));
				}

				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "text/html");

				return new ResponseEntity<>(
						page.formatted(sb.toString()).getBytes(StandardCharsets.UTF_8),
						headers,
						HttpStatus.OK
				);
			}

			URL path = this.getClass().getClassLoader().getResource("reactions/" + type);
			if (path == null) throw new IllegalArgumentException();

			File[] content = new File(path.getFile()).listFiles();
			assert content != null;
			List<File> reactions = Arrays.stream(content)
					.filter(f -> f.isFile() && f.getName().endsWith(".gif"))
					.sorted()
					.collect(Collectors.toList());

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "image/gif");

			try {
				int index = Integer.parseInt(id);
				if (index < 0 || index >= reactions.size()) throw new NumberFormatException();

				return new ResponseEntity<>(
						Files.readAllBytes(reactions.get(index).toPath()),
						headers,
						HttpStatus.OK
				);
			} catch (NumberFormatException e) {
				int index = new Random().nextInt(reactions.size());

				response.sendRedirect(request.getRequestURI() + "?type=" + type + "&id=" + index);
				return new ResponseEntity<>(
						Files.readAllBytes(reactions.get(index).toPath()),
						headers,
						HttpStatus.TEMPORARY_REDIRECT
				);
			}
		} catch (IllegalArgumentException | IOException | URISyntaxException e) {
			URL path = this.getClass().getClassLoader().getResource("reactions/notfound");
			assert path != null;

			File[] files = new File(path.getFile()).listFiles();
			assert files != null;

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "image/gif");

			return new ResponseEntity<>(
					Files.readAllBytes(files[0].toPath()),
					headers,
					HttpStatus.OK
			);
		}
	}
}
