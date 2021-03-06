package cs131.pa1.filter.concurrent;

import java.io.File;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;

public class CdFilter extends ConcurrentFilter  implements Runnable{
	private String dirToSet;
	
	public CdFilter(String line) throws Exception {
		super();
		dirToSet = ConcurrentREPL.currentWorkingDirectory;
		String[] args = line.trim().split(" ");
		if(args.length == 1) {
			System.out.printf(Message.REQUIRES_PARAMETER.toString(), line.trim());
			throw new Exception();
		}
		if(args[1].equals("..")) {
			String current = ConcurrentREPL.currentWorkingDirectory;
			current = current.substring(0, current.lastIndexOf(Filter.FILE_SEPARATOR));
			dirToSet = current;
		} else if (!args[1].equals(".")) {
			String current = ConcurrentREPL.currentWorkingDirectory;
			current = current + Filter.FILE_SEPARATOR + args[1];
			File test = new File(current);
			if (test.isDirectory()) {
				dirToSet = current;
			} else {
				System.out.printf(Message.DIRECTORY_NOT_FOUND.toString(), line);
				throw new IllegalArgumentException();
			}
		}
	}
	
	public void process() {
		processLine("");
		done = true;
		try {
			output.put("poison_pill");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String processLine(String line) {
		ConcurrentREPL.currentWorkingDirectory = dirToSet;
		return null;
	}
	
	/**
	 *Run is the method from the class Runnable, which allows this to be a filter
	 */
	public void run(){
		process();
	}
}
