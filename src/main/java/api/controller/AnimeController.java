package api.controller;

import api.model.Anime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AnimeController {
	@RequestMapping(value = "/apis/da", method = RequestMethod.GET)
	public Anime Anime(@RequestParam(value = "name", defaultValue = "404") String name) {
		try {
			String url = "https://www.dreamanimes.com.br/anime-info/" + name;
			Document doc = Jsoup.connect(url).followRedirects(false).timeout(60000).get();

			List<String> fields = doc.body().getElementById("content").getElementsByClass("container").get(0).textNodes().stream().map(t -> t.getWholeText().trim()).filter(s -> !s.isEmpty()).collect(Collectors.toList());

			final String link = "https://www.dreamanimes.com.br/anime-info/" + name;
			final String description = doc.body().getElementById("pcontent").getAllElements().get(4).text();
			final String status = fields.get(2);
			final String author = fields.get(3).equals("?") ? "Desconhecido" : fields.get(4);
			final String studio = fields.get(4);
			final String[] genres = fields.get(0).split(", ");
			final float score = Float.parseFloat(doc.body().getElementById("content").getElementsByClass("container").get(0).getElementsByTag("span").text());
			final int episodes = Integer.parseInt(fields.get(1));
			final int year = Integer.parseInt(fields.get(6));

			return new Anime(link, description, status, author, studio, genres, score, episodes, year);
		} catch (IOException e) {
			return null;
		}
	}
}
