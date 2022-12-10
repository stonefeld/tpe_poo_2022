package frontend.ui.render;

import javafx.scene.paint.Color;

public class FigureStyle {

	private Color borderColor, fillColor;
	private final Color selColor = Color.RED;
	private int borderWidth = 1;

	public FigureStyle(Color borderColor, Color fillColor) {
		this.borderColor = borderColor;
		this.fillColor = fillColor;
	}

	public void setBorderColor(Color color) {
		borderColor = color;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public Color getSelColor() {
		return selColor;
	}

	public void setBorderWidth(int borderWidth) {
		this.borderWidth = borderWidth;
	}

	public int getBorderWidth() {
		return borderWidth;
	}

	public FigureStyle copy() {
		return new FigureStyle(borderColor, fillColor);
	}

}
