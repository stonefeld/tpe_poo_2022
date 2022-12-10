package frontend.ui.buttons;

import backend.model.Figure;
import backend.model.Point;

public abstract class FigureStyledToggleButton extends StyledToggleButton {

	public FigureStyledToggleButton(String description) {
		super(description);
	}

	public abstract Figure createFigure(Point startPoint, Point endPoint);

}
