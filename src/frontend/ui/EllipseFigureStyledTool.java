package frontend.ui;

import backend.model.Ellipse;
import backend.model.Figure;
import backend.model.Point;

public class EllipseFigureStyledTool extends FigureStyledTool {

	public EllipseFigureStyledTool(String description) {
		super(description);
	}

	@Override
	public Figure createFigure(Point startPoint, Point endPoint) {
		return new Ellipse(startPoint, endPoint);
	}

}
