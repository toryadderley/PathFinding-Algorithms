import java.lang.*;
import java.util.ArrayList;

//Figure out what a normal  class should have 
public class State {

    private State parentState;
    private int x, y;
    private double cost, heuristic;
    public static Model model;
    private static double speed;
    private boolean outOfBounds;
    ArrayList<State> newChildren;

    // Constructors
    State(double cost, State parentState, float x, float y) {

        this.cost = cost;
        this.parentState = parentState;
        this.x = Math.round(x / 10) * 10;// why
        this.y = Math.round(y / 10) * 10;

    }

    State(State other) {
        this.cost = other.getCost();
        this.parentState = other.getParentState();
        this.x = Math.round(other.getX() / 10) * 10;
        this.y = Math.round(other.getY() / 10) * 10;
    }

    State() {
    }

    // initializers
    public static void setSpeed() {
        for (int x = 0; x < Model.XMAX; x += 10) {
            for (int y = 0; y < Model.YMAX; y += 10) {
                if (model.getTravelSpeed(x, y) > speed)
                    speed = model.getTravelSpeed(x, y);
            }
        }
    }

    public static void setModel(Model newModel) {
        model = newModel;
    }

    // computing methods
    public double findCost(float x, float y) {
        double newCost;
        double speed = model.getTravelSpeed(x, y);
        double xx = this.getX() - x;
        double yy = this.getY() - y;
        double distance = Math.sqrt(xx * xx + yy * yy);
        newCost = distance / speed;
        return newCost;
    }

    public double findHeuristic(float x, float y) { // Edit
        double newHeuristic;
        double xx = this.getX() - model.getDestinationX();
        double yy = this.getY() - model.getDestinationY();
        double distance = Math.sqrt(xx * xx + yy * yy);
        newHeuristic = distance / speed;
        return newHeuristic;
    }

    public boolean isOutOfBounds() {
        outOfBounds = true;
        float xbound = Model.XMAX;
        float ybound = Model.YMAX;
        if (this.getX() > xbound)
            outOfBounds = false;
        if (this.getX() < 0)
            outOfBounds = false;
        if (this.getY() > ybound)
            outOfBounds = false;
        if (this.getY() < 0)
            outOfBounds = false;
        return outOfBounds;
    }

    // Create new State Methods
    public State action(int xx, int yy) {
        int newX = this.x + xx;
        int newY = this.y + yy;
        double cost = 0;
        if (newX >= 0 && newY >= 0 && newX <= Model.XMAX && newY <= Model.YMAX)
            cost = this.findCost(newX, newY);
        State nextState = new State(this.getCost() + cost, this, newX, newY);

        return nextState;
    }

    public ArrayList<State> everyAction() {
        newChildren = new ArrayList<State>();

        // Creates possible states
        State movedeast = this.action(10, 0);
        if (movedeast.isOutOfBounds())
            newChildren.add(movedeast);

        State movedwest = this.action(-10, 0);
        if (movedwest.isOutOfBounds())
            newChildren.add(movedwest);

        State movednorth = this.action(0, -10);
        if (movednorth.isOutOfBounds())
            newChildren.add(movednorth);

        State movedsouth = this.action(0, 10);
        if (movedsouth.isOutOfBounds())
            newChildren.add(movedsouth);

        State movednortheast = this.action(10, -10);
        if (movednortheast.isOutOfBounds())
            newChildren.add(movednortheast);

        State movednorthwest = this.action(-10, -10);
        if (movednorthwest.isOutOfBounds())
            newChildren.add(movednorthwest);

        State movedsoutheast = this.action(10, 10);
        if (movedsoutheast.isOutOfBounds())
            newChildren.add(movedsoutheast);

        State movedsouthwest = this.action(-10, 10);
        if (movedsouthwest.isOutOfBounds())
            newChildren.add(movedsouthwest);

        return newChildren;

    }

    public boolean isEqual(State other) { // E D I T
        if (this.x == other.getX() && this.y == other.getY())
            return true;
        return false;
    }

    // Setter Methods
    public void setHeuristic(double heuristic) {
        this.heuristic = heuristic;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setParentState(State parentState) {
        this.parentState = parentState;
    }

    public void setX(float X) {
        this.x = Math.round(X / 10) * 10;
    }

    public void setY(float Y) {
        this.y = Math.round(Y / 10) * 10;
    }

    // Getter Methods
    public double getHeuristic() {
        return this.heuristic;
    }

    public double getCost() {
        return this.cost;
    }

    public State getParentState() {
        return this.parentState;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

}
