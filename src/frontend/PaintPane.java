package frontend;

import backend.CanvasState;
import backend.model.*;
import frontend.ui.buttons.*;
import frontend.ui.buttons.toggle.MouseActionToggleButton;
import frontend.ui.render.FigureRender;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.util.ResourceBundle;

import static javafx.geometry.Pos.CENTER;

public class PaintPane extends BorderPane {

	// BackEnd
	private final CanvasState canvasState;

	// Canvas y relacionados
	private final Canvas canvas = new Canvas(800, 600);
	private final GraphicsContext gc = canvas.getGraphicsContext2D();

	// El toggle group para saber que boton esta seleccionado
	private final ToggleGroup figuresToggleGroup;

	// StatusBar
	private final StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;

		// Configurando los botones
		ToolBox toolBox = new ToolBox(canvasState, this::redrawCanvas);
		figuresToggleGroup = toolBox.getToggleGroup();

		// Seteando los callbacks para el canvas
		canvas.setOnMousePressed(this::onMousePressedCanvas);
		canvas.setOnMouseReleased(this::onMouseReleasedCanvas);
		canvas.setOnMouseMoved(this::onMouseMovedCanvas);
		canvas.setOnMouseClicked(this::onMouseClickedCanvas);
		canvas.setOnMouseDragged(this::onMouseDraggedCanvas);

		setLeft(toolBox);
		// Setenado el callback para el boton de borrado
		deleteButton.setOnAction(this::onActionDeleteButton);

		borderWidthSlider.setShowTickMarks(true);
		borderWidthSlider.setShowTickLabels(true);
		borderWidthSlider.setBlockIncrement(10);

		Button[] ccpTools = {cutButton, copyButton, pasteButton};
		ccpBox.addAll(ccpTools);

		Button[] undoRedoTools = {undoButton, redoButton};
		undoRedoBox.add(undoLabel);
		undoRedoBox.add(undoNumber);
		undoRedoBox.addAll(undoRedoTools);
		undoRedoBox.add(redoNumber);
		undoRedoBox.add(redoLabel);
		undoRedoBox.setAlignment(CENTER);

		StyledButtonGroup[] topTools = {ccpBox, undoRedoBox};
		topBox.getChildren().addAll(topTools);

		setTop(topBox);
		setLeft(buttonsBox);
		setRight(canvas);
	}

	// CALLBACKS
	private void onMousePressedCanvas(MouseEvent event) {
		Point point = new Point(event.getX(), event.getY());
		((MouseActionToggleButton) figuresToggleGroup.getSelectedToggle()).mousePressedAction(point);
	}

	private void onMouseReleasedCanvas(MouseEvent event) {
		Point point = new Point(event.getX(), event.getY());
		((MouseActionToggleButton) figuresToggleGroup.getSelectedToggle()).mouseReleasedAction(point);
		redrawCanvas();
	}

	private void onMouseMovedCanvas(MouseEvent event) {
		Point eventPoint = new Point(event.getX(), event.getY());
		StringBuilder label = new StringBuilder();
		boolean found = getFigureOnMouse(eventPoint, label) != null;
		statusPane.updateStatus(found ? label.toString() : eventPoint.toString());
	}

	private void onMouseClickedCanvas(MouseEvent event) {
		Point point = new Point(event.getX(), event.getY());
		StringBuilder label = new StringBuilder("Se seleccionó: ");
		((MouseActionToggleButton) figuresToggleGroup.getSelectedToggle()).mouseClickedAction(point, label);
		statusPane.updateStatus(canvasState.getSelectedFigure() != null ? label.toString() : "Ninguna figura encontrada");
		redrawCanvas();
	}

	private void onMouseDraggedCanvas(MouseEvent event) {
		Point point = new Point(event.getX(), event.getY());
		((MouseActionToggleButton) figuresToggleGroup.getSelectedToggle()).mouseDraggedAction(point);
		redrawCanvas();
	}

	private void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for (FigureRender<? extends Figure> figure : canvasState.figures()) {
			figure.drawSketch(gc);
		}
	}

	// Devuelve la ultima figura encontrada bajo las coordenadas del mouse y agrega al label la información
	// de cada figura encontrada
	private Figure getFigureOnMouse(Point point, StringBuilder label) {
		Figure ret = null;
		for (FigureRender<? extends Figure> figure : canvasState.figures()) {
			if (figure.getFigure().belongsToSketch(point)) {
				ret = figure.getFigure();
				label.append(ret);
			}
		}
		return ret;
	}

}
