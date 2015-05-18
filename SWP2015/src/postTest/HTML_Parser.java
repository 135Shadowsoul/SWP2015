package postTest;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.FormElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HTML_Parser {
	String url = "";
	Connection conn = null;
	Response res = null;
	Document doc = null;
	Map<String, String> cookies = null;
	HashMap<String, String> userInput = null;

	public HTML_Parser(String URL, HashMap<String, String> userInput) {
		this.url = URL;
		this.userInput = userInput;
	}

	public static void main(String[] args) {
		HashMap<String, String> userInput = new HashMap<String, String>();
		userInput.put("username", "");
		userInput.put("password", "");
		HTML_Parser parser = new HTML_Parser("http://www.chefkoch.de/", userInput);

		try {
			// get form
			Response res = parser.read(parser.url);
			// gives list of all usable formIds

			userInput.put("suche", "Bratkartoffeln");

			// looks at form 0 (search form)
			// System.out.println(parser.getFormData(res, 0).toString());

			// send search form and print output
			res = parser.send(parser.getFormData(res, 0), userInput);
			// Document doc1 = res.parse();
			// System.out.println(doc1.toString());
			// System.out.println(doc1.getElementsByClass("overall-search-result-count"));

			// looks at all forms in the document of res
			// System.out.println(parser.getFormList(res).toString());

			// public HashMap<String,String> getObjects(Response res,
			// List<String> IDList)
			// List<String> IDList = new ArrayList<String>();
			// IDList.add("");
			// System.out.println(parser.getObjects(res, IDList));

		} catch (Exception e) {
		}
	}

	/*
	 * @param url: Website url to read.
	 * 
	 * @param cookies: Session cookies. Equals null if no cookies are given.
	 */
	public Response read(String url) {
		Connection conn1 = Jsoup.connect(url);
		Response res1 = null;
		if (cookies != null) {
			for (Entry<String, String> cookie : cookies.entrySet()) {
				conn1.cookie(cookie.getKey(), cookie.getValue());
			}
		}
		try {
			res1 = conn1.execute();
		} catch (Exception e) {
			System.out.println("nope");
		}

		return res1;
	}

	/*
	 * @param formData: Contains required inputs and url as action for form
	 */
	public Response send(HashMap<String, String> formData, HashMap<String, String> userInput) {
		Connection conn1 = Jsoup.connect(formData.get("action"));
		Response res1 = null;
		for (Map.Entry<String, String> keyIn : formData.entrySet()) {
			if (keyIn.getKey() != "action") {
				if (userInput.keySet().contains(keyIn.getKey())) {
					keyIn.setValue(userInput.get(keyIn.getKey()));
				}
				if (formData.get(keyIn) != "") {
					conn1.data(keyIn.getKey(), keyIn.getValue());
				}
			}
		}
		if (cookies != null) {
			for (Map.Entry<String, String> cookie : cookies.entrySet()) {
				conn1.data(cookie.getKey(), cookie.getValue());
			}
		}
		try {
			res1 = conn1.execute();
			cookies = res1.cookies();
		} catch (Exception e) {
		}
		return res1;
	}

	/*
	 * @param res: Contains the document to search through.
	 * 
	 * @param IDList: Part of userInput, specifies the elements to observe.
	 */
	public HashMap<String, String> getObjects(Response res, List<String> IDList) {
		HashMap<String, String> result = new HashMap<String, String>();
		try {
			Document doc1 = res.parse();
			for (int i = 0; i < IDList.size(); i++) {
				result.put(IDList.get(i), doc1.getElementById(IDList.get(i)).data());
			}
		} catch (Exception e) {
		}

		return result;
	}

	/*
	 * @param return: Gives a list of Id strings to address forms with
	 */
	public List<FormElement> getFormList(Response res) {
		List<FormElement> forms = new ArrayList<FormElement>();
		try {
			Document doc1 = res.parse();
			forms = doc1.getElementsByTag("form").forms();
		} catch (Exception e) {
		}

		return forms;
	}

	/*
	 * @param formNumber: Position of requested form in formList
	 * 
	 * @param return: null, if formNumber too big, HashMap of required inputs,
	 * else.
	 */
	public HashMap<String, String> getFormData(Response res, int formNumber) {
		HashMap<String, String> output = new HashMap<String, String>();
		try {
			Document doc1 = res.parse();
			List<FormElement> forms = doc1.getElementsByTag("form").forms();
			if (formNumber >= forms.size())
				return null;
			List<Connection.KeyVal> dataList = forms.get(formNumber).formData();
			String key = "";
			String value = "";
			for (int i = 0; i < dataList.size(); i++) {
				key = dataList.get(i).key();
				value = dataList.get(i).value();
				output.put(key, value);
			}
			// makes sure that action address is always usable
			if (!forms.get(formNumber).attr("action").contains("http")) {
				output.put("action", url.substring(0, url.length() - 1) + forms.get(formNumber).attr("action"));
			} else {
				output.put("action", forms.get(formNumber).attr("action"));
			}
			if (forms.get(formNumber).attr("method") != "") {
				output.put("method", forms.get(formNumber).attr("method"));
			}
		} catch (Exception e) {
		}

		return output;
	}
}
