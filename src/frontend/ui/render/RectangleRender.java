package frontend.ui.render;

import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;

@SuppressWarnings("unchecked")
public class RectangleRender<T extends Rectangle> extends FigureRender<T> {

	public RectangleRender(FigureStyle style, T figure) {
		super(style, figure);
	}

	@Override
	public RectangleRender<T> copy() {
		return new RectangleRender<>(getStyle(), (T) getFigure().copy());
	}

	/**
	 * Al dibujar la figura, tanto la elipse como el círculo utilizan una función diferente
	 * del GraphicsContext para dibujar una figura con forma rectangular. Primero se llama a la
	 * función padre para setear el estilo.
	 * @param gc El contexto gráfico donde será dibujada la figura.
	 */
	@Override
	public void drawSketch(GraphicsContext gc) {
		super.drawSketch(gc);
		gc.fillRect(getFigure().getStartPoint().getX(), getFigure().getStartPoint().getY(), getFigure().getWidth(), getFigure().getHeight());
		gc.strokeRect(getFigure().getStartPoint().getX(), getFigure().getStartPoint().getY(), getFigure().getWidth(), getFigure().getHeight());
	}

}
