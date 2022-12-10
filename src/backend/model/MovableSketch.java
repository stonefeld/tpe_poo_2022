package backend.model;



public interface MovableSketch extends Movable{
    /*
    Llamamos sketch a todo lo que se dibuja en la pantalla.
    Todos los sketch se mueven, por lo que implementamos esta interfaz que describe su comportamiento.
     */
    /*
    Un sketch se crea a partir de 2 puntos que se obtienen como eventos del mouse.
    Vamos a construir todas nuestras clases hijas mirando desde el punto de vista del canvas y estos 2 puntos (StartPoint y EndPoint).
    Entendiendo que para cada tipo de Figura hay una relación entre (StartPoint,EndPoint), y los puntos que la caracterizan.
     */
    Point [] getPoints();

    default void move(double diffX, double diffY){
        for (Point point: getPoints()){
            point.move(diffX, diffY);
        }
    }


    boolean belongsToSketch(Point point);

}
