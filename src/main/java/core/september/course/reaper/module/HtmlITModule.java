package core.september.course.reaper.module;

import com.google.inject.AbstractModule;

import core.september.course.reaper.html.HtmlItJava;
import core.september.course.reaper.html.HtmlItMdWriter;
import core.september.course.reaper.html.HtmlItPaginator;
import core.september.course.reaper.iface.Paginator;
import core.september.course.reaper.iface.Reaper;
import core.september.course.reaper.iface.Writer;

public class HtmlITModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(Reaper.class).toInstance(new HtmlItJava());
		bind(Paginator.class).toInstance(new HtmlItPaginator());
		bind(Writer.class).toInstance(new HtmlItMdWriter());
		
	}

}
