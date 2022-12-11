package backend.model;

public class Point implements  Movable{

	private double x, y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double distanceX(Point p2) {
		return Math.abs(x - p2.getX());
	}

	public double distanceY(Point p2) {
		return Math.abs(y - p2.getY());
	}

	public double distance(Point point) {
		return Math.sqrt(Math.pow(distanceX(point), 2) + Math.pow(distanceY(point), 2));
	}

	public void move(double diffX, double diffY) {
		x += diffX;
		y += diffY;
	}

	public void moveTo(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Point copy() {
		return new Point(x, y);
	}

	@Override
	public String toString() {
		return String.format("{%.2f , %.2f}", x, y);
	}

}
