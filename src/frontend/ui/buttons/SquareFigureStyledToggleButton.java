package frontend.ui.buttons;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Square;
import frontend.ui.buttons.FigureStyledToggleButton;

public class SquareFigureStyledToggleButton extends FigureStyledToggleButton {

	public SquareFigureStyledToggleButton(String description) {
		super(description);
	}

	@Override
	public Figure createFigure(Point startPoint, Point endPoint) {
		return new Square(startPoint, endPoint);
	}

}
