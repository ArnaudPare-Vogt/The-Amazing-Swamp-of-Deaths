package abcd3901.utility.math;

import java.awt.Point;

/**
 * Class representing a vector
 * @author Arnaud Paré-Vogt
 *
 */
public class Vector2D {
	
	private float dx,dy;
	
	public Vector2D(float dx,float dy){
		this.dx = dx;
		this.dy = dy;
	}
	
	public Vector2D(Point p1, Point p2){
		dx = p2.x-p1.x;
		dy = p2.y-p1.y;
	}
	
	public float scalarProduct(Vector2D vector2){
		return this.dx * vector2.dx + this.dy* vector2.dy;
	}
	
	public float getDx() {
		return dx;
	}
	public float getDy() {
		return dy;
	}
}
