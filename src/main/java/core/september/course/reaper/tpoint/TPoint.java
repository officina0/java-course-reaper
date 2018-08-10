package core.september.course.reaper.tpoint;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator.Tag;

import core.september.course.reaper.iface.Reaper;

public class TPoint implements Reaper {
	private Element articleBody;
	private String title;
	private String next = null;
	
	private static List<String> nexts = new LinkedList<>();
	
	
	private void resolveImages(Elements input) {
		Elements select = input.select("a");
	    for (Element e : select){
	        String attr = e.attr("href");
	        if(attr != null) {
	    		e.attr("href", e.absUrl("href"));
	    	}
	        
	    }

	    select = input.select("img");
	    for (Element e : select){
	    	String attr = e.attr("src");
	    	if(attr != null) {
	    		e.attr("src", e.absUrl("src"));
	    	}
	        //e.attr("src", e.absUrl("src"));
	    }
	}

	@Override
	public void init(String url) {
		try {
			/*
			 * Document doc = Jsoup.connect(url).get(); Element article =
			 * doc.body().getElementById("content-article"); this.title = doc.title();
			 * this.articleBody = article.getElementsByClass("content-text").get(0);
			 * this.next = getNext(doc);
			 */

			Document doc = Jsoup.connect(url).get();
			this.title = doc.title();
			this.next = null;
			Element article = doc.body().getElementsByClass("middle-col").get(0);
			
			resolveImages(article.getAllElements());

			ListIterator<Element> iterator = article.getAllElements().listIterator();

			String[] removable = { "pre-btn", "print-btn", "nxt-btn", "bottomgooglead", "tutorial-menu", "cover","topgooglead" };
			String[] htmlRemovoable = {"Advertisements"};

			for (Element element : article.getAllElements()) {
				for (String remove : removable) {
					if (element.hasClass(remove)) {
						if(element.hasClass("nxt-btn")) {
							retrieveNext(element);
						}
						element.remove();
						break;
					}
				}
			}
			
			for (Element element : article.getAllElements()) {
				for (String remove : htmlRemovoable) {
					if (element.html().equalsIgnoreCase(remove)) {
						element.remove();
						break;
					}
				}
			}
			
			for (Element element : article.getAllElements()) {
				if (element.tag().getName().equalsIgnoreCase("script")) {
					element.remove();
					break;
				}
			}
			
			this.articleBody = article;

			//String source = article.html();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void retrieveNext(Element element) {
		Element tag = element.getElementsByTag("a").get(0);
		String ref = tag.attr("href");
		//this.next = null;
		if(!nexts.contains(ref)) {
			this.next = ref;
			nexts.add(ref);
		}
		
	}

	/*
	 * public HtmlItJava(String url) { this.init(url); }
	 * 
	 * public HtmlItJava(String url) { this.init(url); }
	 */


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
