package frontend.ui.buttons;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import frontend.ui.buttons.FigureStyledToggleButton;

public class RectangleFigureStyledToggleButton extends FigureStyledToggleButton {

	public RectangleFigureStyledToggleButton(String description) {
		super(description);
	}

	@Override
	public Figure createFigure(Point startPoint, Point endPoint) {
		return new Rectangle(startPoint, endPoint);
	}

}
