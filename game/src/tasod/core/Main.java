package tasod.core;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import tasod.graphics.RenderEngine;
import tasod.graphics.javafx.ApplicationInterface;
import tasod.graphics.javafx.CommandLine;

/**
 * Main class of the project
 * 
 * @author Arnaud Paré-Vogt
 */
public class Main {
	public static void main(String[] args){
		
		CommandLine c = new CommandLine();
		System.out.println("allo from java");
		while(true){
			String command = c.getCommand();
			if(command == null){
				continue;
			}else if(command.equals("exit")){
				break;
			}else{
				System.out.println(command);
			}
		}
	}
}
