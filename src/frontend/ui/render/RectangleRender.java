package frontend.ui.render;

import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;

public class RectangleRender<T extends Rectangle> extends FigureRender<T> {

	public RectangleRender(FigureStyle style, T figure) {
		super(style, figure);
	}

	@Override
	public void drawSketch(GraphicsContext gc) {
		super.drawSketch(gc);
		gc.fillRect(getFigure().getStartPoint().getX(), getFigure().getStartPoint().getY(), getFigure().getWidth(), getFigure().getHeight());
		gc.strokeRect(getFigure().getStartPoint().getX(), getFigure().getStartPoint().getY(), getFigure().getWidth(), getFigure().getHeight());
	}
}
