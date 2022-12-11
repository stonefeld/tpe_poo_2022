package backend.model;

public class Circle extends Oval {

	private final double radius;

	public Circle(Point startPoint, Point endPoint) {
		/*
		Para esta figura particular el startPoint es el centro del círculo
		 */
		super(new Point(startPoint.getX() - startPoint.distance(endPoint), startPoint.getY() - startPoint.distance(endPoint)),
				new Point(startPoint.getX() + startPoint.distance(endPoint), startPoint.getY() + startPoint.distance(endPoint)),
				startPoint);
		this.radius = startPoint.distance(endPoint);
	}

	public String name() {
		return "Círculo";
	}

	@Override
	public String toString() {
		return String.format("Círculo [Centro: %s, Radio: %.2f]", getCenterPoint(), getWidth());
	}

	@Override
	public boolean belongsToSketch(Point point) {
		return Math.sqrt(Math.pow(getCenterPoint().getX() - point.getX(), 2) +
				Math.pow(getCenterPoint().getY() - point.getY(), 2)) < getWidth() / 2;
	}

	@Override
	public Figure copy() {
		Point center = getCenterPoint().copy();
		Point end = new Point(center.getX(), center.getY() + radius);
		return new Circle(center, end);
	}

}
