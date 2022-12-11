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

	/**
	 * @return Devuelve la altura del canvas.
	 */
	public double getCanvasHeight() {
		return canvasHeight;
	}

	/**
	 * @return Devuelve el ancho del canvas.
	 */
	public double getCanvasWidth() {
		return canvasWidth;
	}

	/**
	 * Agrega una figura a la lista de figuras para renderizar.
	 * @param figure Es la figura a ser agregada.
	 */
	public void addFigure(FigureRender<? extends Figure> figure) {
		list.add(figure);
	}

	/**
	 * Elimina una figura de la lista de figuras para renderizar.
	 * @param figure Es la figura a ser eliminada
	 */
	public void deleteFigure(FigureRender<? extends Figure> figure) {
		list.remove(figure);
	}

	/**
	 * Utilizada para obtener una copia sobre la cual iterar de las figuras seleccionadas
	 * para ser renderizadas.
	 * @return Un Iterable del tipo de las figuras
	 */
	public Iterable<FigureRender<? extends Figure>> figures() {
		return new ArrayList<>(list);
	}

	/**
	 * Elimina la figura que haya estado seleccionada.
	 */
	public void deleteSelected() {
		deleteFigure(selectedFigure);
		deselectFigure();
	}

	/**
	 * Selecciona una figura.
	 * @param figure La figura a ser seleccionada.
	 */
	public void selectFigure(FigureRender<? extends Figure> figure) {
		selectedFigure = figure;
	}

	/**
	 * Elimina la selección de la figura previamente seleccionada.
	 */
	public void deselectFigure() {
		selectedFigure = null;
	}

	/**
	 * Verifica que exista una figura seleccionada.
	 * @return Verdadero o True en caso de existir una figura seleccionada. Falso o
	 * False en caso contrario.
	 */
	public boolean existsSelected() {
		return selectedFigure != null;
	}

	/**
	 * Para obtener la figura seleccionada y hacer cambios sobre ella.
	 * @return La figura actualmente seleccionada.
	 */
	public FigureRender<? extends Figure> getSelected() {
		return selectedFigure;
	}

	/**
	 * Realiza una copia de la figura actualmente seleccionada.
	 */
	public void copySelected() {
		if (existsSelected()) {
			copiedFigure = selectedFigure.copy();
		}
	}

	/**
	 * Utilizada para pegar la figura copiada. Realiza una copia de la figura y la mueve al centro
	 * para que al ser pegada aparezca en medio del canvas.
	 * @return La figura copiada con sus coordenadas modificadas para aparecer en el centro.
	 */
	public FigureRender<? extends Figure> paste() {
		FigureRender<? extends Figure> ret = getCopiedFigure();
		copiedFigure = null;
		if (ret != null) {
			Figure retFigure = ret.getFigure();
			retFigure.move((canvasWidth / 2) - (retFigure.getStartPoint().getX() + retFigure.getEndPoint().getX()) / 2,
					(canvasHeight / 2) - (retFigure.getStartPoint().getY() + retFigure.getEndPoint().getY()) / 2);
		}
		return ret;
	}

	/**
	 * Verifica que exista una figura copiada.
	 * @return Verdadero o True en caso de existir una figura copiada. Falso o
	 * False en caso contrario.
	 */
	public boolean existsCopied() {
		return copiedFigure != null;
	}

	/**
	 * Guarda el estilo de la figura actualmente seleccionada.
	 */
	public void setStyleToCopy() {
		if (existsSelected()) {
			styleToCopy = selectedFigure.getStyle();
		}
	}

	/**
	 * Obtiene el estilo que iba a ser copiado y elimina el estilo para no seguir
	 * copiandolo a otras figuras al seleccionarlas.
	 * @return El estilo de la figura seleccionada para copiar su formato.
	 */
	public FigureStyle getStyleToCopy() {
		FigureStyle ret = styleToCopy;
		styleToCopy = null;
		return ret;
	}

	/**
	 * Verifica que exista un estilo para copiar.
	 * @return Verdadero o True en caso de existir un estilo para copiar. Falso o
	 * False en caso contrario.
	 */
	public boolean existsStyleToCopy() {
		return styleToCopy != null;
	}

	/**
	 * Agrega una operación al operationStack, utilizado para el undo y el redo.
	 * @param description Descripción utilizada para la operación que luego será plasmada en el
	 * Label de sus correspondientes botones.
	 */
	public void addOperation(String description) {
		stack.addOperation(new Operation(getRenderList(), description, getCopiedFigure()));
	}

	/**
	 * Realiza la operación completa de deshacer o undo sobre el operationStack.
	 */
	public void undo() {
		if (!stack.undoStackEmpty()) {
			Operation undoOperation = stack.undo(getRenderList(), getCopiedFigure());
			list = undoOperation.getState();
			copiedFigure = undoOperation.getCopiedFigure();
		}
	}

	/**
	 * Realiza la operación completa de rehacer o redo sobre el operationStack.
	 */
	public void redo() {
		if (!stack.redoStackEmpty()) {
			Operation redoOperation = stack.redo(getRenderList(), getCopiedFigure());
			list = redoOperation.getState();
			copiedFigure = redoOperation.getCopiedFigure();
		}
	}

	/**
	 * Para obtener el operationStack y realizar algún cambio u obtener sus propiedades
	 * como los labels.
	 * @return El stack de operaciones utilizada para el undo y el redo.
	 */
	public OperationStack getOperationStack() {
		return stack;
	}

	/**
	 * Realiza una copia de la lista de elementos seleccionados para renderizar.
	 * @return La lista copiada, elemento por elemento.
	 */
	private List<FigureRender<? extends Figure>> getRenderList() {
		List<FigureRender<? extends Figure>> toReturn = new ArrayList<>();
		for (FigureRender<? extends Figure> figure : list) {
			toReturn.add(figure.copy());
		}
		return toReturn;
	}

	/**
	 * Realiza una copia de la figura copiada.
	 * @return La copia de la figura copiada.
	 */
	private FigureRender<? extends Figure> getCopiedFigure() {
		FigureRender<? extends Figure> copied = copiedFigure;
		if (copied != null) {
			copied = copied.copy();
		}
		return copied;
	}

}
