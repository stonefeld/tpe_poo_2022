package frontend.ui.buttons;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class StyledToggleButtonGroup extends VBox {

	private final ToggleGroup buttonGroup = new ToggleGroup();

	public StyledToggleButtonGroup() {
		super(10);
		setPadding(new Insets(5));
		setStyle("-fx-background-color: #999");
		setPrefWidth(100);
	}

	public void addToggleButtons(StyledToggleButton[] buttons) {
		for (StyledToggleButton button : buttons) {
			button.setToggleGroup(buttonGroup);
			getChildren().add(button);
		}
	}

	public void addAll(Node[] nodes) {
		getChildren().addAll(nodes);
	}

	public Toggle getSelected() {
		return buttonGroup.getSelectedToggle();
	}

}
