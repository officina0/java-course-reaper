package core.september.course.reaper.iface;

public interface Reaper {
	public void init(String url);
	public String getTitle();
	public String getNext();
	public String getContent();
}