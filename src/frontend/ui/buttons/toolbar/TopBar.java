package frontend.ui.buttons.toolbar;

import com.sun.javafx.scene.web.skin.HTMLEditorSkin;
import frontend.CanvasState;
import frontend.ui.RedrawCanvas;
import frontend.ui.render.CopyPasteActions;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ResourceBundle;

public class TopBar extends VBox {

	private final CanvasState canvasState;
	private final RedrawCanvas redrawCanvas;

	public TopBar(CanvasState canvasState, CopyPasteActions cutAction, CopyPasteActions copyAction, CopyPasteActions pasteAction, RedrawCanvas redrawCanvas) {
		super(10);
		setPadding(new Insets(5));
		setStyle("-fx-background-color: #999");
		setPrefWidth(100);

		this.canvasState = canvasState;
		this.redrawCanvas = redrawCanvas;

		setCopyPasteButtons(cutAction, copyAction, pasteAction);
		setUndoRedoButtons();
	}

	private void setCopyPasteButtons(CopyPasteActions cutAction, CopyPasteActions copyAction, CopyPasteActions pasteAction) {
		Button cutButton = new Button("Cortar", getIcon("cutIcon"));
		Button copyButton = new Button("Copiar", getIcon("copyIcon"));
		Button pasteButton = new Button("Pegar", getIcon("pasteIcon"));

		cutButton.setOnAction(event -> cutAction.action());
		copyButton.setOnAction(event -> copyAction.action());
		pasteButton.setOnAction(event -> pasteAction.action());

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

}
