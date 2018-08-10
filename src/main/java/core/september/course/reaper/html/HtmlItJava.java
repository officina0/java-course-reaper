package core.september.course.reaper.html;

import core.september.course.reaper.iface.Reaper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class HtmlItJava implements  Reaper {
	private Element articleBody;
	private String title;
	private String next;
	
	
	@Override
	public void init(String url) {
		try {
			Document doc = Jsoup.connect(url).get();
			Element article = doc.body().getElementById("content-article");
			this.title = doc.title();
			this.articleBody = article.getElementsByClass("content-text").get(0);
			this.next = getNext(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/*public HtmlItJava(String url) {
		this.init(url);
	}
	
	public HtmlItJava(String url) {
		this.init(url);
	}*/
	
	private String getNext(Document doc) {
		Elements nexts = doc.body().getElementsByClass("next");
		if (nexts.size() > 0) {
			Element next = nexts.first();
			Elements as = next.getElementsByClass("btn");
			if (as.size() > 0) {
				Element a = as.first();
				return a.attr("href");
			}
		}
		return null;
	}
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return this.title;
	}
	@Override
	public String getNext() {
		return this.next;
	}
	
	@Override
	public String getContent() {
		return this.articleBody.html();
	}
	
	 
}

	