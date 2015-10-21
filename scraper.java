import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class scraper {
	public static void main(String[] args) {
		System.out.println("hi");
		try {
			Document doc = Jsoup.connect("http://store.steampowered.com/search").get();
			String title = doc.title();
			System.out.println("title is " + title);
		} catch (Exception e) {
			System.out.println("error");
		}
		
	}
}