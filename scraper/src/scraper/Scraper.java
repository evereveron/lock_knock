package scraper;

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
		
		if(!file.exists()) {
			file.createNewFile();
		}
		
		WritableWorkbook workbook;
		workbook = Workbook.createWorkbook(file);
		WritableSheet sheet = workbook.createSheet("Steam Store", 0);
		
		
		//create reusable font formatter
		WritableFont boldLabel = new WritableFont(WritableFont.TAHOMA, 12, WritableFont.BOLD);
		WritableCellFormat boldFormat = new WritableCellFormat(boldLabel);
		
		//labels for store sheet
		Label appIDLabel = new Label(0, 0, "App ID", boldFormat);
		sheet.addCell(appIDLabel);
		
		Label nameLabel = new Label(1, 0, "Name", boldFormat);
		sheet.addCell(nameLabel);
		
		Label dateLabel = new Label(2, 0, "Release Date", boldFormat);
		sheet.addCell(dateLabel);
		
		Label discountLabel = new Label(3, 0, "Discount", boldFormat);
		sheet.addCell(discountLabel);
		
		Label originalPriceLabel = new Label(4, 0, "Original Price", boldFormat);
		sheet.addCell(originalPriceLabel);
		
		Label priceLabel = new Label(5, 0, "Price", boldFormat);
		sheet.addCell(priceLabel);
		
		Label photoLabel = new Label(6, 0, "Photo Url", boldFormat);
		sheet.addCell(photoLabel);
		
		Label gameUrlLabel = new Label(7, 0, "Game Url", boldFormat);
		sheet.addCell(gameUrlLabel);
		
		Label ratingLabel = new Label(8, 0, "Rating", boldFormat);
		sheet.addCell(ratingLabel);
		
		Label numReviewsLabel = new Label(9, 0, "Review Description", boldFormat);
		sheet.addCell(numReviewsLabel);
		
		//first find number of pages
		try {
			Document doc2 = Jsoup.connect("http://store.steampowered.com/search").get();
			Element search = doc2.select("div.search_pagination_right").first();
			
			Elements atags = search.getAllElements();
			Element lastPageTag = atags.get(3);
			
			totalPageNumber = Integer.parseInt(lastPageTag.text());
			System.out.println("total pages = " + totalPageNumber);
			System.out.println("");
			
		} catch (Exception e) {
			System.out.println("error getting page numbers");
		}
		
		for(;pageNumber <= 1; pageNumber++) {
			
		
			try {
				Document doc = Jsoup.connect("http://store.steampowered.com/search#page=" + 2).get();
				Elements results = doc.getElementsByClass("search_result_row");
				
				for(Element result : results) {
					//grab elements
					Element name = result.select("div.search_name").first();
					Element date = result.select("div.search_released").first();
					Element price = result.select("div.search_price").first();
					Element discount = result.select("div.search_discount").first();				
					Element photo = result.select("img[src~=(?i)\\.(png|jpe?g|gif)]").first();
					Element rating = result.select("span.search_review_summary").first();
						
					//get text
					String appid = result.attr("data-ds-appid");
					String gameName = name.text();
					String gameDate = date.text();
					String gamePrice = price.text();
					String gameDiscount = discount.text();
					String gamePhoto = photo.attr("src");
					String url = result.attr("href");
					String ratingText = "";
					
					if(rating != null) {
						ratingText = rating.attr("data-store-tooltip");
					}
					
					//format rating
					String ratingInfo[] = ratingText.split("<br>");
					String ratingSummary = ratingText;
					String ratingDescription = "";
					if(ratingInfo.length ==2){
						ratingSummary = ratingInfo[0];
						ratingDescription = ratingInfo[1];
					}
					
					//format prices
					String OPrice = "";
					String APrice = gamePrice;
					String[] prices = gamePrice.split(" ");
					if(prices.length == 2) {
						OPrice = prices[0];
						APrice = prices[1];
					}
					
					//create a new game and add to list
					Game game = new Game(appid, gameName, gameDate, gameDiscount, OPrice, APrice, gamePhoto, url, ratingSummary, ratingDescription);
					gameList.add(game);
	
					System.out.println(appid + ": " + gameName + " - " + gameDate + " - " + gamePrice + " (" + gameDiscount + ")");
					System.out.println("photo url: " + gamePhoto);
					System.out.println("rating summary: " + ratingSummary + ", rating description: " + ratingDescription);
					System.out.println("");
						
				}
				
				
				
				/*
				Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");  
	            for (Element image : images) {  
	                System.out.println("src : " + image.attr("src"));  
	                System.out.println("height : " + image.attr("height"));  
	                System.out.println("width : " + image.attr("width"));  
	                System.out.println("alt : " + image.attr("alt"));  
	            } 
	            */ 
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		System.out.println("now add to excel");
		//i is declared up top so that we loop through the pages
		int gameNum =0;
		for(int i=0; i < gameList.size(); i++) {
			//go down the cells
			Game cur = gameList.get(gameNum);
			
			for(int j=0; j < 10; j++) {
				//go across the cells
				switch(j) {
				//add to the correct column
				case 0:
					Label aid = new Label(j, i +1, cur.getAppID());
					sheet.addCell(aid);
					break;
				case 1:
					Label gn = new Label(j, i +1, cur.getName());
					sheet.addCell(gn);
					break;
				case 2:
					Label gd = new Label(j, i+1, cur.getDate());
					sheet.addCell(gd);
					break;
				case 3:
					Label gdi = new Label(j, i+1, cur.getDiscount());
					sheet.addCell(gdi);
					break;
				case 4:
					Label gp = new Label(j, i+1, cur.getPrice());
					sheet.addCell(gp);
					break;
				case 5:
					Label gp2 = new Label(j, i+1, cur.getOriginalPrice());
					sheet.addCell(gp2);
					break;
				case 6:
					Label gph = new Label(j, i+1, cur.getPhotoUrl());
					sheet.addCell(gph);
					break;
				case 7:
					Label gurl = new Label(j, i+1, cur.getGameUrl());
					sheet.addCell(gurl);
					break;
				case 8:
					Label gr = new Label(j, i+1, cur.getRating());
					sheet.addCell(gr);
					break;
				case 9:
					Label grd = new Label(j, i+1, cur.getRatingDescription());
					sheet.addCell(grd);
					break;
				default:
					Label error = new Label(j, i+1, "error");
					sheet.addCell(error);
				}
				

			}
			gameNum++;
		}
		
		//close excel
		workbook.write();
		workbook.close();
	}

}
