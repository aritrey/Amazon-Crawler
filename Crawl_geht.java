
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

public class Crawl_geht {
	
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
		
		
		String neu =".aok-inline-block.zg-item";
		ArrayList<String> neuList = new ArrayList<>();
		Elements neuElements=page.select(neu);
		for (Element e:neuElements) neuList.add(e.text()); 	//iteration over the elements. we only need the text
		for (int i = 0;i < neuList.size(); i++) {  			//Iteration over the number of books
		//	System.out.println("\n\n " + neuList.get(i));
		}
		
		
		//produce a string array to iterate over it 
		String[] informationType = {title, author, price, capa, ranking, bewertungCount};
		
		
		//ArrayLists to store all information of the books 
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
		int add=0;
		for(int i=0; i<informationType.length; i++) {
			ArrayList<String> List = new ArrayList<>();
				bookElements =page.select(informationType[i]);
				for(int k=0; k<bookElements.size(); k++) {
					String s=bookElements.get(k).text();
					while(!neuList.get(i+add).contains(s)) {
						List.add("none");
						add++;
					}
						List.add(s);
					}
				}
				
	
		
		//test if the Scraper actually works 
			//int add=0;												//variable to pay attention to special information (release date)
			for (int i = 0;i < all.get(0).size(); i++) {  			//Iteration over the number of books
				System.out.println("Title: " + all.get(0).get(i));	//prints the title
				System.out.println("Author: " + all.get(1).get(i));	//prints the author
				System.out.println("Price: " + all.get(2).get(i));	//prints the price
				System.out.println("Cover: " + all.get(3).get(i));	//prints the cover type
				
				/*Because book numbers 24 and 39 have different information (no Number of Reviews and Rating , but Releasedate), 
				 *I used the if-condition to filter the information.
				 */
				/*if(i==23||i==38) {
					System.out.println("Releasedate: " + releaseDateList.get(add)+ "\n(no reviews until now)"); //prints the releasedate for book 24 and 39
					add++;
				}else {*/
				System.out.println("Number of Reviews: " + all.get(4).get(i));
				System.out.println("Rating: " + all.get(5).get(i));
				}
				/*System.out.print(releaseDateList.size());
				System.out.print(all.get(4).size());*/
				
				System.out.printf("\n \n");;						//new line between the books
			}
			//System.out.print(releaseDateList.size());
			//System.out.print(all.get(4).size());*/
	}
//}

				
	