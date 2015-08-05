package abcd3901.graphics.UI;

import java.awt.Point;

/**
 * An object that can be clicked and hovered upon
 * @author Arnaud Paré-Vogt
 *
 */
public interface Clickable {
	
	public void updateState(Point pos);
	/**
	 * Check for a click
	 * @param pos the mouse position relative to the space of the component
	 * @return if the event needs to be propagated
	 */
	public boolean clicked(Point pos);
	
}
