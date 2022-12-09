package backend.model;

public class Ellipse extends Figure {
	protected Point centerPoint;
	public Ellipse(Point startPoint, Point endPoint) {
		super(startPoint, endPoint);
		centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
	}

	@Override
	public String toString() {
		return String.format("Elipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]", centerPoint, getWidth(), getHeight());
	}
	public Point getCenterPoint() {
		return centerPoint;
	}

	@Override
	public Point[] getPoints() {
		return new Point[] {getStartPoint(), getEndPoint(), centerPoint};
	}
	@Override
	public boolean belongsToSketch(Point point) {
		return ((Math.pow(point.getX() - centerPoint.getX(), 2) / Math.pow(getWidth(), 2)) +
				(Math.pow(point.getY() - centerPoint.getY(), 2) / Math.pow(getHeight(), 2))) <= 0.30;
	}
}
