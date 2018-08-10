package core.september.course.reaper.tpoint;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.script.ScriptException;

import core.september.course.reaper.converter.HtmlToMd;
import core.september.course.reaper.iface.Reaper;
import core.september.course.reaper.iface.Writer;

public class TPointMdWriter implements Writer{

	private Reaper reaper;
	private File file;
	private Integer counter;

	

	/*public HtmlItMdWriter(String path, Reaper reaper, int counter) {
		this.counter = counter;
		init(path, reaper);

	}*/

	@Override
	public void init(String path, Reaper reaper,int counter) {
		this.counter = new Integer(counter);
		this.reaper = reaper;
		String title = reaper.getTitle().split("\\|")[0];

		file = new File(path.concat("/").concat(String.format("%03d", this.counter) + "-").concat(title.replaceAll("/", " "))
				.concat(".md"));
		file.getParentFile().mkdirs();
	}

	@Override
	public  void write() {
		
		FileWriter writer = null;
		try {

			writer = new FileWriter(file);
			HtmlToMd converter = new HtmlToMd();
			//String[] contents = reaper.getContent().split("Tutte le lezioni");
			/*String re1="\\\\";	// Any Single Character 1
		    String re2="\\[";	// Any Single Character 2
		    String re3="\\]";
		    
		    Pattern p = Pattern.compile(re1+re2,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		    Pattern p2 = Pattern.compile(re1+re3,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		    
			String content = reaper.getContent().replaceAll(p.pattern(), "[").replaceAll(p2.pattern(), "]");*/
			writer.write(converter.convert(reaper.getContent()));
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}


}
