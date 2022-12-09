package frontend.ui;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Square;

public class SquareFigureStyledTool extends FigureStyledTool {

	public SquareFigureStyledTool(String description) {
		super(description);
	}

	@Override
	public Figure createFigure(Point startPoint, Point endPoint) {
		return new Square(startPoint, Math.abs(startPoint.getX() - endPoint.getX()));
	}

}
