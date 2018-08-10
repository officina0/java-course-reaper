package core.september.course.reaper.module;

import com.google.inject.AbstractModule;

import core.september.course.reaper.html.HtmlItJava;
import core.september.course.reaper.html.HtmlItMdWriter;
import core.september.course.reaper.html.HtmlItPaginator;
import core.september.course.reaper.iface.Paginator;
import core.september.course.reaper.iface.Reaper;
import core.september.course.reaper.iface.Writer;
import core.september.course.reaper.tpoint.TPoint;
import core.september.course.reaper.tpoint.TPointMdWriter;
import core.september.course.reaper.tpoint.TPointPaginator;

public class TPointModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(Reaper.class).toInstance(new TPoint());
		bind(Paginator.class).toInstance(new TPointPaginator());
		bind(Writer.class).toInstance(new TPointMdWriter());
		
	}

}
