import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class Crawler {
//public class Crawler implements Runnable {
	private String[] websites;
	private String[] emails;
	private static final int MAX_SIZE=250;
	private static final String emailRegex="[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";
	private static final String URLRegex="\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	
	public Crawler() {
		websites = new String[MAX_SIZE];
		for (int i=0;i<websites.length;i++) {
			websites[i]="";
		}
		emails = new String[MAX_SIZE];
		for (int i=0;i<emails.length;i++) {
			emails[i]="";
		}
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
		script.crawlSite("http://www.bbk.ac.uk/alumni/contact-us"); 
	}
	
	public void crawlSite(String webAddress) {
		URL aURL;
		try {
			aURL = new URL(webAddress);      
		} catch (MalformedURLException ex) {
			System.out.println("website was not found");
			return;
		}
		getPatterns(aURL, websites, URLRegex);
		websites=stripArray(websites);
		getPatterns(aURL, emails, emailRegex);
		emails=stripArray(emails);
		printArray(emails); // this is to test the output - strip out of finished code
		printArray(websites); // this is to test the output - strip out of finished code
	}
	
	public String[] stripArray(String[] inputArray) {
		String[] tempArray = new String[MAX_SIZE];
		int count=0;
		for (int i=0;i<inputArray.length;i++) {
			if (inputArray[i]=="") {
				break;
			} else {
				tempArray[i]=inputArray[i];
				count++;
			}
		}
		inputArray = new String[count];
		for (int i=0;i<count;i++) {
			inputArray[i]=tempArray[i];
		}
		return inputArray;
	}
	
	public void printArray(String[] array) { // this method will not be used in the final code, purely here to test output
		for (int i=0;i<array.length;i++) {
			System.out.println(array[i]);
		}
	}
	
	public void getPatterns(URL aURL, String[] output, String regex) {
		BufferedReader in = null; // declare the buffered reader here so it can be accessed throughout the method
        int count=0;
		try {
			in = new BufferedReader(new InputStreamReader(aURL.openStream()));			
			Pattern pattern = Pattern.compile(regex);
 			Matcher m = pattern.matcher("");	
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				m.reset(inputLine);
				while (m.find()) {
					output[count]=m.group();
					count++;
				}
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
}