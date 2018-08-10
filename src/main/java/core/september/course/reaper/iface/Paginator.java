package core.september.course.reaper.iface;

import java.io.IOException;

public interface Paginator {

	void page();

	void init(String indexPage, String path) throws IOException;

}