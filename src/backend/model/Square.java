package backend.model;

public class Square extends Rectangle {

	public Square(Point startPoint, Point endPoint) {
		super(startPoint, new Point(endPoint.getX(), startPoint.getY() + endPoint.distanceX(startPoint)));
	}

	@Override
	public String toString() {
		return String.format("Cuadrado [ %s , %s ]", getStartPoint(), getEndPoint());
	}

}
