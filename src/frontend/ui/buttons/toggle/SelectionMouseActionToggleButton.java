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

	/**
	 * Al momento de presionar el botón del mouse, es posible que se esté arrastrando
	 * una figura, por lo que se guarda el punto donde fue realizada la acción.
	 * @param point El punto donde fue accionado el evento.
	 */
	@Override
	public void mousePressedAction(Point point) {
		lastPoint = point;
	}

	/**
	 * Al clickear con el mouse con este botón seleccionado posiblemente se desee
	 * seleccionar una figura, por lo que se verifica si existe una figura en el punto
	 * donde sucedió la acción.
	 * @param point El punto donde fue accionado el evento.
	 * @param label El label al cual se le agregará la información de todas las figuras
	 * encontradas bajo el mouse.
	 */
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

	/**
	 * Al arrastrar el mouse, si existe un elemento seleccionado este debe moverse
	 * por lo que se toma acción calculando la diferencia de distancia entre el punto
	 * actual y el punto donde comenzó a presionarse el botón.
	 * @param point El punto donde fue accionado el evento.
	 */
	@Override
	public void mouseDraggedAction(Point point) {
		if (getCanvasState().existsSelected()) {
			double diffX = (point.getX() - lastPoint.getX()) / 150;
			double diffY = (point.getY() - lastPoint.getY()) / 150;
			getCanvasState().getSelected().getFigure().move(diffX, diffY);
		}
	}

}
