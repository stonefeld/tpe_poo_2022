package frontend.ui.Rendering;

import backend.model.Figure;
import backend.model.MovableSketch;
import backend.model.Point;

@FunctionalInterface
public interface SketchCreator<T extends Figure> {
    FigureRender<T> create(Point startPoint, Point endPoint);
}



