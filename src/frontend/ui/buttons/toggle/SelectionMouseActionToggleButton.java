package frontend.ui.buttons.toggle;

import backend.CanvasState;
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
		if (aux != null) {
			aux.select();
			label.append(aux.getFigure());
		}
		getCanvasState().selectFigure(aux);
	}

	@Override
	public void mouseDraggedAction(Point point) {
		if (getCanvasState().getSelectedFigure() != null) {
			double diffX = (point.getX() - lastPoint.getX()) / 100;
			double diffY = (point.getY() - lastPoint.getY()) / 100;
			getCanvasState().getSelectedFigure().getFigure().move(diffX, diffY);
		}
	}

}
