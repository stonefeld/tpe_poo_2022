package frontend.ui.render.operations;

import backend.model.Figure;
import frontend.ui.render.FigureRender;
import javafx.scene.control.Label;

import java.util.List;
import java.util.Stack;

public class OperationStack {

	private final Stack<Operation> redoStack = new Stack<>();
	private final Stack<Operation> undoStack = new Stack<>();
	private final Label redoLabel = new Label();
	private final Label undoLabel = new Label();

	public OperationStack() {
		setUndoRedoLabelText();
	}

	public Operation peekUndo() {
		return undoStack.peek();
	}

	public Operation peekRedo() {
		return redoStack.peek();
	}

	public void addOperation(Operation operation) {
		cleanRedoStack();
		undoStack.push(operation);
		setUndoRedoLabelText();
	}

	public void cleanRedoStack() {
		redoStack.removeAllElements();
		setUndoRedoLabelText();
	}

	public Operation undo(List<FigureRender<? extends Figure>> list, FigureRender<? extends Figure> copied) {
		Operation ret = undoStack.pop();
		redoStack.push(new Operation(list, ret.toString(), copied));
		setUndoRedoLabelText();
		return ret;
	}

	public Operation redo(List<FigureRender<? extends Figure>> list, FigureRender<? extends Figure> copied) {
		Operation ret = redoStack.pop();
		undoStack.push(new Operation(list, ret.toString(), copied));
		setUndoRedoLabelText();
		return ret;
	}

	public int undoSize() {
		return undoStack.size();
	}

	public int redoSize() {
		return redoStack.size();
	}

	public boolean redoStackEmpty() {
		return redoStack.isEmpty();
	}

	public boolean undoStackEmpty() {
		return undoStack.isEmpty();
	}

	public Label getUndoLabel() {
		return undoLabel;
	}

	public Label getRedoLabel() {
		return redoLabel;
	}

	private void setUndoRedoLabelText() {
		undoLabel.setText(getUndoStackMessage());
		redoLabel.setText(getRedoStackMessage());
	}

	private String getUndoStackMessage() {
		if (undoStackEmpty()) {
			return "0";
		}
		return String.format("%s %d", peekUndo().toString(), undoSize());
	}

	private String getRedoStackMessage() {
		if (redoStackEmpty()) {
			return "0";
		}
		return String.format("%d %s", redoSize(), peekRedo().toString());
	}

}
