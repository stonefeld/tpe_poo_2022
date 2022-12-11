package frontend.ui.render.operations;

import backend.model.Figure;
import frontend.ui.render.FigureRender;

import java.util.List;

/**
 * Cada operación guarda los estados relevantes del canvasState, es decir, el listado de figuras
 * a renderizar, y la figura copiada. Además de una descripción de la operación utilizada para
 * los labels.
 */
public class Operation {

	private final List<FigureRender<? extends Figure>> state;
	private final String description;
	private final FigureRender<? extends Figure> copiedFigure;

	public Operation(List<FigureRender<? extends Figure>> state, String description, FigureRender<? extends Figure> copiedFigure) {
		this.state = state;
		this.description = description;
		this.copiedFigure = copiedFigure;
	}

	public List<FigureRender<? extends Figure>> getState() {
		return state;
	}

	public FigureRender<? extends Figure> getCopiedFigure() {
		return copiedFigure;
	}

	@Override
	public String toString() {
		return description;
	}

}
