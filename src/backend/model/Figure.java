package backend.model;


/*
Las figuras son sketch, pues se dibujan en el canvas
 */
public abstract class Figure extends Sketch {


    public Figure(Point startPoint, Point endPoint) {
        super(startPoint, endPoint);
    }

    @Override
    public boolean isInvalid() {
        /*
        Start Point debe ser esquina superior izquierda y End Point Esquina inferior Izquierda
         */
        return (getStartPoint().getX() >= getEndPoint().getX() || getStartPoint().getY() >= getEndPoint().getY());
    }

    /*
      Width y Height son los argumentos que toma Graphic Context, facilitamos los getters. Al mismo tiempo representan en cada figura una característica particular.
      */
    public double getWidth(){
        return getEndPoint().distanceX(getStartPoint());
    }
    public double getHeight(){
        return getEndPoint().distanceY(getStartPoint());
    }

}
