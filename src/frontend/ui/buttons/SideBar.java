package frontend.ui.buttons;

import backend.CanvasState;
import backend.model.*;
import frontend.StatusPane;
import frontend.ui.RedrawCanvas;
import frontend.ui.buttons.toggle.FigureMouseActionToggleButton;
import frontend.ui.buttons.toggle.SelectionMouseActionToggleButton;
import frontend.ui.render.FigureStyle;
import frontend.ui.render.OvalRender;
import frontend.ui.render.RectangleRender;
import frontend.ui.render.operations.Operation;
import frontend.ui.render.operations.OperationStack;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SideBar extends VBox {

	private final ToggleGroup toggleGroup = new ToggleGroup();
	private final CanvasState canvasState;
	private final StatusPane statusPane;
	private final FigureStyle currentStyle = new FigureStyle(Color.BLACK, Color.YELLOW);
	private final RedrawCanvas redrawCanvas;

	private OperationStack stack;

	public SideBar(CanvasState canvasState, RedrawCanvas redrawCanvas) {
		super(10);
		setPadding(new Insets(5));
		setStyle("-fx-background-color: #999");
		setPrefWidth(100);

		this.canvasState = canvasState;
		this.statusPane = statusPane;
		this.redrawCanvas = redrawCanvas;

		setFigureButtons();
		setUtilityTools();
	}

	public ToggleGroup getToggleGroup() {
		return toggleGroup;
	}

	private void setFigureButtons() {
		SelectionMouseActionToggleButton selectionButton = new SelectionMouseActionToggleButton("Seleccionar", canvasState);
		FigureMouseActionToggleButton<Rectangle> rectangleButton = new FigureMouseActionToggleButton<>("Rectángulo",
				currentStyle, canvasState, (p1, p2, style) -> new RectangleRender<>(style, new Rectangle(p1, p2)));
		FigureMouseActionToggleButton<Square> squareButton = new FigureMouseActionToggleButton<>("Cuadrado",
				currentStyle, canvasState, (p1, p2, style) -> new RectangleRender<>(style, new Square(p1, p2)));
		FigureMouseActionToggleButton<Ellipse> ellipseButton = new FigureMouseActionToggleButton<>("Elipse",
				currentStyle, canvasState, (p1, p2, style) -> new OvalRender<>(style, new Ellipse(p1, p2)));
		FigureMouseActionToggleButton<Circle> circleButton = new FigureMouseActionToggleButton<>("Círculo",
				currentStyle, canvasState, (p1, p2, style) -> new OvalRender<>(style, new Circle(p1, p2)));

		ToggleButton[] figureButtons = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton};
		for (ToggleButton button : figureButtons) {
			setButtonStyle(button);
			button.setToggleGroup(toggleGroup);
			getChildren().add(button);
		}
	}

	private void setUtilityTools() {
		Button deleteButton = new Button("Borrar");
		Button copyFormatButton = new Button("Cop. Form.");

		setButtonStyle(deleteButton);
		setButtonStyle(copyFormatButton);

		deleteButton.setOnAction(this::onActionDeleteButton);
		copyFormatButton.setOnAction(this::onActionCopyFormatButton);

		Slider borderWidthSlider = new Slider(1, 50, currentStyle.getBorderWidth());
		borderWidthSlider.setShowTickLabels(true);
		borderWidthSlider.setShowTickMarks(true);
		borderWidthSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			currentStyle.setBorderWidth((int) Math.floor(borderWidthSlider.getValue()));
			if (canvasState.existsSelected()) {
				canvasState.getSelectedFigure().getStyle().setBorderWidth(currentStyle.getBorderWidth());
				redrawCanvas.redraw();
			}
		});

		ColorPicker borderColorPicker = new ColorPicker(currentStyle.getBorderColor());
		ColorPicker fillColorPicker = new ColorPicker(currentStyle.getFillColor());

		borderColorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
			currentStyle.setBorderColor(borderColorPicker.getValue());
			if (canvasState.existsSelected()) {
				stack.cleanRedoStack();
				stack.pushToUndoStack(new Operation(canvasState.getRenderList(), "Cambiar el color de borde de la figura seleccionada"));
				canvasState.getSelectedFigure().getStyle().setBorderColor(currentStyle.getBorderColor());
				redrawCanvas.redraw();
			}
		});
		fillColorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
			currentStyle.setFillColor(fillColorPicker.getValue());
			if (canvasState.existsSelected()) {
				stack.cleanRedoStack();
				stack.pushToUndoStack(new Operation(canvasState.getRenderList(), "Cambiar el color de relleno de la figura seleccionada"));
				canvasState.getSelectedFigure().getStyle().setFillColor(currentStyle.getFillColor());
				redrawCanvas.redraw();
			}
		});

		Node[] utilityTools = {deleteButton, copyFormatButton, new Label("Borde"), borderWidthSlider, borderColorPicker, new Label("Relleno"), fillColorPicker};
		getChildren().addAll(utilityTools);
	}

	private void onActionDeleteButton(ActionEvent event) {
		stack.pushToUndoStack(new Operation(canvasState.getRenderList(), "Borrar Figura"));
		canvasState.deleteSelected();
		redrawCanvas.redraw();
	}




	private void onActionCopyFormatButton(ActionEvent event) {
		stack.pushToUndoStack(new Operation(canvasState.getRenderList(), "Copiar Formato"));
		if (canvasState.existsSelected()) {
			canvasState.setStyleToCopy();
			statusPane.updateStatus("Seleccione ahora el elemento que desea aplicarle el formato copiado");
		} else {
			statusPane.updateStatus("Debe seleccionar un elemento primero");
		}
	}

	private void setButtonStyle(ButtonBase button) {
		button.setMinWidth(90);
		button.setCursor(Cursor.HAND);
	}

}
