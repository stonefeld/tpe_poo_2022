package frontend.ui.render.operations;

import backend.model.Figure;
import frontend.ui.render.FigureRender;

import java.util.List;
import java.util.Stack;

public class OperationStack{

    private final Stack<Operation> redoStack = new Stack<>();
    private final Stack<Operation> undoStack = new Stack<>();

    public Operation peekUndo(){
        return undoStack.peek();
    }
    public Operation peekRedo(){
        return redoStack.peek();
    }
    public void pushToUndoStack(Operation operation){
        undoStack.push(operation);
    }
    public void cleanRedoStack(){
        redoStack.removeAllElements();
    }
    public void pushToRedoStack(Operation operation){
        redoStack.push(operation);
    }

    public Operation undo(){
        return undoStack.pop();
    }
    public Integer redoSize(){
        return redoStack.size();

    }
    public Integer undoSize(){
        return undoStack.size();
    }
    public Operation redo(){
       return redoStack.pop();
    }
    public boolean redoStackEmpty() {
        return redoStack.isEmpty();
    }
    public boolean undoStackEmpty(){
        return undoStack.isEmpty();
    }



}
