package frontend.ui.render;

import backend.model.Rectangle;

public class RectangleRender<T extends Rectangle> extends FigureRender<T> {

	public RectangleRender(FigureStyle style, T figure) {
		super(style, figure);
	}

}
