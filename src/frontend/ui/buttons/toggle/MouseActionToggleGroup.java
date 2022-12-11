package frontend.ui.buttons.toggle;

import javafx.scene.control.ToggleGroup;

/**
 * Simple clase wrapper encargada para devolver el botón seleccionado casteado al tipo de
 * botón correcto.
 */
public class MouseActionToggleGroup extends ToggleGroup {

	public MouseActionToggleButton getSelected() {
		MouseActionToggleButton selected = (MouseActionToggleButton) getSelectedToggle();
		if (selected == null) {
			return new MouseActionToggleButton("DummyToggleButton", null);
		}
		return selected;
	}

}
