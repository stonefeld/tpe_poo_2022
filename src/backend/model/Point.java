package backend.model;

public class Point {

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

	public void move(double diffX, double diffY){
		this.x += diffX;
		this.y += diffY;
	}

	@Override
	public String toString() {
		return String.format("{%.2f , %.2f}", x, y);
	}

}
