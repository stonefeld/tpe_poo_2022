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

	/**
	 * Agrega una nueva operación a la pila de deshacer o undo. Al realizar una nueva operación
	 * la pila de rehacer o redo debe limpiarse.
	 * @param operation La última operación realizada a agregar en la pila de deshacer o undo.
	 */
	public void addOperation(Operation operation) {
		cleanRedoStack();
		undoStack.push(operation);
		setUndoRedoLabelText();
	}

	public void cleanRedoStack() {
		redoStack.removeAllElements();
		setUndoRedoLabelText();
	}

	/**
	 * Función de deshacer o undo, donde la última operación del stack es utilizada para sobreescribir el
	 * estado actual del canvas, mientras que el estado actual es metido en la pila de rehacer o redo.
	 * @param list La copia de la lista de figuras obtenidas del canvasState.
	 * @param copied La figura copiada obtenida del canvasState.
	 * @return La operación obtenida de la pila de deshacer o undo para sobreescribir en el canvasState.
	 */
	public Operation undo(List<FigureRender<? extends Figure>> list, FigureRender<? extends Figure> copied) {
		Operation ret = undoStack.pop();
		redoStack.push(new Operation(list, ret.toString(), copied));
		setUndoRedoLabelText();
		return ret;
	}

	/**
	 * Función de rehacer o redo, donde la última operación del stack es utilizada para sobreescribir el
	 * estado actual del canvas, mientras que el estado actual es metido en la pila de deshacer o undo.
	 * @param list La copia de la lista de figuras obtenidas del canvasState.
	 * @param copied La figura copiada obtenida del canvasState.
	 * @return La operación obtenida de la pila de rehacer o redo para sobreescribir en el canvasState.
	 */
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
