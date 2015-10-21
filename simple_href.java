import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {

    private static File file = new File("steam.xls");
    private static ArrayList<Game> gameList = new ArrayList<Game>();
    private static int pageNumber = 1;
    private static int totalPageNumber = 1;

    public static void main(String[] args) throws IOException, RowsExceededException, WriteException {

        Document doc = Jsoup.connect("http://store.steampowered.com/search/").get();
        Elements link = doc.select("div.search_pagination_right a");
        String href = link.attr("href");
        System.out.println(link);
        System.out.println(href);

        doc = Jsoup.connect(href).get();
        link = doc.select("span.title");
        System.out.println(link);
    }

}
