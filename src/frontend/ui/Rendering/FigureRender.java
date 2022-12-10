package frontend.ui.Rendering;

import backend.model.Figure;

public class FigureRender<T extends Figure> {
    private FigureStyle style;
    private T figure;

    public FigureRender(FigureStyle style, T figure) {
        this.style = style;
        this.figure = figure;
    }
}
