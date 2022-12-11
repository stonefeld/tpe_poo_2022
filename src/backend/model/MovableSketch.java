package backend.model;

public interface MovableSketch extends Movable {

	Point[] getPoints();

	default void move(double diffX, double diffY) {
		for (Point point : getPoints()) {
			point.move(diffX, diffY);
		}
	}

	boolean belongsToSketch(Point point);

}
