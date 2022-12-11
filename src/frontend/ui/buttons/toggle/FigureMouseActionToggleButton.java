package frontend.ui.buttons.toggle;

import backend.model.Figure;
import backend.model.Point;
import frontend.CanvasState;
import frontend.ui.render.FigureRender;
import frontend.ui.render.FigureStyle;
import frontend.ui.render.SketchCreator;

public class FigureMouseActionToggleButton<T extends Figure> extends MouseActionToggleButton {

	private final SketchCreator<T> sketchCreator;
	private final FigureStyle currentStyle;
	private Point startPoint;

	public FigureMouseActionToggleButton(String description, FigureStyle currentStyle, CanvasState canvasState, SketchCreator<T> sketchCreator) {
		super(description, canvasState);
		this.sketchCreator = sketchCreator;
		this.currentStyle = currentStyle;
	}

	/**
	 * Al presionar el mouse, se espera que se desee crear una nueva figura por lo que se
	 * establece el startPoint.
	 * @param point El punto donde fue accionado el evento.
	 */
	@Override
	public void mousePressedAction(Point point) {
		startPoint = point;
	}

	/**
	 * Al soltar el botón del mouse, se verifica si el punto final es válido. En caso de
	 * serlo, se crea una figura utilizando el sketchCreator que fue definido al momento
	 * de crear el botón.
	 * @param point El punto donde fue accionado el evento.
	 */
	@Override
	public void mouseReleasedAction(Point point) {
		if (Figure.isValid(startPoint, point)) {
			FigureRender<? extends Figure> aux = sketchCreator.createSketch(startPoint, point, currentStyle);
			getCanvasState().addOperation(String.format("Dibujar %s", aux.getFigure().name()));
			getCanvasState().addFigure(aux);
			startPoint = null;
		}
	}

}
