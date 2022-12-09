package backend.model;

/*
Un sketch se crea a partir de 2 puntos que se obtienen como eventos del mouse.
Vamos a construir todas nuestras clases hijas mirando desde el punto de vista del canvas y estos 2 puntos StartPoint y EndPoint.
Entendiendo que para cada tipo de Figura hay una relación entre estos 2 y los puntos que la caracterizan.
 */
public abstract class Sketch implements MovableSketch{

    private final Point startPoint, endPoint;


    public Sketch(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
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
