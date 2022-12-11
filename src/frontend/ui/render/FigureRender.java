package frontend.ui.render;

import backend.model.Figure;
import javafx.scene.canvas.GraphicsContext;

public abstract class FigureRender<T extends Figure> {

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

	/**
	 * De la figura seleccionada, se le pasa un estilo con algún cambio el cual
	 * modifica el estilo actual de la figura.
	 * @param style El estilo nuevo de la figura.
	 */
	public void setStyle(FigureStyle style) {
		this.style.setBorderColor(style.getBorderColor());
		this.style.setFillColor(style.getFillColor());
		this.style.setBorderWidth(style.getBorderWidth());
	}

	public FigureStyle getStyle() {
		return style;
	}

	/**
	 * Para dibujar el borde con un color diferente si esta seleccionado o no.
	 */
	public void select() {
		selected = true;
	}

	/**
	 * Para dibujar el borde con un color diferente si esta seleccionado o no.
	 */
	public void deselect() {
		selected = false;
	}

	/**
	 * Al ser llamada la función para dibujar una figura, primero se establece
	 * el estilo según el estilo de la figura a dibujar.
	 * @param gc El contexto donde se dibujará la figura.
	 */
	public void drawSketch(GraphicsContext gc) {
		gc.setLineWidth(style.getBorderWidth());
		gc.setStroke(selected ? style.getSelColor() : style.getBorderColor());
		gc.setFill(style.getFillColor());
	}

	public abstract FigureRender<T> copy();

}
