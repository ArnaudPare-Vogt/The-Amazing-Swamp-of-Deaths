package tasod.graphics;

public class RenderEngine {
	/**
	 * Name of the library that backs RenderEngine
	 */
	public static final String LIB_NAME = "render_engine";
	
	private RenderEngine(){
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static RenderEngine renderEngine = null; 
	
	/**
	 * The get of a singleton
	 * @return the unique instance of a renderEngine
	 */
	public static RenderEngine get(){
		if(renderEngine == null){
			System.loadLibrary(LIB_NAME);
			renderEngine = new RenderEngine();
		}
		return renderEngine;
	}
}
