package tasod.graphics;

public class RenderEngine {
	/**
	 * Name of the system dependencies that need to be satisfied in order to run
	 */
	public static final String[] DEPENDENCIES = {"freeglut", "glew32", "glfw3", "render_engine"};
	
	private RenderEngine(){
	}
	
	
	
	
	
	public native void method();
	
	
	private static RenderEngine renderEngine = null; 
	
	/**
	 * The get of a singleton
	 * @return the unique instance of a renderEngine
	 */
	public static RenderEngine get(){
		if(renderEngine == null){
			for(String libName : DEPENDENCIES){
				System.loadLibrary(libName);
			}
			renderEngine = new RenderEngine();
		}
		return renderEngine;
	}
}
