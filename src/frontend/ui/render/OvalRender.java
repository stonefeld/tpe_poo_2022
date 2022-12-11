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

	/**
	 * Al dibujar la figura, tanto la elipse como el círculo utilizan una función diferente
	 * del GraphicsContext para dibujar una figura con forma ovalada. Primero se llama a la
	 * función padre para setear el estilo.
	 * @param gc El contexto gráfico donde será dibujada la figura.
	 */
	@Override
	public void drawSketch(GraphicsContext gc) {
		super.drawSketch(gc);
		gc.fillOval(getFigure().getStartPoint().getX(), getFigure().getStartPoint().getY(), getFigure().getWidth(), getFigure().getHeight());
		gc.strokeOval(getFigure().getStartPoint().getX(), getFigure().getStartPoint().getY(), getFigure().getWidth(), getFigure().getHeight());
	}

}
