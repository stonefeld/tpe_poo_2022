package frontend.ui.buttons;

import backend.model.Figure;
import backend.model.Point;
import frontend.ui.render.FigureRender;
import frontend.ui.render.FigureStyle;
import frontend.ui.render.SketchCreator;

public class FigureStyledToggleButton<T extends Figure> extends StyledToggleButton {

	private final SketchCreator<T> sketchCreator;

	public FigureStyledToggleButton(String description, SketchCreator<T> sketchCreator) {
		super(description);
		this.sketchCreator = sketchCreator;
	}

	public FigureRender<T> render(Point startPoint, Point endPoint, FigureStyle style) {
		return sketchCreator.createSketch(startPoint, endPoint, style);
	}

}
