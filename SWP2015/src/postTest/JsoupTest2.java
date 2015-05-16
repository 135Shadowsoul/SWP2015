package postTest;

import java.util.Map;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupTest2 {
	
	public static void main(String[] args) throws Exception {
		
		
//		// Initial Login
//		Response response = Jsoup.connect("https://www.tribalwars.net/index.php?action=login&show_server_selection=1")
//				.data("cookieexists", "false")
//				.data("user", "swp2015")
//				.data("hidden", "")
//				.data("password", "SWP-2015")
//				.data("login_submit_button", "")
//				.data("cookie", "true").
//				execute();
//		Map<String, String> cookies = response.cookies();
//
//		if (response.parse().toString().contains("Session expired"))
//			System.out.println("Session Expired");
//		else
//			System.out.println("noch da");
//
//		
//		// Server choise
//		Response response2 = Jsoup.connect("https://www.tribalwars.net/index.php?action=login")
//				.cookies(cookies).data("cookieexists", "true")
//				.data("user", "swp2015")
//				.data("password", "a6e0ab9b3a17470930887d4505526ab3ff22ce79")
//				.execute();
//
//		if (response2.parse().toString().contains("Session expired"))
//			System.out.println("Session Expired");
//		else
//			System.out.println("noch da");
//
//		cookies = response2.cookies();

		// Hier liegt das Problem. Nach response2 bin ich scheinbar nicht mehr eingelogt
		
		
		Response res = Jsoup.connect("http://email.tribalwars.net/c/eJwtTs1qBCEYexrntuL_6sFDod1bjz30VNRPd2SdUXTKvH4dKARCQhICNjKt9JLtB_1un_V1I4SwXG5fCQkSd03AUwxjWe3dUe9B-EQEDVyy5MBpp5gXxqQAfCl2PY6G-Btij4mrjI-evSun6wPv8Zhuqc-847bO3ON3xI74-zgbI1Qippob46wdpqk9FdI4o5QPFHiCEIkhXIIMd9DezfRWe_zZXC5j6fZ_5JLz-PNiHOr2B7-aRY4")
				.execute();
		
		Map<String, String> cookies = res.cookies();
		
		System.out.println(cookies.toString());
		
		
		// Attack Form
		Response response3 = Jsoup.connect("http://en80.tribalwars.net/game.php?village=17099&try=confirm&screen=place").userAgent("Mozilla")
				.cookies(cookies).data("cookieexists", "true")
				.data("240e8e3def6737f8e9dc7f", "31e8782c240e8e")
				.data("template_id", "")
				.data("unit_input_spear", "15")
				.data("unit_input_sword", "0")
				.data("unit_input_axe", "0")
				.data("unit_input_archer", "0")
				.data("unit_input_spy", "0")
				.data("unit_input_light", "0")
				.data("unit_input_marcher", "0")
				.data("unit_input_heavy", "0")
				.data("unit_input_ram", "0")
				.data("unit_input_catapult", "0")
				.data("unit_input_knight", "0")
				.data("unit_input_snob", "0")
				.data("inputx", "")
				.data("inputy", "")
				.data("checked", "coord")
				.data("target_type", "village_name")
				.data("radio", "player_name")
				.data("input", "672|552")
				.data("target_attack", "attack")
				.execute();

		cookies.putAll(response3.cookies());;
		System.out.println(cookies.toString());

		if (response3.parse().toString().contains("Session expired"))
			System.out.println("Session Expired");
		else
			System.out.println("noch da");
		
		System.out.println(response3.parse().toString());

		
		// Attack
		Document doc = Jsoup.connect("http://en80.tribalwars.net/game.php?village=17099&action=command&h=79644868&screen=place").userAgent("Mozilla")
				.cookies(cookies)
				.data("cookieexists", "true")
				.data("submit", "Send attack")
				.data("spear", "0")
				.data("sword", "0")
				.data("axe", "0")
				.data("archer", "5")
				.data("spy", "0")
				.data("light", "0")
				.data("marcher", "0")
				.data("heavy", "0")
				.data("ram", "0")
				.data("catapult", "0")
				.data("knight", "0")
				.data("snob", "0")
				.get();

		if (doc.toString().contains("Session expired"))
			System.out.println("Session Expired");
		else
			System.out.println("noch da");

		System.out.println(doc.toString());

	}
	
	
//	Map<String, String> cookies2 = new HashMap<String, String>();
//	cookies2.put("ref1083524", "start");
//	cookies2.put("global_village_id", "37110");
//	cookies2.put("cid", "2038736802");
//	cookies2.put("sid", "0%3A92e014be8d59");
}