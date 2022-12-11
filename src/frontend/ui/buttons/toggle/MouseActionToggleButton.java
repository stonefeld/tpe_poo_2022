package frontend.ui.buttons.toggle;

import frontend.CanvasState;
import frontend.ui.buttons.MouseActions;
import javafx.scene.control.ToggleButton;

public class MouseActionToggleButton extends ToggleButton implements MouseActions {

	private final CanvasState canvasState;

	/**
	 * Todos los MouseActionToggleButton comparten el canvasState para facilitar
	 * el llamado a sus funcionalidades.
	 * @param description El mensaje que el botón mostrará en la interfaz gráfica.
	 * @param canvasState El estado del canvas.
	 */
	public MouseActionToggleButton(String description, CanvasState canvasState) {
		super(description);
		this.canvasState = canvasState;
	}

	/**
	 * Devuelve el estado del canvas. Utilizado por las clases hijas de esta misma.
	 * @return El estado del canvas.
	 */
	public CanvasState getCanvasState() {
		return canvasState;
	}
}
