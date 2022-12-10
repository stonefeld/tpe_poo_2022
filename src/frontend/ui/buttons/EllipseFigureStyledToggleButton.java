package frontend.ui.buttons;

import backend.model.Ellipse;
import backend.model.Figure;
import backend.model.Point;

public class EllipseFigureStyledToggleButton extends FigureStyledToggleButton {

	public EllipseFigureStyledToggleButton(String description) {
		super(description);
	}

	@Override
	public Figure createFigure(Point startPoint, Point endPoint) {
		return new Ellipse(startPoint, endPoint);
	}

}
