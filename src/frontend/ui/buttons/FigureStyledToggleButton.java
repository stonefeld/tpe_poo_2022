package frontend.ui.buttons;

import backend.model.Figure;
import backend.model.Point;
import frontend.ui.render.FigureRender;
import frontend.ui.render.FigureStyle;
import frontend.ui.render.SketchCreator;

public class FigureStyledToggleButton extends StyledToggleButton {

	private final SketchCreator<? extends Figure> sketchCreator;

	public FigureStyledToggleButton(String description, SketchCreator<? extends Figure> sketchCreator) {
		super(description);
		this.sketchCreator = sketchCreator;
	}

	public FigureRender<? extends Figure> createFigure(Point startPoint, Point endPoint, FigureStyle style) {
		return sketchCreator.createSketch(startPoint, endPoint, style);
	}

}
