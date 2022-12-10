package frontend.ui.render.operations;

import backend.model.Figure;
import frontend.ui.render.FigureRender;

public class Operation {
    private FigureRender<? extends Figure> state;
    private String operationLabel;

    public Operation(FigureRender<? extends Figure> state, String operationLabel) {
        this.state = state;
        this.operationLabel = operationLabel;
    }
}
