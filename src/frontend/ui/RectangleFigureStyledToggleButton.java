package frontend.ui;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;

public class RectangleFigureStyledToggleButton extends FigureStyledToggleButton {

	public RectangleFigureStyledToggleButton(String description) {
		super(description);
	}

	@Override
	public Figure createFigure(Point startPoint, Point endPoint) {
		return new Rectangle(startPoint, endPoint);
	}

}
