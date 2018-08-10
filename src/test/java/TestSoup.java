import core.september.course.reaper.converter.HtmlToMd;
import core.september.course.reaper.html.HtmlItJava;
import core.september.course.reaper.html.HtmlItPaginator;
import core.september.course.reaper.iface.Paginator;
import core.september.course.reaper.tpoint.TPoint;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Assert;
import org.junit.Test;

import com.google.inject.spi.Elements;

import javax.script.ScriptException;
import java.io.IOException;
import java.util.ListIterator;

public class TestSoup {




    @Test
    public void testPage() throws IOException {
        HtmlItJava first = new HtmlItJava();
        first.init("http://www.html.it/pag/15096/introduzione-a-java/");
        Assert.assertNotNull(first);

    }

    @Test
    public void testConversion() throws IOException, ScriptException {
        HtmlToMd htmlToMd = new HtmlToMd();
        String converted = htmlToMd.convert("<h2>Pluto</h2>");
        Assert.assertEquals("Pluto\n-----",converted);

    }

    @Test
    public void testBigConversion() throws IOException, ScriptException {
        Document doc = Jsoup.connect("http://www.html.it/pag/15096/introduzione-a-java/").get();
        Element article = doc.body().getElementById("content-article");
        Element articleBody = article.getElementsByClass("content-text").get(0);
        String source = articleBody.html();

        HtmlToMd htmlToMd = new HtmlToMd();
        String converted = htmlToMd.convert(source);
        Assert.assertNotNull(converted);

    }
    
    @Test
    public void testTutorialsPoint() throws IOException, ScriptException {
       TPoint point = new TPoint();
       point.init("https://www.tutorialspoint.com/angular4/angular4_components.htm");
       System.out.println(point.getContent());

    }
    
   
}
