package frontend.ui;

import javafx.scene.Cursor;
import javafx.scene.control.ToggleButton;

public class StyledTool extends ToggleButton {

	public StyledTool(String description) {
		super(description);
		setMinWidth(90);
		setCursor(Cursor.HAND);
	}

}
