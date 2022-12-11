package frontend.ui.render.operations;

import backend.model.Figure;
import frontend.ui.render.FigureRender;

import java.util.List;

public class Operation {

	private final List<FigureRender<? extends Figure>> state;
	private final String operationLabel;

	public Operation(List<FigureRender<? extends Figure>> state, String operationLabel) {
		this.state = state;
		this.operationLabel = operationLabel;
	}

	public List<FigureRender<? extends Figure>> getState() {
		return state;
	}

	@Override
	public String toString() {
		return operationLabel;
	}

}
