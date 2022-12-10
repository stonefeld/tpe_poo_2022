package backend.model;

public class Ellipse extends Oval {

	public Ellipse(Point startPoint, Point endPoint) {
		super(startPoint, endPoint,
				new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2,
						(Math.abs((endPoint.getY() + startPoint.getY())) / 2)));
	}

	@Override
	public String toString() {
		return String.format("Elipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]", getCenterPoint(), getWidth(), getHeight());
	}

	@Override
	public boolean belongsToSketch(Point point) {
		return ((Math.pow(point.getX() - getCenterPoint().getX(), 2) / Math.pow(getHeight(), 2)) +
				Math.pow(point.getY() - getCenterPoint().getY(), 2) / Math.pow(getHeight(), 2)) <= 0.3;
	}

}
