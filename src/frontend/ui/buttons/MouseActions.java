package frontend.ui.buttons;

import backend.model.Point;

/**
 * Interfaz encargada de modelar las acciones deseadas a escuchar de un mouse e implementadas por los
 * botones del MouseActionToggleButton. Tienen un default con una función vacía para que los botones
 * que no deberían tener acción alguna sobre un evento no realicen nada al ser llamados desde PaintPane.
 */
public interface MouseActions {

	default void mousePressedAction(Point point) {}
	default void mouseReleasedAction(Point point) {}
	default void mouseClickedAction(Point point, StringBuilder label) {}
	default void mouseDraggedAction(Point point) {}

}
