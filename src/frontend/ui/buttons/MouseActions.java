package frontend.ui.buttons;

import backend.model.Point;

public interface MouseActions {

	default void mousePressedAction(Point point) {}
	default void mouseReleasedAction(Point point) {}
	default void mouseClickedAction(Point point, StringBuilder label) {}
	default void mouseDraggedAction(Point point) {}

}
