package tasod.graphics.javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * Sets up an application, and uses intelligent stuff in order to return correct
 * values.
 * 
 * @author Arnaud Paré-Vogt
 *
 */
public class ApplicationInterface {

	private static ApplicationInterface instance;
	private Thread javaFxThread;
	private ApplicationImpl applicationImpl;

	/**
	 * We are using a singleton because javaFx only supports one application!
	 */
	private ApplicationInterface() {
		javaFxThread = new Thread(() -> {
			ApplicationImpl.launch(ApplicationImpl.class);// No arguments...
		});
	}

	/**
	 * Disgusting method that is called in order to setup an application class.
	 * More or less like a constructor.
	 */
	private void setUpApplication() {
		javaFxThread.start();
		
		synchronized (this) {
			while (applicationImpl == null) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					return;// TODO fox this and have decent error handling
				}
			}
		}
	}

	private void setApplicationImpl(ApplicationImpl applicationImpl) {
		this.applicationImpl = applicationImpl;
	}

	/**
	 * Non-blocking method to create a new window. This method will setup a
	 * stage and call the create method in the window interface.
	 */
	public static void createNewWindow(Window window) {
		if (instance == null) {
			instance = new ApplicationInterface();
			instance.setUpApplication();

			Platform.runLater(()->{
				window.create(instance.applicationImpl.primaryStage);
			});
		} else {
			Platform.runLater(()->{
				window.create(new Stage());
			});
		}
	}

	/**
	 * Implementation of the JavaFX Application class. Has a tight binding with
	 * ApplicationInterface.
	 * 
	 * @author Arnaud Paré-Vogt
	 *
	 */
	public static class ApplicationImpl extends Application {
		private Stage primaryStage;

		@Override
		public void start(Stage primaryStage) {
			this.primaryStage = primaryStage;
			synchronized (instance) {
				instance.setApplicationImpl(this);
				instance.notifyAll();
			}
		}

	}
}
