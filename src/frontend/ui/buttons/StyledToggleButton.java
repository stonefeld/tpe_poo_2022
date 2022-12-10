package frontend.ui.buttons;

import backend.model.Figure;
import backend.model.MovableSketch;
import backend.model.Point;
import backend.model.Square;
import javafx.scene.Cursor;
import javafx.scene.control.ToggleButton;

public class StyledToggleButton  extends ToggleButton {

	public StyledToggleButton(String description) {
		super(description);
		setMinWidth(90);
		setCursor(Cursor.HAND);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
