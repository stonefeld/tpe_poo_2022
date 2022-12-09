package frontend;

import backend.CanvasState;
import backend.model.*;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PaintPane extends BorderPane {

	// BackEnd
	CanvasState canvasState;

	// Canvas y relacionados
	Canvas canvas = new Canvas(800, 600);
	GraphicsContext gc = canvas.getGraphicsContext2D();
	Color lineColor = Color.BLACK;
	Color fillColor = Color.YELLOW;

	// Botones Barra Izquierda
	/*
****************IDEA********************
	FigureToggleButton clase abstracta/ Interfaz que extiende a ToggleButton
****************IDEA********************

	 * */
	ToggleButton selectionButton = new ToggleButton("Seleccionar");
	ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	ToggleButton circleButton = new ToggleButton("Círculo");
	ToggleButton squareButton = new ToggleButton("Cuadrado");
	ToggleButton ellipseButton = new ToggleButton("Elipse");
	ToggleButton deleteButton = new ToggleButton("Borrar");

	// Dibujar una figura
	Point startPoint;

	// Seleccionar una figura
	Figure selectedFigure;

	// StatusBar
	StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(1);

		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());
		});

		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());
			if (startPoint == null) {
				return;
			}
			if (endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
				return;
			}


			Figure newFigure = null;
			if (rectangleButton.isSelected()) {
				newFigure = new Rectangle(startPoint, endPoint);
			} else if (circleButton.isSelected()) {
				double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
				newFigure = new Circle(startPoint, endPoint);
			} else if (squareButton.isSelected()) {
				double size = Math.abs(endPoint.getX() - startPoint.getX());
				newFigure = new Square(startPoint, size);
			} else if (ellipseButton.isSelected()) {
				Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
				double sMayorAxis = Math.abs(endPoint.getX() - startPoint.getX());
				double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
				newFigure = new Ellipse(centerPoint, endPoint);
			} else {
				return;
			}
			canvasState.addFigure(newFigure);
			startPoint = null;
			redrawCanvas();
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();
			for (Figure figure : canvasState.figures()) {
				if (figureBelongs(figure, eventPoint)) {
					found = true;
					label.append(figure.toString());
				}
			}
			if (found) {
				statusPane.updateStatus(label.toString());
			} else {
				statusPane.updateStatus(eventPoint.toString());
			}
		});

		canvas.setOnMouseClicked(event -> {
			if (selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				boolean found = false;
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				for (Figure figure : canvasState.figures()) {
					if (figureBelongs(figure, eventPoint)) {
						found = true;
						selectedFigure = figure;
						label.append(figure.toString());
					}
				}
				if (found) {
					statusPane.updateStatus(label.toString());
				} else {
					selectedFigure = null;
					statusPane.updateStatus("Ninguna figura encontrada");
				}
				redrawCanvas();
			}
		});

		canvas.setOnMouseDragged(event -> {
			if (selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
				/*
				should be
				selectedFigure.move(diffX, diffY);
				redrawCanvas();
				 */
				if (selectedFigure instanceof Square) {
					Square square = (Square) selectedFigure;
					square.move(diffX, diffY);
				} else if (selectedFigure instanceof Rectangle) {
					Rectangle rectangle = (Rectangle) selectedFigure;
					rectangle.move(diffX, diffY);
				} else if (selectedFigure instanceof Circle) {
					Circle circle = (Circle) selectedFigure;
					circle.move(diffX, diffY);
				} else if (selectedFigure instanceof Ellipse) {
					Ellipse ellipse = (Ellipse) selectedFigure;
					ellipse.move(diffX, diffY);
				}
				redrawCanvas();
			}
		});

		deleteButton.setOnAction(event -> {
			if (selectedFigure != null) {
				canvasState.deleteFigure(selectedFigure);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		setLeft(buttonsBox);
		setRight(canvas);
	}


	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for (Figure figure : canvasState.figures()) {
			if (figure == selectedFigure) {
				gc.setStroke(Color.RED);
			} else {
				gc.setStroke(lineColor);
			}
			gc.setFill(fillColor);
			/*
			****************IDEA********************
				MÉTODO DE FIGURE que devuelve
			****************IDEA********************
			 */
			if (figure instanceof Square) {
				Square square = (Square) figure;
				gc.fillRect(square.getStartPoint().getX(), square.getStartPoint().getY(),
						square.getWidth(), square.getHeight());
				gc.strokeRect(square.getStartPoint().getX(), square.getStartPoint().getY(),
						square.getWidth(), square.getHeight());
			} else if (figure instanceof Rectangle) {
				Rectangle rectangle = (Rectangle) figure;
				gc.fillRect(rectangle.getStartPoint().getX(), rectangle.getStartPoint().getY(),
						rectangle.getWidth(), rectangle.getHeight());
				gc.strokeRect(rectangle.getStartPoint().getX(), rectangle.getStartPoint().getY(),
						rectangle.getWidth(), rectangle.getHeight());
			} else if (figure instanceof Circle) {
				Circle circle = (Circle) figure;
				double diameter = getHeight();
				gc.fillOval(circle.getCenterPoint().getX() - circle.getWidth()/2, circle.getCenterPoint().getY() - circle.getWidth()/2, diameter, diameter);
				gc.strokeOval(circle.getCenterPoint().getX() - circle.getWidth()/2, circle.getCenterPoint().getY() - circle.getWidth()/2, diameter, diameter);
			} else if (figure instanceof Ellipse) {
				Ellipse ellipse = (Ellipse) figure;
				gc.strokeOval(ellipse.getCenterPoint().getX() - (ellipse.getWidth() / 2), ellipse.getCenterPoint().getY() - (ellipse.getHeight() / 2), ellipse.getWidth(), ellipse.getHeight());
				gc.fillOval(ellipse.getCenterPoint().getX() - (ellipse.getWidth() / 2), ellipse.getCenterPoint().getY() - (ellipse.getHeight() / 2), ellipse.getWidth(), ellipse.getHeight());
			}
		}
	}


	/*
****************IDEA********************
	figureBelongs MÉTODO DE FIGURE
****************IDEA********************
	 */
	boolean figureBelongs(Figure figure, Point eventPoint) {
		boolean found = false;
		if (figure instanceof Square) {
			Square square = (Square) figure;
			found = eventPoint.getX() > square.getStartPoint().getX() && eventPoint.getX() < square.getEndPoint().getX() &&
					eventPoint.getY() > square.getStartPoint().getY() && eventPoint.getY() < square.getEndPoint().getY();
		} else if (figure instanceof Rectangle) {
			Rectangle rectangle = (Rectangle) figure;
			found = eventPoint.getX() > rectangle.getStartPoint().getX() && eventPoint.getX() < rectangle.getEndPoint().getX() &&
					eventPoint.getY() > rectangle.getStartPoint().getY() && eventPoint.getY() < rectangle.getEndPoint().getY();
		} else if (figure instanceof Circle) {
			Circle circle = (Circle) figure;
			found = Math.sqrt(Math.pow(circle.getCenterPoint().getX() - eventPoint.getX(), 2) +
					Math.pow(circle.getCenterPoint().getY() - eventPoint.getY(), 2)) < circle.getWidth()/2;
		} else if (figure instanceof Ellipse) {
			Ellipse ellipse = (Ellipse) figure;
			// Nota: Fórmula aproximada. No es necesario corregirla.
			found = ((Math.pow(eventPoint.getX() - ellipse.getCenterPoint().getX(), 2) / Math.pow(ellipse.getHeight(), 2)) +
					(Math.pow(eventPoint.getY() - ellipse.getCenterPoint().getY(), 2) / Math.pow(ellipse.getHeight(), 2))) <= 0.30;
		}
		return found;
	}

}
