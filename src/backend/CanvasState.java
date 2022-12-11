package backend;

import backend.model.Figure;
import frontend.ui.render.FigureRender;
import frontend.ui.render.operations.OperationStack;

import java.util.ArrayList;
import java.util.List;

public class CanvasState {

	private List<FigureRender<? extends Figure>> list = new ArrayList<>();
	private FigureRender<? extends Figure> selectedFigure;

	private final OperationStack stack = new OperationStack();

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

	public void deleteSelected() {
		deleteFigure(selectedFigure);
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

	public Iterable<FigureRender<? extends Figure>> figures() {
		return new ArrayList<>(list);
	}

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
