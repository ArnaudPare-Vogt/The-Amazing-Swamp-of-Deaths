package abcd3901.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import abcd3901.game.gamemode.GameMode;
import abcd3901.game.gamemode.PlayMode;
import abcd3901.graphics.Renderer;
import abcd3901.io.UIInput;
import abcd3901.io.UserInput;
import abcd3901.utility.exception.ExceptionLog;

/**
 * The main class.
 * 
 * @author Arnaud Paré-Vogt
 *
 */
public class Component extends JPanel implements Runnable {
	public static final long serialVersionUID = 1l;

	public static final String TITLE = "The Amazing Swamp of Deaths";
	public static final String VERSION = "0.0.1-alpha";

	// thread stuff
	private boolean repaintFinished = false;
	private Thread gameLoop;
	private double fps = 60;

	// size stuff
	private Dimension size = new Dimension(800, 600);
	private int pixelSize = 1;

	// graphics stuff
	private Renderer renderer;

	// control stuff
	private UserInput in;
	private UIInput uiIn;

	// tepm stuff
	GameMode m = new PlayMode(800, 600);

	public Component(JFrame parent) {
		this.setPreferredSize(size);
		initGraphics();
		initThread();
		initInput(parent);
	}

	private void initGraphics() {
		renderer = new Renderer(size.width, size.height);
	}

	private void initThread() {
		gameLoop = new Thread(this, "TASOD_mainGameLoop");
	}

	/**
	 * Used by the constructor to initialize the input classes and add the
	 * listeners.
	 * 
	 * @param parent
	 *            the parent frame of the <code>Component</code>
	 */
	private void initInput(JFrame parent) {
		in = new UserInput();
		parent.addKeyListener(in);
		this.addMouseMotionListener(in);
		this.addMouseListener(in);
		uiIn = new UIInput(parent.getContentPane());
		parent.getContentPane().addComponentListener(uiIn);
	}

	public void start() {
		gameLoop.start();
	}

	public void stop() {
		gameLoop.interrupt();
	}

	@Override
	public void run() {
		long lastTime = System.currentTimeMillis();
		while (!Thread.currentThread().isInterrupted()) {
			try {
				long remaningTime = (long) (1000 / fps)
						- (System.currentTimeMillis() - lastTime);
				Thread.sleep(remaningTime < 0 ? 0 : remaningTime);
				lastTime = System.currentTimeMillis();
				// update
				update();
				Thread.sleep(10);
				// render
				render();
				repaintFinished = false;
				repaint();
				synchronized (this) {
					while (!repaintFinished) {
						this.wait();
					}
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}

		}
	}

	private void update() {
		m.update(in);
		in.clear();
		if (uiIn.wasResizedThisFrame()) {
			size = uiIn.getActualSize();
			this.setSize(size);
			m.resize(size);
			renderer.resize(size);
		}
		uiIn.reset();
	}

	/**
	 * Tells the current GameMode to render the level
	 */
	private void render() {
		renderer.clearImage();
		m.render(renderer);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(renderer.getImage(), 0, 0, size.width * pixelSize,
				size.height * pixelSize, null);
		synchronized (this) {
			repaintFinished = true;
			this.notifyAll();
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame();
			Component component = new Component(frame);
			
			//Prevents a lot of flickering when resizing the frame.
			Toolkit.getDefaultToolkit().setDynamicLayout(false);
			component.setIgnoreRepaint(true);
			
			frame.add(component);
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setTitle(TITLE + "   " + VERSION);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);

			component.start();

			ExceptionLog.showException(frame);
		});
	}
}
