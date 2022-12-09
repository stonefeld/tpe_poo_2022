package frontend.ui;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;

public class RectangleFigureStyledTool extends FigureStyledTool {

	public RectangleFigureStyledTool(String description) {
		super(description);
	}

	@Override
	public Figure createFigure(Point startPoint, Point endPoint) {
		return new Rectangle(startPoint, endPoint);
	}

}
