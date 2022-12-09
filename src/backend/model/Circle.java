package backend.model;

public class Circle extends Ellipse {

	public Circle(Point startPoint, Point endPoint) {
		super(new Point(startPoint.getX() - startPoint.distanceX(endPoint),startPoint.getY() - startPoint.distanceY(endPoint)),
				new Point(startPoint.getX() + startPoint.distanceX(endPoint),startPoint.getY() + startPoint.distanceY(endPoint)));
		centerPoint = startPoint;

	}

	@Override
	public String toString() {
		return String.format("Círculo [Centro: %s, Radio: %.2f]", getCenterPoint(), getWidth());
	}

	@Override
	public boolean belongsToSketch(Point point) {
		return (Math.sqrt(Math.pow(getStartPoint().getX() - point.getX(), 2) +
				Math.pow(getStartPoint().getY() - point.getY(), 2)) < getHeight()/2);
	}
}
