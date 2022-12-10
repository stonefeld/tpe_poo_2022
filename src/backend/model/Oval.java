package backend.model;

import javafx.scene.canvas.GraphicsContext;

public abstract class Oval extends Figure {

	private final Point centerPoint;

	public Oval(Point startPoint, Point endPoint, Point centerPoint) {
		super(startPoint, endPoint);
		this.centerPoint = centerPoint;
	}

	public Point getCenterPoint() {
		return centerPoint;
	}

	@Override
	public Point[] getPoints() {
		return new Point[]{getStartPoint(), getEndPoint(), centerPoint};
	}

	@Override
	public void drawSketch(GraphicsContext gc) {
		gc.strokeOval(getStartPoint().getX(), getStartPoint().getY(), getWidth(), getHeight());
		gc.fillOval(getStartPoint().getX(), getStartPoint().getY(), getWidth(), getHeight());
	}

}
