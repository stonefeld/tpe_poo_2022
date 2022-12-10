package backend.model;


/*
    Las figuras son sketch, pues se dibujan en el canvas
*/
/*
    Un sketch se crea a partir de 2 puntos que se obtienen como eventos del mouse.
    Vamos a construir todas nuestras clases hijas mirando desde el punto de vista del canvas y estos 2 puntos StartPoint y EndPoint.
    Entendiendo que para cada tipo de Figura hay una relación entre estos 2 y los puntos que la caracterizan.
*/
public abstract class Figure implements MovableSketch {

	private final Point startPoint, endPoint;

	public Figure(Point startPoint, Point endPoint) {
		this.endPoint = endPoint;
		this.startPoint = startPoint;
	}

	/*
	  Width y Height son los argumentos que toma Graphic Context, facilitamos los getters. Al mismo tiempo representan en cada figura una característica particular.
	  */
	public double getWidth() {
		return endPoint.distanceX(startPoint);
	}

	public double getHeight() {
		return endPoint.distanceY(startPoint);
	}

	/*
	es static porque no necesito crear una instancia para determinar si 2 puntos son válidos para crear cualquier figura
	 */
	public static boolean isInvalid(Point startPoint, Point endPoint) {
		return (startPoint.getX() >= endPoint.getX() || startPoint.getY() >= endPoint.getY());
	}

	public Point getStartPoint() {
		return startPoint;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	@Override
	public Point[] getPoints() {
		return new Point[]{startPoint, endPoint};
	}

}
