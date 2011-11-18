package com.cardlytics.scm.Yui;

import java.nio.charset.Charset;
import java.util.Vector;
import java.io.Writer;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import java.util.Iterator;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

public class YuiTask extends Task {

    private Vector<FileSet> filesets = new Vector<FileSet>();
    private File minfile;
    private String charset;
    private boolean munge;
    private boolean verbose;
    private int linebreakpos = -1;
    private boolean preserveAllSemiColons;
    private boolean disableOptimizations;

    public void setMunge(boolean value){
	   this.munge = value;
    }
    
    public void setVerbose(boolean value){
	   this.verbose = value;
    }

    public void setLinebreakpos(String val){
      String linebreakstr = (String) val;
      if (linebreakstr != null) {
        try {
             linebreakpos = Integer.parseInt(linebreakstr, 10);
        } catch (NumberFormatException e) {
             throw new BuildException(e);
        }
      }
    }
   
    public void setPreserveAllSemiColons(boolean value){
	   this.preserveAllSemiColons = value;
    }
    
    public void setDisableOptimizations(boolean value){
	    this.disableOptimizations= value;
    }

    public void addFileSet(FileSet fileset) {
      if (!filesets.contains(fileset)) {
         filesets.add(fileset);
      }
    }

    public void setMinfile(String filename){
      minfile = new File(filename);
    }
    
    public void setCharset(String charset){
      this.charset = charset;
    }
    
    public void execute() {
      int filesProcessed = 0;
      if (charset == null || !Charset.isSupported(charset)) {
         charset = "UTF-8";
      }
      Writer out = null;
      for (FileSet fileset : filesets) {
         DirectoryScanner ds = fileset.getDirectoryScanner(getProject());
         File dir = ds.getBasedir();
         String[] filesInSet = ds.getIncludedFiles();
         for (String filename : filesInSet) {
	    try{
               File file = new File(dir,filename);
               System.out.println("working on... " + file.toString());
	       InputStreamReader in = new InputStreamReader(new FileInputStream(file),charset);
	       try{
                  JavaScriptCompressor compressor = new JavaScriptCompressor(in, new ErrorReporter() {
		    public void warning(String message, String sourceName,
			    int line, String lineSource, int lineOffset) {
			    if (line < 0) {
				    System.err.println("\n[WARNING] " + message);
			    } else {
				    System.err.println("\n[WARNING] " + line + ':' + lineOffset + ':' + message);
			    }
		    }
		    
		    public void error(String message, String sourceName,
			    int line, String lineSource, int lineOffset) {
			    if (line < 0) {
				    System.err.println("\n[ERROR] " + message);
			    } else {
				    System.err.println("\n[ERROR] " + line + ':' + lineOffset + ':' + message);
			    }
		    }
		    
		    public EvaluatorException runtimeError(String message, String sourceName,
			    int line, String lineSource, int lineOffset) {
			    error(message, sourceName, line, lineSource, lineOffset);
			    return new EvaluatorException(message);
		    }
	          });
	    
	          // Close the input stream first, and then open the output stream,
                  // in case the output file should override the input file.
	          in.close(); in = null;

                  out = new OutputStreamWriter(new FileOutputStream(minfile,true), charset);

                  compressor.compress(out, linebreakpos, munge, verbose,
			    preserveAllSemiColons, disableOptimizations);

	          filesProcessed++;
	       } catch (EvaluatorException e) {
		   e.printStackTrace();
		   throw new BuildException(e);
	       }
	    }catch(IOException ioe){
	       throw new BuildException(ioe);
	    }
	 }
      }
      log("Done. "+filesProcessed+" file(s) processed to minfile " + minfile.toString() +".");
    }
}
