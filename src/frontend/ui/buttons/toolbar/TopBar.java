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
import javafx.scene.control.Labeled;
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

	private void setUndoRedoButtons() {
		Button undoButton = new Button("Deshacer", getIcon("undoIcon"));
		Button redoButton = new Button("Rehacer", getIcon("redoIcon"));

		undoButton.setOnAction(this::onActionUndoButton);
		redoButton.setOnAction(this::onActionRedoButton);

		HBox undoRedoBox = new HBox(10);
		setHBoxStyle(undoRedoBox);

		setLabelStyle(undoButton, Pos.CENTER, canvasState.getCanvasWidth()/6);
		setLabelStyle(redoButton, Pos.CENTER, canvasState.getCanvasWidth()/6);
		setLabelStyle(canvasState.getOperationStack().getUndoLabel(), Pos.CENTER_RIGHT, canvasState.getCanvasWidth()/3);
		setLabelStyle(canvasState.getOperationStack().getRedoLabel(), Pos.CENTER_LEFT, canvasState.getCanvasWidth()/3);


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

	private void onActionUndoButton(ActionEvent actionEvent) {
		canvasState.undo();
		redrawCanvas.redraw();
	}

	private void onActionRedoButton(ActionEvent actionEvent) {
		canvasState.redo();
		canvasState.deselectFigure();
		redrawCanvas.redraw();
	}

	private void setHBoxStyle(HBox box) {
		box.setPadding(new Insets(5));
		box.setStyle("-fx-background-color: #999");
		box.setPrefWidth(100);
	}

	private ImageView getIcon(String iconName) {
		String iconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString(iconName);
		Image icon = new Image(HTMLEditorSkin.class.getResource(iconPath).toString());
		return new ImageView(icon);
	}

	/**
	 * Función encargada del estilo del Labeled recibido, se lo alinea con la alineacion recibida
	 * y se lo ansancha con el ancho recibido
	 */
	private void setLabelStyle(Labeled label, Pos pos, double width){
		label.setAlignment(pos);
		label.setPrefWidth(width);
	}

}
