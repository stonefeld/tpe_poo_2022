package frontend.ui.render;

import backend.model.Figure;
import javafx.scene.canvas.GraphicsContext;

public class FigureRender<T extends Figure> {

	private final FigureStyle style;
	private final T figure;
	private boolean selected = false;

	public FigureRender(FigureStyle style, T figure) {
		this.style = style;
		this.figure = figure;
	}

	public T getFigure() {
		return figure;
	}

	public boolean isSelected() {
		return selected;
	}

	public void toggleSelected() {
		selected = !selected;
	}

	public void drawSketch(GraphicsContext gc, boolean selected) {
		gc.setLineWidth(style.getLineWidth());
		gc.setStroke(selected ? style.getSelColor() : style.getLineColor());
		gc.setFill(style.getFillColor());
		figure.drawSketch(gc);
	}

}
