package frontend.ui.buttons.toggle;

import frontend.CanvasState;
import frontend.ui.buttons.MouseActions;
import javafx.scene.control.ToggleButton;

public class MouseActionToggleButton extends ToggleButton implements MouseActions {

	private final CanvasState canvasState;

	public MouseActionToggleButton(String description, CanvasState canvasState) {
		super(description);
		this.canvasState = canvasState;
	}

	public CanvasState getCanvasState() {
		return canvasState;
	}
}
