package frontend;

import backend.model.Figure;
import frontend.ui.render.FigureRender;
import frontend.ui.render.FigureStyle;
import frontend.ui.render.operations.Operation;
import frontend.ui.render.operations.OperationStack;

import java.util.ArrayList;
import java.util.List;

public class CanvasState {

	private final OperationStack stack = new OperationStack();
	private final double canvasHeight, canvasWidth;
	private List<FigureRender<? extends Figure>> list = new ArrayList<>();
	private FigureRender<? extends Figure> selectedFigure;
	private FigureRender<? extends Figure> copiedFigure;
	private FigureStyle styleToCopy;

	public CanvasState(double canvasHeight, double canvasWidth) {
		this.canvasHeight = canvasHeight;
		this.canvasWidth = canvasWidth;
	}

	public double getCanvasHeight() {
		return canvasHeight;
	}

	public double getCanvasWidth() {
		return canvasWidth;
	}

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

	public FigureRender<? extends Figure> paste() {
		FigureRender<? extends Figure> ret = copiedFigure.copy();
		Figure retFigure = ret.getFigure();
		retFigure.move((canvasWidth / 2) - (retFigure.getStartPoint().getX() + retFigure.getEndPoint().getX()) /2,
				(canvasHeight / 2) - (retFigure.getStartPoint().getY() + retFigure.getEndPoint().getY()) /2);
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
	public void addOperation(String description) {
		stack.addOperation(new Operation(getRenderList(), description, getCopiedFigure()));
	}

	public void undo() {
		if (!stack.undoStackEmpty()) {
			Operation undoOperation = stack.undo(getRenderList(), getCopiedFigure());
			list = undoOperation.getState();
			copiedFigure = undoOperation.getCopiedFigure();
		}
	}

	public void redo() {
		if (!stack.redoStackEmpty()) {
			Operation redoOperation = stack.redo(getRenderList(), getCopiedFigure());
			list = redoOperation.getState();
			copiedFigure = redoOperation.getCopiedFigure();
		}
	}

	public OperationStack getOperationStack() {
		return stack;
	}

	private List<FigureRender<? extends Figure>> getRenderList() {
		List<FigureRender<? extends Figure>> toReturn = new ArrayList<>();
		for (FigureRender<? extends Figure> figure : list) {
			toReturn.add(figure.copy());
		}
		return toReturn;
	}

	private FigureRender<? extends Figure> getCopiedFigure() {
		FigureRender<? extends Figure> copied = copiedFigure;
		if (copied != null) {
			copied = copied.copy();
		}
		return copied;
	}

}
