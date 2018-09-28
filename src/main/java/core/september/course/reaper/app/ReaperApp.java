package core.september.course.reaper.app;

import java.io.IOException;

import javax.script.ScriptException;

import com.google.inject.Guice;
import com.google.inject.Injector;

import core.september.course.reaper.iface.Paginator;
import core.september.course.reaper.iface.Reaper;
import core.september.course.reaper.iface.Writer;
import core.september.course.reaper.module.HtmlITModule;
import core.september.course.reaper.module.TPointModule;

public class ReaperApp {

    private  final String path = "/home/christian/Scrivania/corsoJAVANew";
    private final static String url = "http://www.html.it/pag/15096/introduzione-a-java/";
    private final static String pageUrl = "http://www.html.it/guide/guida-java/";
    private final static boolean pageOnly = true;
	
	/*private  final String path = "/home/christian/Scrivania/corsoAngularTP";
    private final static String url = "https://www.tutorialspoint.com/angular4/index.htm";
    private final static String pageUrl = "https://www.tutorialspoint.com/angular4/index.htm";
    private final static boolean pageOnly = false;*/
	
    private  int counter;
    private static Injector injector;
  
    public static void main(String[] args) throws IOException, ScriptException, InterruptedException {
    	
    	injector = Guice.createInjector(new HtmlITModule());

        

        ReaperApp app = new ReaperApp(1);
        
       
        Reaper first = injector.getInstance(Reaper.class);
        if(!pageOnly) {
        	first.init(url);
        }
        

        app.doNext(first);

    }

    public ReaperApp(int counter) {
        this.counter = counter;
    }
    
    private void paginate() throws IOException {
    	 Paginator paginator = injector.getInstance(Paginator.class);
		 paginator.init(pageUrl, path);
         paginator.page();
    }

    public  void doNext(Reaper reaper) throws IOException, ScriptException, InterruptedException {
    	if(pageOnly) {
    		paginate();
    	}
    	
    	else {
    		
    		
    		
    		 Writer writer = injector.getInstance(Writer.class);
			 writer.init(path,reaper,counter);
			 writer.write();
    			
            
            
            if(reaper.getNext() != null) {
                counter+=1;
                Reaper next = injector.getInstance(Reaper.class);
                next.init(reaper.getNext());
                doNext(next);
            }
            
            else {
            	 //paginate();
            }
    	}
        
    }
}
