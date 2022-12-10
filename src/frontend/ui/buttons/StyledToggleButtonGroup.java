package frontend.ui.buttons;

import frontend.ui.buttons.StyledButton;
import frontend.ui.buttons.StyledToggleButton;
import javafx.geometry.Insets;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class StyledToggleButtonGroup extends VBox {

	private final ToggleGroup buttonGroup = new ToggleGroup();

	public StyledToggleButtonGroup() {
		super(10);
		setPadding(new Insets(5));
		setStyle("-fx-background-color: #999");
		setPrefWidth(100);
	}

	public void addButton(StyledToggleButton button) {
		button.setToggleGroup(buttonGroup);
		getChildren().add(button);
	}

	public void addAll(StyledToggleButton[] buttons) {
		for (StyledToggleButton button : buttons) {
			addButton(button);
		}
	}

	public void addButton(StyledButton button) {
		getChildren().add(button);
	}

	public Toggle getSelected() {
		return buttonGroup.getSelectedToggle();
	}

}
