package backend.model;

public class Rectangle extends Figure {

	public Rectangle(Point startPoint, Point endPoint) {
		super(startPoint, endPoint);
	}

	@Override
	public String toString() {
		return String.format("Rectángulo [ %s , %s ]", getStartPoint(), getEndPoint());
	}

	@Override
	public boolean belongsToSketch(Point point) {
		return (point.getX() > getStartPoint().getX() && point.getX() < getEndPoint().getX() &&
				point.getY() > getStartPoint().getY() && point.getY() < getEndPoint().getY());
	}

	@Override
	public Figure copy() {
		return new Rectangle(getStartPoint().copy(), getEndPoint().copy());
	}

}
