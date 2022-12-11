package backend;

import backend.model.Figure;
import frontend.ui.render.FigureRender;
import frontend.ui.render.FigureStyle;

import java.util.ArrayList;
import java.util.List;

public class CanvasState {

	private final List<FigureRender<? extends Figure>> list = new ArrayList<>();
	private FigureRender<? extends Figure> selectedFigure;
	private FigureStyle styleToCopy;

	public void addFigure(FigureRender<? extends Figure> figure) {
		list.add(figure);
	}

	public void deleteFigure(FigureRender<? extends Figure> figure) {
		list.remove(figure);
	}

	public void deleteSelected() {
		deleteFigure(selectedFigure);
		deselectFigure();
	}

	public void deselectFigure() {
		selectedFigure = null;
	}

	public void selectFigure(FigureRender<? extends Figure> figure) {
		selectedFigure = figure;
	}

	public boolean existsSelected() {
		return selectedFigure != null;
	}

	public FigureRender<? extends Figure> getSelectedFigure() {
		return selectedFigure;
	}

	public FigureStyle getStyleToCopy() {
		FigureStyle ret = styleToCopy;
		styleToCopy = null;
		return ret;
	}

	public void setStyleToCopy() {
		styleToCopy = selectedFigure.getStyle();
	}

	public boolean existsStyleToCopy() {
		return styleToCopy != null;
	}

	public Iterable<FigureRender<? extends Figure>> figures() {
		return new ArrayList<>(list);
	}

}
