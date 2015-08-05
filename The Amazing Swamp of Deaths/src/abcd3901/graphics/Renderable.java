package abcd3901.graphics;

/**
 * An object that can be rendered.
 * 
 * @author Arnaud Paré-Vogt
 *
 */
public interface Renderable {

	/**
	 * his method is called to ask the current <code>Renderable</code> object
	 * to render itself onto the <code>Renderer</code>.
	 * 
	 * @param ren
	 *            The <code>Renderer</code> on which the object has to be
	 *            rendered.
	 */
	public void render(Renderer ren);
}
