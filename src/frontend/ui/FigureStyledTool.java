package frontend.ui;

import backend.model.Figure;
import backend.model.Point;

public abstract class FigureStyledTool extends StyledTool {

	public FigureStyledTool(String description) {
		super(description);
	}

	public abstract Figure createFigure(Point startPoint, Point endPoint);

}
