package frontend.ui.buttons.toolbar;

import backend.model.Figure;
import com.sun.javafx.scene.web.skin.HTMLEditorSkin;
import frontend.CanvasState;
import frontend.ui.RedrawCanvas;
import frontend.ui.render.FigureRender;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ResourceBundle;

public class TopBar extends VBox {

	private final CanvasState canvasState;
	private final RedrawCanvas redrawCanvas;

	public TopBar(CanvasState canvasState, RedrawCanvas redrawCanvas) {
		super(10);
		setPadding(new Insets(5));
		setStyle("-fx-background-color: #999");
		setPrefWidth(100);

		this.canvasState = canvasState;
		this.redrawCanvas = redrawCanvas;

		setCopyPasteButtons();
		setUndoRedoButtons();
	}

	/**
	 * Callback público para que el PaintPane pueda utilizar esta función para las llamadas por
	 * combinación de teclas.
	 * @param event El evento que contiene la combinación de teclas para tomar acción al respecto.
	 */
	public void processCode(KeyEvent event) {
		if (event.isControlDown()) {
			if (event.getCode() == KeyCode.X) {
				onActionCutButton();
			} else if (event.getCode() == KeyCode.C) {
				onActionCopyButton();
			} else if (event.getCode() == KeyCode.V) {
				onActionPasteButton();
			}
		}
	}

	/**
	 * Función encargada de configurar la sección de elementos de copy, cut and paste con sus respectivos
	 * callbacks, íconos y funciones.
	 */
	private void setCopyPasteButtons() {
		Button cutButton = new Button("Cortar", getIcon("cutIcon"));
		Button copyButton = new Button("Copiar", getIcon("copyIcon"));
		Button pasteButton = new Button("Pegar", getIcon("pasteIcon"));

		cutButton.setOnAction(event -> onActionCutButton());
		copyButton.setOnAction(event -> onActionCopyButton());
		pasteButton.setOnAction(event -> onActionPasteButton());

		HBox copyPasteBox = new HBox(10);
		setHBoxStyle(copyPasteBox);

		Button[] copyPasteButtons = {cutButton, copyButton, pasteButton};
		copyPasteBox.getChildren().addAll(copyPasteButtons);
		getChildren().add(copyPasteBox);
	}

	/**
	 * Función encargada de configurar la sección de elementos de undo y redo con sus respectivos
	 * callbacks, íconos y funciones.
	 */
	private void setUndoRedoButtons() {
		Button undoButton = new Button("Deshacer", getIcon("undoIcon"));
		Button redoButton = new Button("Rehacer", getIcon("redoIcon"));

		undoButton.setOnAction(this::onActionUndoButton);
		redoButton.setOnAction(this::onActionRedoButton);

		HBox undoRedoBox = new HBox(10);
		setHBoxStyle(undoRedoBox);

		Node[] undoRedoItems = {
				canvasState.getOperationStack().getUndoLabel(),
				undoButton,
				redoButton,
				canvasState.getOperationStack().getRedoLabel()
		};
		undoRedoBox.getChildren().addAll(undoRedoItems);
		undoRedoBox.setAlignment(Pos.CENTER);
		getChildren().add(undoRedoBox);
	}

	/**
	 * Función encargada de la funcionalidad de corte o cut tanto para la combinación de teclas
	 * como para los botones en el topBar.
	 */
	private void onActionCutButton() {
		if (canvasState.existsSelected()) {
			canvasState.addOperation(String.format("Cortar %s", canvasState.getSelected().getFigure().name()));
			canvasState.copySelected();
			canvasState.deleteSelected();
			redrawCanvas.redraw();
		}
	}

	/**
	 * Función encargada de la funcionalidad de copiado o copy tanto para la combinación de teclas
	 * como para los botones en el topBar.
	 */
	private void onActionCopyButton() {
		if (canvasState.existsSelected()) {
			canvasState.addOperation(String.format("Copiar %s", canvasState.getSelected().getFigure().name()));
			canvasState.copySelected();
		}
	}

	/**
	 * Función encargada de la funcionalidad de pegado o paste tanto para la combinación de teclas
	 * como para los botones en el topBar.
	 */
	private void onActionPasteButton() {
		if (canvasState.existsCopied()) {
			FigureRender<? extends Figure> aux = canvasState.paste();
			canvasState.addOperation(String.format("Pegar %s", aux.getFigure().name()));
			canvasState.addFigure(aux);
			redrawCanvas.redraw();
		}
	}

	/**
	 * Función encargada de la funcionalidad de deshacer o undo para los botones en el topBar.
	 */
	private void onActionUndoButton(ActionEvent actionEvent) {
		canvasState.undo();
		redrawCanvas.redraw();
	}

	/**
	 * Función encargada de la funcionalidad de rehacer o redo para los botones en el topBar.
	 */
	private void onActionRedoButton(ActionEvent actionEvent) {
		canvasState.redo();
		canvasState.deselectFigure();
		redrawCanvas.redraw();
	}

	/**
	 * Una función genérica para establecer todos los botones con el mismo estilo.
	 * @param box Es el elemento a ser configurado con el estilo deseado.
	 */
	private void setHBoxStyle(HBox box) {
		box.setPadding(new Insets(5));
		box.setStyle("-fx-background-color: #999");
		box.setPrefWidth(100);
	}

	/**
	 * Para reducir el nivel de código repetido se creo esta función para la creación de los
	 * íconos de los botones.
	 * @param iconName El nombre del ícono deseado para buscar.
	 * @return El ícono ya creado para utilizar en los botones.
	 */
	private ImageView getIcon(String iconName) {
		String iconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString(iconName);
		Image icon = new Image(HTMLEditorSkin.class.getResource(iconPath).toString());
		return new ImageView(icon);
	}

}
