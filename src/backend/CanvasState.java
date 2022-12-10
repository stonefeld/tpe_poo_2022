package backend;

import backend.model.Figure;
import frontend.ui.render.FigureRender;

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

	public void deleteFigure(Figure figure) {
		for (FigureRender<? extends Figure> element : list) {
			if (element.getFigure().equals(figure)) {
				list.remove(element);
				return;
			}
		}
	}

	public Iterable<FigureRender<? extends Figure>> figures() {
		return new ArrayList<>(list);
	}

}
