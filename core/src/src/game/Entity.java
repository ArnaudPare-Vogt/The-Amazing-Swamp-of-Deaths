package src.game;

import src.game.graphics.Renderer;

/**
 * An in-game entity. Useful to have because the entity can be removed, rendered
 * and updated.
 * 
 * FYI the id of an entity is the same as its location in memory. So checking if
 * two entities are equals is done using the == operator. A reference is anyway
 * probably faster and lighter than using some fancy unique string.
 * 
 * @author Arnaud Paré-Vogt
 *
 */
public abstract class Entity {
	private boolean shouldBeRemoved = false;
	
	
	/**
	 * Method that should update this entity, make it move and such
	 */
	public void update(){}
	
	/**
	 * Method that should be used to render the entity on screen. The entity will render itself at it's position.
	 */
	public void render(Renderer r){}
	
	
	/**
	 * Sets a flag so that this entity will be removed before the next update
	 * cycle
	 */
	public void remove() {
		shouldBeRemoved = true;
	}

	/**
	 * Returns if the entity should be removed next update cycle or not
	 * 
	 * @return if the entity should be removed next update cycle or not
	 */
	public boolean shouldBeRemoved() {
		return shouldBeRemoved;
	}

	/**
	 * Checks if two entities are the same (instance)
	 * 
	 * @return if the two entities are the same
	 */
	@Override
	public boolean equals(Object other) {
		return this == other;
	}
}
