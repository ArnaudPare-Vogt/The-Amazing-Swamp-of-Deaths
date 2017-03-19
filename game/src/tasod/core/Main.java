package tasod.core;

import tasod.graphics.RenderEngine;

/**
 * Main class of the project
 * 
 * @author Arnaud Paré-Vogt
 */
public class Main {
	public static void main(String[] args){
		System.out.println("allo from java");
		
		RenderEngine re = RenderEngine.get();
		re.method();
	}
}
