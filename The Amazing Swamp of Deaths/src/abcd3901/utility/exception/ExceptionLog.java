package abcd3901.utility.exception;

import java.awt.Dimension;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * A static class used to log exceptions
 * @author Arnaud Paré-Vogt
 *
 */
public class ExceptionLog {
	
	private static final Dimension SCROLL_PANE_SIZE = new Dimension(400,200);
	private static ArrayList<Exception> exceptionLog = new ArrayList<>();
	
	/**
	 * logs an exception to the log.
	 * @param e the exception
	 */
	public static void log(Exception e){
		if(e!=null){
			exceptionLog.add(e);
		}
	}
	
	/**
	 * Shows a small window with the logged exception in there
	 * @param mainFrame the frame to witch the dialog is attached
	 */
	public static void showException(Frame mainFrame){
		if(exceptionLog.isEmpty()){
			return;
		}
		JDialog frame = new JDialog(mainFrame);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		JTextArea txt = new JTextArea();
		txt.setEditable(false);
		
		for (Iterator<Exception> iterator = exceptionLog.iterator(); iterator.hasNext();) {
			Exception exception = iterator.next();
			txt.append(exception.toString()+"\n");
			
			StackTraceElement[] st = exception.getStackTrace();
			
			for (StackTraceElement stackTraceElement : st) {
				txt.append("\t"+"at : "+stackTraceElement.toString()+"\n");
			}
		}
		
		
		JScrollPane sp = new JScrollPane(txt);
		sp.setPreferredSize(SCROLL_PANE_SIZE);
		
		frame.add(sp);
		frame.pack();
		
		frame.setVisible(true);
	}
	
	

}
