package backend.model;

public class Square extends Rectangle {

	public Square(Point startPoint, Point endPoint) {
		super(startPoint, new Point(endPoint.getX(), startPoint.getY() + endPoint.distanceX(startPoint)));
	}

	public String name() {
		return "Cuadrado";
	}

	@Override
	public String toString() {
		return String.format("Cuadrado [ %s , %s ]", getStartPoint(), getEndPoint());
	}

	@Override
	public Figure copy() {
		return new Square(getStartPoint().copy(), getEndPoint().copy());
	}

}
