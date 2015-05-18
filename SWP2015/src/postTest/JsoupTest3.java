package postTest;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupTest3 {
	
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; rv:38.0) Gecko/20100101 Firefox/38.0";

	public static void main(String[] args) throws Exception {		

	
		Response response = Jsoup.connect("https://www.faz.net/mein-faz-net/?redirectUrl=$boersens$/a/depot.cgi/membership/loginNoScript").userAgent(USER_AGENT).followRedirects(false)
				.data("loginURL", "/mein-faz-net/")
				.data("redirectURL", "/mein-faz-net/")
				.data("loginNameFAZ2", "swp2015")
				.data("passwordFAZ", "SWP-2015")
				.data("rememberMeFAZ", "")
				.data("submit", "Anmelden")
				.method(Method.POST)
				.execute();
		System.out.println(response.cookies());

		
		Document doc = Jsoup.connect("http://boersenspiel.faz.net/a/depot.cgi").cookies(response.cookies()).userAgent(USER_AGENT).method(Method.GET).followRedirects(true).get();

		System.out.println(doc.toString());
	
	}
	
}