package frontend.ui;

import javafx.scene.Cursor;
import javafx.scene.control.Button;

public class StyledButton extends Button {

	public StyledButton(String description) {
		super(description);
		setMinWidth(90);
		setCursor(Cursor.HAND);
	}

}
