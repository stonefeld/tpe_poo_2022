package frontend;

import backend.model.Figure;
import frontend.ui.render.FigureRender;
import frontend.ui.render.operations.OperationStack;

import frontend.ui.render.FigureStyle;


import java.util.ArrayList;
import java.util.List;

public class CanvasState {

	private List<FigureRender<? extends Figure>> list = new ArrayList<>();
	private FigureRender<? extends Figure> selectedFigure;
	private FigureRender<? extends Figure> copiedFigure;
	private FigureStyle styleToCopy;
	private final OperationStack stack = new OperationStack();

	public void addFigure(FigureRender<? extends Figure> figure) {
		list.add(figure);
	}

	public void deleteFigure(FigureRender<? extends Figure> figure) {
		list.remove(figure);
	}

	public Iterable<FigureRender<? extends Figure>> figures() {
		return new ArrayList<>(list);
	}

	// SELECT FIGURE
	public void deleteSelected() {
		deleteFigure(selectedFigure);
		deselectFigure();
	}

	public void selectFigure(FigureRender<? extends Figure> figure) {
		selectedFigure = figure;
	}

	public void deselectFigure() {
		selectedFigure = null;
	}

	public boolean existsSelected() {
		return selectedFigure != null;
	}

	public FigureRender<? extends Figure> getSelected() {
		return selectedFigure;
	}

	// COPY FIGURE
	public void copySelected() {
		copiedFigure = selectedFigure.copy();
	}

	public FigureRender<? extends Figure> getCopied() {
		FigureRender<? extends Figure> ret = copiedFigure;
		copiedFigure = copiedFigure.copy();
		return ret;
	}

	public boolean existsCopied() {
		return copiedFigure != null;
	}

	// COPY STYLE
	public void setStyleToCopy() {
		styleToCopy = selectedFigure.getStyle();
	}

	public FigureStyle getStyleToCopy() {
		FigureStyle ret = styleToCopy;
		styleToCopy = null;
		return ret;
	}

	public boolean existsStyleToCopy() {
		return styleToCopy != null;
	}

	// OPERATION
	public void setRenderList(List<FigureRender<? extends Figure>> list) {
		this.list = list;
	}

	public List<FigureRender<? extends Figure>> getRenderList() {
		return new ArrayList<>(list);
	}

	public OperationStack getStack() {
		return stack;
	}

}
