package postTest;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupTest3 {

	public static void main(String[] args) throws Exception {
		

		Document doc = Jsoup.connect("https://www.faz.net/mein-faz-net/").userAgent("Mozilla").get();

	
		Response response = Jsoup.connect("https://www.faz.net/mein-faz-net/membership/loginNoScript").userAgent("Mozilla")
				.data("inputURL", "")
				.data("redirectURL", "$boersens$/a/depot.cgi")
				.data("loginNameFAZ2", "swp2015")
				.data("passwordFAZ", "SWP-2015")
				.data("rememberMeFAZ", "")
				.data("submit", "Anmelden")
				.method(Method.POST)
				.execute();
		System.out.println(response.cookies());

		
		doc = Jsoup.connect("https://www.faz.net/mein-faz-net/meine-beitraege/").cookies(response.cookies()).get();

		if (doc.toString().contains("swp gruppe"))
			System.out.println(doc.toString());
		else System.out.println("nix da");

	
	}
	
}