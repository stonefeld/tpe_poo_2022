package frontend.ui.buttons;

import backend.model.Figure;
import backend.model.Point;
import frontend.ui.Rendering.FigureRender;
import frontend.ui.Rendering.SketchCreator;

public class FigureToggleButton<T extends Figure> extends StyledToggleButton {
    SketchCreator<T> creator;

    public FigureToggleButton(String description, SketchCreator<T> creator) {
        super(description);
        this.creator = creator;
    }

    public FigureRender<T> createFigure(Point startPoint, Point endPoint){
        return creator.create(startPoint, endPoint);
    }

}
