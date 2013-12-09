import java.io.*;
import java.net.*;
import java.util.*;

public class Crawler {
//public class Crawler implements Runnable {
	private String[] websites;
	private int webCount;
	private String[] emails;
	private int emailCount;
	private static final int MAX_SIZE=150;
	
	public Crawler() {
		websites = new String[MAX_SIZE];
		for (int i=0;i<websites.length;i++) {
			websites[i]="";
		}
		webCount=0;
		emails = new String[MAX_SIZE];
		for (int i=0;i<emails.length;i++) {
			emails[i]="";
		}
		emailCount=0;
	}
	
    /*public void run(Spambot caller) {
		// get the latest URL from the URL Unread Queue
		String webAddress = caller.getNextURL();
		// now process the website
		crawlSite(webAddress);
		caller.putURLs(websites); // pass a string array	
		caller.putEmails(emails);
	}*/
	
	public static void main(String[] args) {
		Crawler script = new Crawler();
		script.crawlSite("http://www.oracle.com/");
	}
	
	public void crawlSite(String webAddress) {
		URL aURL;
		try {
			aURL = new URL(webAddress);      
		} catch (MalformedURLException ex) {
			System.out.println("website was not found");
			return;
		}
		BufferedReader in = null; // declare the buffered reader here so it can be accessed throughout the method
        try {
			in = new BufferedReader(new InputStreamReader(aURL.openStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				getSites(inputLine);
				//getEmails(inputLine);
			}
			in.close();
		} catch (FileNotFoundException ex) {
			System.out.println("File was not found");
		}  catch (IOException ex) {
				ex.printStackTrace();
		} finally {
			try {
				if (in != null) {
						in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public void getSites(String inputLine) {
		Scanner s = new Scanner(inputLine);
		s.findInLine("(\\d+) fish (\\d+) fish (\\w+) fish (\\w+)");
		MatchResult result = s.match();
		for (int i=1; i<=result.groupCount(); i++)
			System.out.println(result.group(i));
		s.close(); 
	}
	
	/*public void getEmails(String inputLine) {
	}*/
}