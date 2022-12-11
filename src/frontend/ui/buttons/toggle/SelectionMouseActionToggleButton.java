package frontend.ui.buttons.toggle;

import frontend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import frontend.ui.render.FigureRender;

public class SelectionMouseActionToggleButton extends MouseActionToggleButton {

	private Point lastPoint;

	public SelectionMouseActionToggleButton(String description, CanvasState canvasState) {
		super(description, canvasState);
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
				getCanvasState().addOperation(String.format("Aplicar formato copiado a %s", aux.getFigure().name()));
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
			double diffX = (point.getX() - lastPoint.getX()) / 150;
			double diffY = (point.getY() - lastPoint.getY()) / 150;
			getCanvasState().getSelected().getFigure().move(diffX, diffY);
		}
	}

}
