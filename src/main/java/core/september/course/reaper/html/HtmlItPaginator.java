package core.september.course.reaper.html;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import core.september.course.reaper.iface.Paginator;

public class HtmlItPaginator implements Paginator {

	private Document doc;
	private String path;
	
	/*public HtmlItPaginator(String indexPage,String path) throws IOException {
		this.init(indexPage, path);
	}*/
	
	
	@Override
	public void init(String indexPage,String path) throws IOException {
		this.doc = Jsoup.connect(indexPage).get();
		this.path = path;
	}
	
	@Override
	public void page() {
		Elements pages = doc.body().getElementsByClass("guide-index__chapter");
		handlePages(pages);
	}

	private void handlePages(Elements pages) {
		//int counter = 0;
		final AtomicInteger counter = new AtomicInteger(0);
		for (Element page : pages) {
			
			Element ol = page.getElementsByTag("ol").get(0);
			Element mainTitle = page.getElementsByTag("span").first();
			Elements li = ol.getElementsByTag("li");
			
			pageWrite(mainTitle.html(),li.size(),counter.incrementAndGet());
			
			/*li.stream().forEach(eLi -> {
				Element title = eLi.getElementsByTag("h3").first();
				title.getElementsByTag("span").remove();
				//System.out.println(page.getElementsByTag("h3").get(0).html());
				//System.out.println(li.getElementsByTag("li").size());
				pageWrite(mainTitle.html(),1,counter.incrementAndGet());
			});*/

			
		}
		
	}

	private void pageWrite(String title, int size,int counter) {
		String parsedTitle = String.format("%02d-%s", counter, title.replaceAll(" ", "-")).replaceAll("/", " ");
		System.out.println(parsedTitle);
		File folder = new File(String.format("%s/%s", path,parsedTitle));
		folder.mkdirs();
		File pathd = new File(path);
		int prog = 0;
		File[] allFiles = pathd.listFiles();
		Arrays.sort(allFiles);
		for(File file:allFiles) {
			
			if(file.isFile() && prog < size) {
				try {
					Files.move(file.toPath(), new File(String.format("%s/%s", folder,file.getName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				prog++;
			}
		}
		
		
	}
}
