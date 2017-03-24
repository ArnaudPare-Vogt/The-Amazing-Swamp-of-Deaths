package tasod.graphics;

public class RenderEngine {
	/**
	 * Name of the system dependencies that need to be satisfied in order to run
	 */
	public static final String[] DEPENDENCIES = { "freeglut", "glew32", "glfw3", "render_engine" };

	private RenderEngine() {
	}

	/**
	 * Blocking method that initializes the render engine on this thread.
	 */
	public native void init();

	/**
	 * Begins the synchronization with the render engine. After this method is
	 * called, the render engine can be interacted with safely
	 */
	public native void beginSynchronization();

	/**
	 * Terminates the synchronized state and allows the render engine to
	 * continue rendering alone.
	 */
	public native void endSynchronization();

	/**
	 * Loads a material form a resource name
	 * 
	 * @param resourceName
	 *            the name of the resource
	 * @return a pointer to the loaded resource
	 */
	public native int loadMaterial(String resourceName);

	/**
	 * Spawns an object that is clothed with a specific material.
	 * 
	 * @param materialPointer
	 *            The pointer to a specific material
	 * @return a pointer to the object
	 */
	public native int spawnObject(int materialPointer);

	/**
	 * Despawns (destroys forever) an object given a object pointer.
	 * 
	 * @param objectPointer
	 *            the pointer to the to-be-despawned object
	 */
	public native void despawnObject(int objectPointer);

	/**
	 * Sets a simple object's movement by giving it a start position and a
	 * velocity
	 * 
	 * @param objectPointer
	 *            the pointer to the object
	 * @param startPosition
	 *            the start position of the object
	 * @param velocity
	 *            the velocity of the object
	 */
	public native void setObjectMovement(int objectPointer, Vec3f startPosition, Vec3f velocity);

	/**
	 * Sets a simple object's movement by giving it a start position and a
	 * velocity. The object should move with the given velocity form the start
	 * position to the end position. Akin to a lerp, but with speed instead of
	 * time.
	 * 
	 * @param objectPointer
	 *            the pointer to the object
	 * @param startPosition
	 *            the start position of the object
	 * @param velocity
	 *            the velocity of the object
	 * @param endPosition
	 *            the position at which he object should stop
	 */
	public native void setObjectMovement(int objectPointer, Vec3f startPosition, Vec3f velocity, Vec3f endPosition);

	/**
	 * Sets up an object to turn. The object will move from startPosition to
	 * turn position with the given velocity, and upon reaching the turn
	 * position, the object will begin moving with the end velocity.
	 * 
	 * @param objectPointer
	 *            the pointer to the object
	 * @param startPosition
	 *            the start position of the object
	 * @param velocity
	 *            the velocity of the object
	 * @param turnPosition
	 *            the position at which he object should turn
	 * @param endVelocity
	 *            the velocity of the object after it has reached the end
	 *            position
	 */
	public native void setObjectTurn(int objectPointer, Vec3f startPosition, Vec3f velocity, Vec3f turnPosition,
			Vec3f endVelocity);

	private static RenderEngine renderEngine = null;

	/**
	 * The get of a singleton
	 * 
	 * @return the unique instance of a renderEngine
	 */
	public static RenderEngine get() {
		if (renderEngine == null) {
			for (String libName : DEPENDENCIES) {
				System.loadLibrary(libName);
			}
			renderEngine = new RenderEngine();
		}
		return renderEngine;
	}
}
