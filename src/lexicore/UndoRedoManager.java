package lexicore;


import java.util.Stack;

public class UndoRedoManager {

    private final Stack<TextState> undoStack;
    private final Stack<TextState> redoStack;
    private TextState currentState;

    public UndoRedoManager() {

        undoStack = new Stack<>();
        redoStack = new Stack<>();
        currentState = null;
    }

    public UndoRedoManager(TextState initialState) {

        this();

        currentState = initialState;
    }

    public void initialize(TextState initialState) {

        undoStack.clear();
        redoStack.clear();

        currentState = initialState;
    }

    /**
     * Registers a brand-new state as the result of a mutation
     * (e.g. a word replacement). The previous current state is
     * pushed onto the undo stack, and the redo stack is cleared
     * since the redo history is no longer valid after a new edit.
     */
    public void pushNewState(TextState newState) {

        if (newState == null) {
            return;
        }

        if (currentState != null) {
            undoStack.push(currentState);
        }

        currentState = newState;

        redoStack.clear();
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    public TextState undo() {

        if (!canUndo()) {
            return currentState;
        }

        redoStack.push(currentState);

        currentState = undoStack.pop();

        return currentState;
    }

    public TextState redo() {

        if (!canRedo()) {
            return currentState;
        }

        undoStack.push(currentState);

        currentState = redoStack.pop();

        return currentState;
    }

    public TextState getCurrentState() {
        return currentState;
    }

    public int getUndoDepth() {
        return undoStack.size();
    }

    public int getRedoDepth() {
        return redoStack.size();
    }
}