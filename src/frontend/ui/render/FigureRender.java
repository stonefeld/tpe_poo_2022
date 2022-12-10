package frontend.ui.render;

import backend.model.Figure;
import javafx.scene.canvas.GraphicsContext;

public class FigureRender<T extends Figure> {

	private final FigureStyle style;
	private final T figure;
	private boolean selected = false;

	public FigureRender(FigureStyle style, T figure) {
		this.style = style.copy();
		this.figure = figure;
	}

	public T getFigure() {
		return figure;
	}

	public FigureStyle getStyle() {
		return style;
	}

	public void select() {
		selected = true;
	}

	public void deselect() {
		selected = false;
	}

	public void drawSketch(GraphicsContext gc) {
		gc.setLineWidth(style.getBorderWidth());
		gc.setStroke(selected ? style.getSelColor() : style.getBorderColor());
		gc.setFill(style.getFillColor());
	}

}
