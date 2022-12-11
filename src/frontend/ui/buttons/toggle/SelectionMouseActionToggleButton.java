package frontend.ui.buttons.toggle;

import frontend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import frontend.ui.render.FigureRender;
import frontend.ui.render.operations.Operation;
import frontend.ui.render.operations.OperationStack;

public class SelectionMouseActionToggleButton extends MouseActionToggleButton {

	private Point lastPoint;
	private final OperationStack stack;

	public SelectionMouseActionToggleButton(String description, CanvasState canvasState) {
		super(description, canvasState);
		this.stack = canvasState.getStack();
	}

	@Override
	public void mousePressedAction(Point point) {
		lastPoint = point;
	}

	@Override
	public void mouseClickedAction(Point point, StringBuilder label) {
		FigureRender<? extends Figure> aux = null;
		for (FigureRender<? extends Figure> figure : getCanvasState().figures()) {
			if (figure.getFigure().belongsToSketch(point)) {
				aux = figure;
			}
			figure.deselect();
		}
		getCanvasState().deselectFigure();
		if (aux != null) {
			label.append(aux.getFigure());
			if (getCanvasState().existsStyleToCopy()) {
				stack.addOperation(new Operation(getCanvasState().getRenderList(), "Copiar el formato de una figura"));
				aux.setStyle(getCanvasState().getStyleToCopy());
			} else {
				aux.select();
				getCanvasState().selectFigure(aux);
			}
		}
	}

	@Override
	public void mouseDraggedAction(Point point) {
		if (getCanvasState().getSelected() != null) {
			double diffX = (point.getX() - lastPoint.getX()) / 200;
			double diffY = (point.getY() - lastPoint.getY()) / 200;
			getCanvasState().getSelected().getFigure().move(diffX, diffY);
		}
	}

}
