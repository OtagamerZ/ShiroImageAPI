package api.controller;

import api.model.Anime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AnimeController {
	@RequestMapping(value = "/apis/da", method = RequestMethod.GET)
	public Anime Anime(@RequestParam(value = "name", defaultValue = "404") String name) {
		try {
			String url = "https://www.dreamanimes.com.br/anime-info/" + name;
			Document doc = Jsoup.connect(url).followRedirects(false).timeout(60000).get();

			List<String> fields = Arrays.asList(Arrays.copyOfRange(doc.getElementById("content").getElementsByClass("container").get(0).html().split("\n"), 8, 32));
			fields = fields.stream().map(s -> s.replaceAll(".*</b>", "").trim()).collect(Collectors.toList());
			fields.removeIf(s -> s.equalsIgnoreCase("<br>"));

			final String link = "https://www.dreamanimes.com.br/anime-info/" + name;
			final String description = doc.body().getElementById("pcontent").getAllElements().get(4).text();
			final String status = fields.get(3);
			final String author = fields.get(4).equals("?") ? "Desconhecido" : fields.get(4);
			final String studio = fields.get(5);
			final String[] genres = fields.get(0).split(", ");
			final float score = Float.parseFloat(fields.get(12).replace("</span>", "").replace("<span>", ""));
			final int episodes = Integer.parseInt(fields.get(1));
			final int ovas = Integer.parseInt(fields.get(2));
			final int year = Integer.parseInt(fields.get(7));

			return new Anime(link, description, status, author, studio, genres, score, episodes, ovas, year);
		} catch (IOException e) {
			return null;
		}
	}
}
