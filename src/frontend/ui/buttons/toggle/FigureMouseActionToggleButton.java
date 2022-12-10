package frontend.ui.buttons.toggle;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
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

	public FigureRender<T> render(Point startPoint, Point endPoint, FigureStyle style) {
		return sketchCreator.createSketch(startPoint, endPoint, style);
	}

	@Override
	public void mousePressedAction(Point point) {
		startPoint = point;
	}

	@Override
	public void mouseReleasedAction(Point point) {
		if (Figure.isValid(startPoint, point)) {
			getCanvasState().addFigure(render(startPoint, point, currentStyle));
			startPoint = null;
		}
	}

}
