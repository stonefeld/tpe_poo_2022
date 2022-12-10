package frontend.ui.buttons;

import backend.model.Circle;
import backend.model.Figure;
import backend.model.Point;

public class CircleFigureStyledToggleButton extends FigureStyledToggleButton {

	public CircleFigureStyledToggleButton(String description) {
		super(description);
	}

	@Override
	public Figure createFigure(Point startPoint, Point endPoint) {
		return new Circle(startPoint, endPoint);
	}

}
