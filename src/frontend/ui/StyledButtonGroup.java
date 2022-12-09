package frontend.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class StyledButtonGroup extends VBox {

	private final ToggleGroup buttonGroup = new ToggleGroup();

	public StyledButtonGroup() {
		super(10);
		setPadding(new Insets(5));
		setStyle("-fx-background-color: #999");
		setPrefWidth(100);
	}

	public void addButton(StyledTool button) {
		button.setToggleGroup(buttonGroup);
		getChildren().add(button);
	}

	public Toggle getSelected() {
		return buttonGroup.getSelectedToggle();
	}

}
