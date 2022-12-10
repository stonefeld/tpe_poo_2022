package frontend;

import backend.CanvasState;
import backend.model.*;
import frontend.ui.*;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Toggle;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class PaintPane extends BorderPane {

	// BackEnd
	CanvasState canvasState;

	// Canvas y relacionados
	Canvas canvas = new Canvas(800, 600);
	GraphicsContext gc = canvas.getGraphicsContext2D();
	Color lineColor = Color.BLACK, fillColor = Color.YELLOW, selColor = Color.RED;

	// Botones Barra Izquierda
	StyledToggleButton selectionButton = new StyledToggleButton("Seleccionar");
	FigureStyledToggleButton rectangleButton = new RectangleFigureStyledToggleButton("Rectángulo");
	FigureStyledToggleButton circleButton = new CircleFigureStyledToggleButton("Círculo");
	FigureStyledToggleButton squareButton = new SquareFigureStyledToggleButton("Cuadrado");
	FigureStyledToggleButton ellipseButton = new EllipseFigureStyledToggleButton("Elipse");
	StyledButton deleteButton = new StyledButton("Borrar");
	StyledToggleButtonGroup buttonsBox = new StyledToggleButtonGroup();

	// Dibujar una figura
	Point startPoint;

	// Seleccionar una figura
	Figure selectedFigure;

	// StatusBar
	StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;

		StyledToggleButton[] figuresTools = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton};
		buttonsBox.addAll(figuresTools);
		buttonsBox.addButton(deleteButton);

		gc.setLineWidth(1);

		// Seteando los callbacks para el canvas
		canvas.setOnMousePressed(this::onMousePressedCanvas);
		canvas.setOnMouseReleased(this::onMouseReleasedCanvas);
		canvas.setOnMouseMoved(this::onMouseMovedCanvas);
		canvas.setOnMouseClicked(this::onMouseClickedCanvas);
		canvas.setOnMouseDragged(this::onMouseDraggedCanvas);

		// Setenado el callback para el boton de borrado
		deleteButton.setOnAction(this::onActionDeleteButton);

		setLeft(buttonsBox);
		setRight(canvas);
	}

	private void onMousePressedCanvas(MouseEvent event) {
		startPoint = new Point(event.getX(), event.getY());
	}

	private void onMouseReleasedCanvas(MouseEvent event) {
		Point endPoint = new Point(event.getX(), event.getY());
		if (isValidPoint(endPoint)) {
			Toggle selected = buttonsBox.getSelected();
			if (selected != selectionButton) {
				canvasState.addFigure(((FigureStyledToggleButton) selected).createFigure(startPoint, endPoint));
			}
			startPoint = null;
			redrawCanvas();
		}
	}

	private void onMouseMovedCanvas(MouseEvent event) {
		Point eventPoint = new Point(event.getX(), event.getY());
		StringBuilder label = new StringBuilder();
		boolean found = getFigureOnMouse(eventPoint, label) != null;
		statusPane.updateStatus(found ? label.toString() : eventPoint.toString());
	}

	private void onMouseClickedCanvas(MouseEvent event) {
		if (selectionButton.isSelected()) {
			Point eventPoint = new Point(event.getX(), event.getY());
			StringBuilder label = new StringBuilder("Se seleccionó: ");
			selectedFigure = getFigureOnMouse(eventPoint, label);
			statusPane.updateStatus(selectedFigure != null ? label.toString() : "Ninguna figura encontrada");
			redrawCanvas();
		}
	}

	private void onMouseDraggedCanvas(MouseEvent event) {
		if (selectionButton.isSelected() && selectedFigure != null) {
			Point eventPoint = new Point(event.getX(), event.getY());
			double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
			double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
			selectedFigure.move(diffX, diffY);
			redrawCanvas();
		}
	}

	private void onActionDeleteButton(ActionEvent event) {
		if (selectedFigure != null) {
			canvasState.deleteFigure(selectedFigure);
			selectedFigure = null;
			redrawCanvas();
		}
	}

	private void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for (Figure figure : canvasState.figures()) {
			gc.setStroke(figure == selectedFigure ? selColor : lineColor);
			gc.setFill(fillColor);
			figure.drawSketch(gc);
		}
	}

	private boolean isValidPoint(Point endPoint) {
		return startPoint != null &&
				!(endPoint.getX() < startPoint.getX()) && !(endPoint.getY() < startPoint.getY());
	}

	private Figure getFigureOnMouse(Point point, StringBuilder label) {
		Figure ret = null;
		for (Figure figure : canvasState.figures()) {
			if (figure.belongsToSketch(point)) {
				ret = figure;
				label.append(figure);
			}
		}
		return ret;
	}

}
