package core.september.course.reaper.converter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;

import javax.script.ScriptException;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.JavaVoidCallback;
import com.eclipsesource.v8.NodeJS;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.google.common.io.Resources;

import core.september.course.reaper.iface.Converter;

public class HtmlToMd implements Converter {

  

    final NodeJS nodeJs;
    private String input;
    private String output;


    public HtmlToMd() throws IOException, ScriptException {
        nodeJs = NodeJS.createNodeJS();
        JavaCallback takeFrom = new JavaCallback() {

            public Object invoke(V8Object receiver, V8Array parameters) {
                return HtmlToMd.this.input;
            }
        };

        JavaVoidCallback putTo = new JavaVoidCallback() {

            public void invoke(V8Object receiver, V8Array parameters) {
            	
            	String re1="\\\\";	// Any Single Character 1
    		    String re2="\\[";	// Any Single Character 2
    		    String re3="\\]";
    		    
    		    Pattern p = Pattern.compile(re1+re2,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    		    Pattern p2 = Pattern.compile(re1+re3,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    		    
    			String content = parameters.get(0).toString().replaceAll(p.pattern(), "[").replaceAll(p2.pattern(), "]");
            	
                HtmlToMd.this.output = content;
            }
        };

        nodeJs.getRuntime().registerJavaMethod(takeFrom, "takeFrom");
        nodeJs.getRuntime().registerJavaMethod(putTo, "putTo");

    }

    private void setInput(String input) {
        this.input = input;
    }
    
    private String getOutput() {
        return this.output;
    } 



    @Override
    public String  convert(String input) {

        setInput(input);

        URL resUrl = Resources.getResource("jsconverter/index.js");

        File script = new File(resUrl.getFile());
        nodeJs.exec(script);

        while(nodeJs.isRunning()) {
            nodeJs.handleMessage();
        }
        nodeJs.release();
        return getOutput();
    }


}
