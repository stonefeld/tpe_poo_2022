package frontend.ui.render;

import backend.model.Oval;
import javafx.scene.canvas.GraphicsContext;

@SuppressWarnings("unchecked")
public class OvalRender<T extends Oval> extends FigureRender<T> {

	public OvalRender(FigureStyle style, T figure) {
		super(style, figure);
	}

	@Override
	public FigureRender<T> copy() {
		return new OvalRender<>(getStyle(), (T) getFigure().copy());
	}

	@Override
	public void drawSketch(GraphicsContext gc) {
		super.drawSketch(gc);
		gc.fillOval(getFigure().getStartPoint().getX(), getFigure().getStartPoint().getY(), getFigure().getWidth(), getFigure().getHeight());
		gc.strokeOval(getFigure().getStartPoint().getX(), getFigure().getStartPoint().getY(), getFigure().getWidth(), getFigure().getHeight());
	}

}
