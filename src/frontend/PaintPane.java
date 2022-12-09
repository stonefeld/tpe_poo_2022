package frontend;

import backend.CanvasState;
import backend.model.*;
import frontend.ui.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Toggle;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class PaintPane extends BorderPane {

	// BackEnd
	CanvasState canvasState;

	// Canvas y relacionados
	Canvas canvas = new Canvas(800, 600);
	GraphicsContext gc = canvas.getGraphicsContext2D();
	Color lineColor = Color.BLACK, fillColor = Color.YELLOW;

	// Botones Barra Izquierda
	StyledTool selectionButton = new StyledTool("Seleccionar");
	FigureStyledTool rectangleButton = new RectangleFigureStyledTool("Rectángulo");
	FigureStyledTool circleButton = new CircleFigureStyledTool("Círculo");
	FigureStyledTool squareButton = new SquareFigureStyledTool("Cuadrado");
	FigureStyledTool ellipseButton = new EllipseFigureStyledTool("Elipse");
	StyledTool deleteButton = new StyledTool("Borrar");
	StyledButtonGroup buttonsBox;

	// Dibujar una figura
	Point startPoint;

	// Seleccionar una figura
	Figure selectedFigure;

	// StatusBar
	StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;

		StyledTool[] figuresTools = { selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton };
		buttonsBox = new StyledButtonGroup();
		for (StyledTool tool : figuresTools) {
			buttonsBox.addButton(tool);
		}

		gc.setLineWidth(1);

		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());
		});
		canvas.setOnMouseReleased(this::onMouseReleased);

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
				selectedFigure.move(diffX, diffY);
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

	private void onMouseReleased(javafx.scene.input.MouseEvent event) {
		Point endPoint = new Point(event.getX(), event.getY());
		if (startPoint == null) {
			return;
		}
		if (endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
			return;
		}
		Toggle selected = buttonsBox.getSelected();
		if (selected != selectionButton && selected != deleteButton) {
			canvasState.addFigure(((FigureStyledTool)selected).createFigure(startPoint, endPoint));
		}
		startPoint = null;
		redrawCanvas();
	}

	private void redrawCanvas() {
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
				gc.strokeOval(circle.getStartPoint().getX(), circle.getStartPoint().getY(), circle.getWidth(), circle.getHeight());
				gc.fillOval(circle.getStartPoint().getX(), circle.getStartPoint().getY(), circle.getWidth(), circle.getHeight());
			} else if (figure instanceof Ellipse) {
				Ellipse ellipse = (Ellipse) figure;
				gc.strokeOval(ellipse.getStartPoint().getX(), ellipse.getStartPoint().getY(), ellipse.getWidth(), ellipse.getHeight());
				gc.fillOval(ellipse.getStartPoint().getX(), ellipse.getStartPoint().getY(), ellipse.getWidth(), ellipse.getHeight());
			}
		}
	}


	/*
****************IDEA********************
	figureBelongs MÉTODO DE FIGURE
****************IDEA********************
	 */
	private boolean figureBelongs(Figure figure, Point eventPoint) {
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
