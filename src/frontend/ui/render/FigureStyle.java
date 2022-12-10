package frontend.ui.render;

import javafx.scene.paint.Color;

public class FigureStyle {

	private Color lineColor, fillColor;
	private final Color selColor = Color.RED;
	private int lineWidth = 1;

	public FigureStyle(Color lineColor, Color fillColor) {
		this.lineColor = lineColor;
		this.fillColor = fillColor;
	}

	public void setLineColor(Color color) {
		lineColor = color;
	}

	public Color getLineColor() {
		return lineColor;
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

	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}

	public int getLineWidth() {
		return lineWidth;
	}

	public FigureStyle copy() {
		return new FigureStyle(lineColor, fillColor);
	}

}
