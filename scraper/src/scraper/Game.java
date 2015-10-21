package scraper;

public class Game {
	String appID;
	String name;
	String date;
	String discount;
	String originalPrice;
	String price;
	String photoUrl;
	String gameUrl;
	String rating;
	String ratingDescription;
	
	public Game(String appID, String name, String date, String discount,
			String originalPrice, String price, String photoUrl, String gameUrl,
			String rating, String ratingDescription) {

		this.appID = appID;
		this.name = name;
		this.date = date;
		this.discount = discount;
		this.originalPrice = originalPrice;
		this.price = price;
		this.photoUrl = photoUrl;
		this.gameUrl = gameUrl;
		this.rating = rating;
		this.ratingDescription = ratingDescription;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getGameUrl() {
		return gameUrl;
	}

	public void setGameUrl(String gameUrl) {
		this.gameUrl = gameUrl;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getRatingDescription() {
		return ratingDescription;
	}

	public void setRatingDescription(String ratingDescription) {
		this.ratingDescription = ratingDescription;
	}
	
	
	
}
