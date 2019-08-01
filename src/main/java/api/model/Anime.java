package api.model;

public class Anime {
	private String url, description, status, author, studio;
	private String[] genres;
	private float score;
	private int episodes, ovas, year;

	public Anime(String url, String description, String status, String author, String studio, String[] genres, float score, int episodes, int ovas, int year) {
		this.url = url;
		this.description = description;
		this.status = status;
		this.author = author;
		this.studio = studio;
		this.genres = genres;
		this.score = score;
		this.episodes = episodes;
		this.ovas = ovas;
		this.year = year;
	}

	public String getUrl() {
		return url;
	}

	public String getDescription() {
		return description;
	}

	public String getStatus() {
		return status;
	}

	public String getAuthor() {
		return author;
	}

	public String getStudio() {
		return studio;
	}

	public String[] getGenres() {
		return genres;
	}

	public float getScore() {
		return score;
	}

	public int getEpisodes() {
		return episodes;
	}

	public int getOvas() {
		return ovas;
	}

	public int getYear() {
		return year;
	}
}
