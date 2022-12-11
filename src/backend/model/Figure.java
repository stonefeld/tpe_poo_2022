package backend.model;

public abstract class Figure implements MovableSketch {

	private final Point startPoint, endPoint;

	public Figure(Point startPoint, Point endPoint) {
		this.endPoint = endPoint;
		this.startPoint = startPoint;
	}

	public double getWidth() {
		return endPoint.distanceX(startPoint);
	}

	public double getHeight() {
		return endPoint.distanceY(startPoint);
	}

	public Point getStartPoint() {
		return startPoint;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public abstract Figure copy();

	public abstract String name();

	@Override
	public Point[] getPoints() {
		return new Point[]{startPoint, endPoint};
	}

	/**
	 * Función static utilizada para validar los puntos obtenidos para saber
	 * si es posible dibujar esa figura. El startPoint debe estar más arriba y más
	 * a la izquierda que el endPoint.
	 * @param startPoint El punto de inicio o topLeft.
	 * @param endPoint El punto final o bottomRight.
	 * @return Retorna Verdadero o True en caso de ser válidos y Falso o False
	 * en caso contrario.
	 */
	public static boolean isValid(Point startPoint, Point endPoint) {
		return startPoint != null && startPoint.getX() < endPoint.getX() && startPoint.getY() < endPoint.getY();
	}

}
