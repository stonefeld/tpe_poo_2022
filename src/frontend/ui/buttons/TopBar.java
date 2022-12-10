package frontend.ui.buttons;

import backend.CanvasState;
import com.sun.javafx.scene.web.skin.HTMLEditorSkin;
import frontend.ui.RedrawCanvas;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

	private void setCopyPasteButtons() {
		String cutIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("cutIcon");
		Image cutIcon = new Image(HTMLEditorSkin.class.getResource(cutIconPath).toString());
		Button cutButton = new Button("Cortar", new ImageView(cutIcon));

		String copyIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("copyIcon");
		Image copyIcon = new Image(HTMLEditorSkin.class.getResource(copyIconPath).toString());
		Button copyButton = new Button("Copiar", new ImageView(copyIcon));

		String pasteIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("pasteIcon");
		Image pasteIcon = new Image(HTMLEditorSkin.class.getResource(pasteIconPath).toString());
		Button pasteButton = new Button("Pegar", new ImageView(pasteIcon));

		HBox copyPasteBox = new HBox(10);
		setHBoxStyle(copyPasteBox);

		Button[] copyPasteButtons = {cutButton, copyButton, pasteButton};
		copyPasteBox.getChildren().addAll(copyPasteButtons);
		getChildren().add(copyPasteBox);
	}

	private void setUndoRedoButtons() {
		String undoIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("undoIcon");
		Image undoIcon = new Image(HTMLEditorSkin.class.getResource(undoIconPath).toString());
		Button undoButton = new Button("Deshacer", new ImageView(undoIcon));
		Label undoLabel = new Label("");
		Label undoNumber = new Label("0");

		String redoIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("redoIcon");
		Image redoIcon = new Image(HTMLEditorSkin.class.getResource(redoIconPath).toString());
		Button redoButton = new Button("Rehacer", new ImageView(redoIcon));
		Label redoLabel = new Label("");
		Label redoNumber = new Label("0");

		HBox undoRedoBox = new HBox(10);
		setHBoxStyle(undoRedoBox);

		Node[] undoRedoItems = {undoLabel, undoNumber, undoButton, redoButton, redoNumber, redoLabel};
		undoRedoBox.getChildren().addAll(undoRedoItems);
		undoRedoBox.setAlignment(Pos.CENTER);
		getChildren().add(undoRedoBox);
	}

	private void setHBoxStyle(HBox box) {
		box.setPadding(new Insets(5));
		box.setStyle("-fx-background-color: #999");
		box.setPrefWidth(100);
	}

}
