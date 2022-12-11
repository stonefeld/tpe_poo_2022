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
		SideBar sideBar = new SideBar(canvasState, statusPane, this::redrawCanvas);
		figuresToggleGroup = sideBar.getToggleGroup();
		TopBar topBar = new TopBar(canvasState, this::redrawCanvas);

		// Seteando los callbacks para el canvas
		canvas.setOnMousePressed(this::onMousePressedCanvas);
		canvas.setOnMouseReleased(this::onMouseReleasedCanvas);
		canvas.setOnMouseMoved(this::onMouseMovedCanvas);
		canvas.setOnMouseClicked(this::onMouseClickedCanvas);
		canvas.setOnMouseDragged(this::onMouseDraggedCanvas);

		setTop(topBar);
		setLeft(sideBar);
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
