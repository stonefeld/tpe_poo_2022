package frontend.ui.render.operations;

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
    public void addOperation(Operation operation){
        cleanRedoStack();
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
    public Operation redo(){
        return redoStack.pop();
    }
    public Integer undoSize(){
        return undoStack.size();
    }

    public boolean redoStackEmpty() {
        return redoStack.isEmpty();
    }
    public boolean undoStackEmpty(){
        return undoStack.isEmpty();
    }



}
