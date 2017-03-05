package src.game;

import com.badlogic.gdx.utils.Array;

/**
 * Class used to manage and render all the entities inside of it.
 * @author Arnaud Paré-Vogt
 *
 */
public class EntityManager {

	private Array<Entity> entities = new Array<>(false, 400);
	private Array<Entity> entitiesToAdd = new Array<>(false, 10);
	
	
	/**
	 * Sets up the entityManager to add this entity to the manager
	 * @param newEntity the entity to add
	 */
	public void addEntityAssync(Entity newEntity){
		entitiesToAdd.add(newEntity);
	}
}
