package frontend.ui.render;

import backend.model.Figure;
import backend.model.Point;

@FunctionalInterface
public interface SketchCreator<T extends Figure> {

    FigureRender<T> createSketch(Point startPoint, Point endPoint, FigureStyle style);

}
