package frontend.ui.buttons.toolbar;

import frontend.CanvasState;
import backend.model.*;
import frontend.StatusPane;
import frontend.ui.RedrawCanvas;
import frontend.ui.buttons.toggle.FigureMouseActionToggleButton;
import frontend.ui.buttons.toggle.MouseActionToggleGroup;
import frontend.ui.buttons.toggle.SelectionMouseActionToggleButton;
import frontend.ui.render.FigureStyle;
import frontend.ui.render.OvalRender;
import frontend.ui.render.RectangleRender;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SideBar extends VBox {

	private final MouseActionToggleGroup toggleGroup = new MouseActionToggleGroup();
	private final CanvasState canvasState;
	private final StatusPane statusPane;
	private final FigureStyle currentStyle = new FigureStyle(Color.BLACK, Color.YELLOW);
	private final RedrawCanvas redrawCanvas;

	public SideBar(CanvasState canvasState, StatusPane statusPane, RedrawCanvas redrawCanvas) {
		super(10);
		setPadding(new Insets(5));
		setStyle("-fx-background-color: #999");
		setPrefWidth(110);

		this.canvasState = canvasState;
		this.statusPane = statusPane;
		this.redrawCanvas = redrawCanvas;

		setFigureButtons();
		setUtilityTools();
	}

	public MouseActionToggleGroup getToggleGroup() {
		return toggleGroup;
	}

	/**
	 * Función encargada de la configuración y ubicación de los botones de tipo toggle
	 * que cumplen funciones de selección de figuras y agregado de figuras.
	 */
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

	/**
	 * Función encargada de la configuración y ubicación de las utilidades restantes como
	 * botones de borrado y copia de formato, sliders y color pickers.
	 */
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
				canvasState.getSelected().getStyle().setBorderWidth(currentStyle.getBorderWidth());
				redrawCanvas.redraw();
			}
		});

		ColorPicker borderColorPicker = new ColorPicker(currentStyle.getBorderColor());
		ColorPicker fillColorPicker = new ColorPicker(currentStyle.getFillColor());

		borderColorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
			currentStyle.setBorderColor(borderColorPicker.getValue());
			if (canvasState.existsSelected()) {
				canvasState.addOperation(String.format("Cambiar color de borde a %s", canvasState.getSelected().getFigure().name()));
				canvasState.getSelected().getStyle().setBorderColor(currentStyle.getBorderColor());
				redrawCanvas.redraw();
			}
		});
		fillColorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
			currentStyle.setFillColor(fillColorPicker.getValue());
			if (canvasState.existsSelected()) {
				canvasState.addOperation(String.format("Cambiar color de relleno a %s", canvasState.getSelected().getFigure().name()));
				canvasState.getSelected().getStyle().setFillColor(currentStyle.getFillColor());
				redrawCanvas.redraw();
			}
		});

		Node[] utilityTools = {
				deleteButton,
				copyFormatButton,
				new Label("Borde"),
				borderWidthSlider,
				borderColorPicker,
				new Label("Relleno"),
				fillColorPicker
		};
		getChildren().addAll(utilityTools);
	}

	/**
	 * Callback encargado de la funcionalidad del botón de borrado.
	 */
	private void onActionDeleteButton(ActionEvent event) {
		if (canvasState.existsSelected()) {
			canvasState.addOperation(String.format("Borrar %s", canvasState.getSelected().getFigure().name()));
			canvasState.deleteSelected();
			redrawCanvas.redraw();
		}
	}

	/**
	 * Callback encargado de la funcionalidad del botón de copia de formato.
	 */
	private void onActionCopyFormatButton(ActionEvent event) {
		if (canvasState.existsSelected()) {
			canvasState.setStyleToCopy();
			statusPane.updateStatus("Seleccione ahora el elemento que desea aplicarle el formato copiado");
		} else {
			statusPane.updateStatus("Debe seleccionar un elemento primero");
		}
	}

	/**
	 * Encargada de establecer un estilo similar para todos los botones agregados
	 * en esta sección.
	 * @param button El botón a ser modificado su estilo.
	 */
	private void setButtonStyle(ButtonBase button) {
		button.setMinWidth(100);
		button.setCursor(Cursor.HAND);
		button.setAlignment(Pos.CENTER);
	}

}
