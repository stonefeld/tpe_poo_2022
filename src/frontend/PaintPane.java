package frontend;

import backend.CanvasState;
import backend.model.*;
import frontend.ui.buttons.FigureStyledToggleButton;
import frontend.ui.buttons.StyledButton;
import frontend.ui.buttons.StyledToggleButton;
import frontend.ui.buttons.StyledToggleButtonGroup;
import frontend.ui.render.FigureRender;
import frontend.ui.render.FigureStyle;
import frontend.ui.render.RectangleRender;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Toggle;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class PaintPane extends BorderPane {

	// BackEnd
	private final CanvasState canvasState;

	// Canvas y relacionados
	private final Canvas canvas = new Canvas(800, 600);
	private final GraphicsContext gc = canvas.getGraphicsContext2D();

	// Color de borde, relleno y seleccion
	private final FigureStyle currentStyle = new FigureStyle(Color.BLACK, Color.YELLOW);

	// Botones Barra Izquierda
	private final StyledToggleButton selectionButton = new StyledToggleButton("Seleccionar");
	private final FigureStyledToggleButton<Rectangle> rectangleButton = new FigureStyledToggleButton<>("Rectángulo",
			(p1, p2, style) -> new RectangleRender<>(style, new Rectangle(p1, p2)));
	private final FigureStyledToggleButton<Circle> circleButton = new FigureStyledToggleButton<>("Círculo",
			(p1, p2, style) -> new FigureRender<>(style, new Circle(p1, p2)));
	private final FigureStyledToggleButton<Square> squareButton = new FigureStyledToggleButton<>("Cuadrado",
			(p1, p2, style) -> new FigureRender<>(style, new Square(p1, p2)));
	private final FigureStyledToggleButton<Ellipse> ellipseButton = new FigureStyledToggleButton<>("Elipse",
			(p1, p2, style) -> new FigureRender<>(style, new Ellipse(p1, p2)));
	private final StyledButton deleteButton = new StyledButton("Borrar");
	private final StyledToggleButtonGroup buttonsBox = new StyledToggleButtonGroup();

	private final StyledToggleButton copyFormatButton = new StyledToggleButton("Cop. Form.");
	private final Slider borderWidthSlider = new Slider(1, 50, currentStyle.getBorderWidth());
	private final Label borderLabel = new Label("Borde");
	private final Label fillingLabel = new Label("Relleno");
	private final ColorPicker borderColorPicker = new ColorPicker(currentStyle.getBorderColor());
	private final ColorPicker fillingColorPicker = new ColorPicker(currentStyle.getFillColor());

	// Dibujar una figura
	private Point startPoint;

	// Seleccionar una figura
	private Figure selectedFigure;

	// StatusBar
	private final StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;

		// Configurando los botones
		StyledToggleButton[] figuresTools = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton};
		Node[] utilityTools = {deleteButton, copyFormatButton, borderLabel, borderWidthSlider, borderColorPicker, fillingLabel, fillingColorPicker};
		buttonsBox.addToggleButtons(figuresTools);
		buttonsBox.addAll(utilityTools);

		// Seteando los callbacks para el canvas
		canvas.setOnMousePressed(this::onMousePressedCanvas);
		canvas.setOnMouseReleased(this::onMouseReleasedCanvas);
		canvas.setOnMouseMoved(this::onMouseMovedCanvas);
		canvas.setOnMouseClicked(this::onMouseClickedCanvas);
		canvas.setOnMouseDragged(this::onMouseDraggedCanvas);

		// Setenado el callback para el boton de borrado
		deleteButton.setOnAction(this::onActionDeleteButton);

		borderWidthSlider.setShowTickMarks(true);
		borderWidthSlider.setShowTickLabels(true);
		borderWidthSlider.setBlockIncrement(10);

		setLeft(buttonsBox);
		setRight(canvas);
	}

	// CALLBACKS
	private void onMousePressedCanvas(MouseEvent event) {
		startPoint = new Point(event.getX(), event.getY());
	}

	private void onMouseReleasedCanvas(MouseEvent event) {
		Point endPoint = new Point(event.getX(), event.getY());
		if (isValidPoint(endPoint)) {
			Toggle selected = buttonsBox.getSelected();
			if (selected != selectionButton) {
				canvasState.addFigure(((FigureStyledToggleButton) selected)
						.render(startPoint, endPoint, currentStyle.copy()));
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
		for (FigureRender<? extends Figure> figure : canvasState.figures()) {
			figure.drawSketch(gc, figure.getFigure() == selectedFigure);
		}
	}

	// Valida si el segundo punto es correcto segun las restricciones
	private boolean isValidPoint(Point endPoint) {
		return startPoint != null &&
				!(endPoint.getX() < startPoint.getX()) && !(endPoint.getY() < startPoint.getY());
	}

	// Devuelve la ultima figura encontrada bajo las coordenadas del mouse y agrega al label la información
	// de cada figura encontrada
	private Figure getFigureOnMouse(Point point, StringBuilder label) {
		Figure ret = null;
		for (FigureRender<? extends Figure> figure : canvasState.figures()) {
			if (figure.getFigure().belongsToSketch(point)) {
				ret = figure.getFigure();
				label.append(figure);
			}
		}
		return ret;
	}

}
