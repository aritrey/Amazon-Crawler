

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

/*
For the WebScrawling project I used the webside of Amazon "Mais Vendidos em Livros".
The link to the page is: https://www.amazon.com.br/gp/bestsellers/books/ref=zg_bs_nav_0
*/

/*
/*Still to do: As not every book has a ranking, Number of Reviews and release date, we need to put it in the right order.
The idea is to create an arrayList with the whole information about one book in a string:

		String neu =".aok-inline-block.zg-item";
		ArrayList<String> neuList = new ArrayList<>();
		Elements neuElements=page.select(neu);
		for (Element e:neuElements) neuList.add(e.text()); 	//iteration over the elements. we only need the text

and compare later on if the current element has this information:

		for(int k=0; k<bookElements.size(); k++) {
			int add=i;
			while(!(neuList.get(add).contains(s))) {
				List.add("none");
				add++;}
			List.add(s);}
*/
public class Crawl {
	
	public static void main(String[] args) throws IOException {
		
		//get a connection to the page
		String url = "https://www.amazon.com.br/gp/bestsellers/books/ref=zg_bs_nav_0";		//link to the side
		Document page = Jsoup.connect(url).userAgent("Jsoup Scraper").get();
		
		//the strings to find the information of the books
		String title =".aok-inline-block.zg-item > .a-link-normal";												//title
		String ranking =".aok-inline-block.zg-item > .a-icon-row.a-spacing-none > .a-size-small.a-link-normal";	//amount of people who has already rated the bool
		String releaseDate =".aok-inline-block.zg-item > .a-row > .zg-release-date";							//release date (only if it is in the future)
		String author = ".aok-inline-block.zg-item > .a-row.a-size-small >  .a-size-small.a-color-base";		//author
		String bewertungCount= ".aok-inline-block.zg-item > .a-icon-row.a-spacing-none > .a-link-normal[title]";//amount of stars
		String price =".a-row >  .a-link-normal.a-text-normal > .a-size-base.a-color-price > .p13n-sc-price";	//price
		String capa= ".aok-inline-block.zg-item > .a-row.a-size-small > .a-size-small.a-color-secondary";		//cover type
		
		
		
		//produce a string array to iterate over it 
		String[] informationType = {title, author, price, capa, ranking, bewertungCount};
		
		
		//ArrayLists to store all information of the books 
		//the information of a book should have the same number i (all.get(x).get(i))
		//there are still some problems as the rating and the number of reviews is missing sometimes
		ArrayList<ArrayList<String>> all=new ArrayList<ArrayList<String>>();
		//store "special information" (only two elements have it)
		ArrayList<String> releaseDateList = new ArrayList<>();

		
		//create a new Element to store the URLs
		Elements bookElements=null;
		//element object stores the whole URL, 
		bookElements =page.select(releaseDate);
		for (Element e:bookElements) releaseDateList.add(e.text()); 	//iteration over the elements. we only need the text
		bookElements =page.select(bewertungCount);
		
		
		//iteration that stores information about title, author, price, capa, ranking and bewertungCount in the array
		for(int i=0; i<informationType.length; i++) {
			ArrayList<String> List = new ArrayList<>();
				bookElements =page.select(informationType[i]);
				for (Element e:bookElements) List.add(e.text());
				all.add(List);
		}
		
		//test if the Scraper actually works 											//variable to pay attention to special information (release date)
			for (int i = 0;i < all.get(0).size(); i++) {  			//Iteration over the number of books
				System.out.println("Title: " + all.get(0).get(i));	//prints the title
				System.out.println("Author: " + all.get(1).get(i));	//prints the author
				System.out.println("Price: " + all.get(2).get(i));	//prints the price
				System.out.println("Cover: " + all.get(3).get(i));	//prints the cover type
				System.out.printf("\n \n");
				
			}
		}
	}

				
	
