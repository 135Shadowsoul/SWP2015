package postTest;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupTest3 {

	public static void main(String[] args) throws Exception {
		

		Document doc = Jsoup.connect("https://www.faz.net/mein-faz-net/?redirectUrl=$boersens$/a/depot.cgi").userAgent("Mozilla").get();

	
		Response response = Jsoup.connect("https://www.faz.net/mein-faz-net/?redirectUrl=$boersens$/a/depot.cgi/membership/loginNoScript").userAgent("Mozilla")
				.data("inputURL", "")
				.data("redirectURL", "$boersens$/a/depot.cgi")
				.data("loginNameFAZ2", "swp2015")
				.data("passwordFAZ", "SWP-2015")
				.data("rememberMeFAZ", "")
				.data("submit", "Anmelden")
				.method(Method.POST)
				.execute();
		System.out.println(response.cookies());

		
		doc = Jsoup.connect("http://boersenspiel.faz.net/a/depot.cgi").data("cookiesExist", "ture").cookies(response.cookies()).get();
		System.out.println(doc.toString());

	
	}
	
}
