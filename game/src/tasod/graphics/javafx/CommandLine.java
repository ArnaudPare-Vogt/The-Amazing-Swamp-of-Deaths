package tasod.graphics.javafx;

import java.awt.Paint;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Represents the very complex command line that will be given to the player.
 * The command line is essentially a web page, that looks like a command line,
 * but the web page idea allows the command line to display rich text
 * formatting.
 * 
 * Since the command line is technically considered GUI, it will not be updated
 * on the same thread as the main game. In order to get the commands, the
 * command line should be periodically queried. An interrupt system might be a
 * good idea.
 * 
 * @author Arnaud Paré-Vogt
 *
 */
public class CommandLine implements Window {
	private static final String TITLE = "T.A.S.O.D. - Terminal";
	private static final String PROMPT_TEXT = " >";

	private boolean creationFinished = false;

	private Stage mainWindow;
	private Scene mainScene;
	private TextArea content;
	private TextField txtCommand;
	private Label lblPrompt;

	private ApplicationImpl application;

	private List<String> commands;

	/**
	 * Creates the command line. After the constructor is called, the command
	 * line will be shown to the user.
	 */
	public CommandLine() {
		ApplicationInterface.createNewWindow(this);
		commands = new ArrayList<>();
		synchronized (this) {
			while (!creationFinished) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					break;
				}
			}
		}

	}

	@Override
	public void create(Stage mainWindow) {
		this.mainWindow = mainWindow;

		VBox vBox = new VBox();
		vBox.setSpacing(0);
		vBox.setId("background");

		mainScene = new Scene(vBox);

		content = new TextArea();
		content.setPrefColumnCount(80);
		content.setPrefRowCount(20);
		content.setEditable(false);
		content.setId("content");
		txtCommand = new TextField();
		txtCommand.setPrefColumnCount(80);
		txtCommand.setId("command");
		lblPrompt = new Label(PROMPT_TEXT);
		lblPrompt.setAlignment(Pos.BASELINE_CENTER);
		lblPrompt.setId("prompt");

		vBox.getChildren().add(content);

		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.getChildren().add(lblPrompt);

		hbox.getChildren().add(txtCommand);
		vBox.getChildren().add(hbox);

		Font font = Font.loadFont(CommandLine.class.getResourceAsStream("/cour.ttf"), 15);
		content.setFont(font);
		txtCommand.setFont(font);
		lblPrompt.setFont(font);

		File f = new File("res/terminal.css");
		mainScene.getStylesheets().clear();
		mainScene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

		mainWindow.setScene(mainScene);
		mainWindow.setTitle(TITLE);

		txtCommand.setOnAction((eve) -> {
			synchronized (commands) {
				String command = txtCommand.getText();
				commands.add(command);
				content.appendText(PROMPT_TEXT + ' ' + command + '\n');

				txtCommand.clear();
			}
		});

		mainWindow.show();

		synchronized (this) {
			creationFinished = true;
			this.notifyAll();
		}
	}

	/**
	 * Gives the list of all commands that were executed since the last call of
	 * this method. Thread-safe.
	 * 
	 * @return the list of commands that were executed since the last call of
	 *         this method
	 */
	public List<String> getCommands() {
		ArrayList<String> buffer;
		synchronized (commands) {
			buffer = new ArrayList<>(commands);
			commands.clear();
		}
		return buffer;
	}

	/**
	 * Gives the oldest command that was executed since the last call of this
	 * method. Thread-safe. If there was no command, returns null.
	 * 
	 * @return the oldest command that was executed since the last call of this
	 *         method
	 */
	public String getCommand() {
		String command;
		synchronized (commands) {
			if (commands.size() > 0) {
				command = commands.remove(0);
			} else {
				command = null;
			}
		}
		return command;
	}

	private class ApplicationImpl extends Application {

		@Override
		public void start(Stage primaryStage) throws Exception {
		}

	}

}
