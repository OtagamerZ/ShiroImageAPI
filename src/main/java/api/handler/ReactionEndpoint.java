package api.handler;

import org.json.JSONObject;
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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@RestController
public class ReactionEndpoint {
	private static final String BASE_PATH = "https://raw.githubusercontent.com/OtagamerZ/ShiroImageAPI/master/src/main/resources/reactions/%s/%s";

	@RequestMapping(value = "/reaction", method = RequestMethod.GET)
	public String reaction(@RequestParam(value = "type", defaultValue = "") String type, HttpServletRequest res) {
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

				return page.formatted(sb.toString());
			}

			URL path = this.getClass().getClassLoader().getResource("reactions/" + type);
			if (path == null) throw new IllegalArgumentException();

			File[] content = new File(path.getFile()).listFiles();
			assert content != null;
			List<String> reactions = new ArrayList<>();
			for (File file : content) {
				if (file.isFile() && !file.getName().startsWith("."))
					reactions.add(file.getName());
			}
			Collections.sort(reactions);

			int index = new Random().nextInt(reactions.size());
			return new JSONObject() {{
				put("id", index);
				put("url", BASE_PATH.formatted(type, reactions.get(index)));
			}}.toString();
		} catch (IllegalArgumentException | IOException | URISyntaxException e) {
			return new JSONObject() {{
				put("id", 404);
				put("url", BASE_PATH.formatted("notfound", "000.gif"));
			}}.toString();
		}
	}
}
