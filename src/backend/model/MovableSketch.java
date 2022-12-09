package backend.model;



public interface MovableSketch extends Movable{
    /*
    Llamamos sketch a todo lo que se dibuja en la pantalla.
    Todos los sketch se mueven, por lo que implementamos esta interfaz que describe su comportamiento.
     */
    Point [] getPoints();
    default void move(double diffX, double diffY){
        for (Point point: getPoints()){
            point.move(diffX, diffY);
        }
    }

    boolean isInvalid();

    boolean belongsToSketch(Point point);

}
