package backend;

import backend.model.Figure;
import frontend.ui.Rendering.FigureRender;

import java.util.ArrayList;
import java.util.List;

public class CanvasState {

	private final List<FigureRender<? extends Figure>> list = new ArrayList<>();

	public void addFigure(FigureRender<? extends Figure> figure) {
		list.add(figure);
	}

	public void deleteFigure(FigureRender<? extends Figure> figure) {
		list.remove(figure);
	}

	public Iterable<FigureRender<? extends Figure>> figures() {
		return new ArrayList<>(list);
	}

}
