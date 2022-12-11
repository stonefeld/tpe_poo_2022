package frontend.ui.render;

import backend.model.Figure;
import backend.model.Point;

/**
 * Interfaz funcional encargada de dibujar la figura de manera genérica.
 * @param <T> El modelo de figura a dibujar.
 */
@FunctionalInterface
public interface SketchCreator<T extends Figure> {

    FigureRender<T> createSketch(Point startPoint, Point endPoint, FigureStyle style);

}
