package frontend.ui;

import backend.model.Circle;
import backend.model.Figure;
import backend.model.Point;

public class CircleFigureStyledTool extends FigureStyledTool {

	public CircleFigureStyledTool(String description) {
		super(description);
	}

	@Override
	public Figure createFigure(Point startPoint, Point endPoint) {
		return new Circle(startPoint, endPoint);
	}

}
