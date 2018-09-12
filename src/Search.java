import java.util.*;
import java.awt.Graphics;
import java.io.IOException;
import javax.swing.Timer;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Search {

    TreeSet<State> beenthere;
    PriorityQueue<State> frontier;

    public State UniformCostSearch(final State startState, final State goalState) {

        final StateComparator frontier_comp = new StateComparator();
        frontier = new PriorityQueue<State>(frontier_comp);
        final StateComparator_beenthere beenthere_comp = new StateComparator_beenthere();
        beenthere = new TreeSet<State>(beenthere_comp);

        beenthere.add(startState);
        frontier.add(startState);

        while (frontier.size() > 0) {
            State currentState = frontier.poll();
            if (currentState.isEqual(goalState)) {
                return currentState;
            }

            ArrayList<State> newStates = currentState.everyAction();

            for (State childState : newStates) {
                if (beenthere.contains(childState)) {
                    State childState2 = beenthere.floor(childState);
                    if (childState.getCost() < childState2.getCost()) {
                        childState2.setCost(childState.getCost());
                        childState2.setParentState(currentState);
                    }

                } else {
                    State newChild = new State(childState.getCost(), currentState, childState.getX(),
                            childState.getY());
                    frontier.add(newChild);
                    beenthere.add(newChild);
                }
            }
        }

        throw new RuntimeException("There is no path to the destination");
    }

    public State AStarSearch(final State startState, final State goalState) {

        final StateComparator frontier_comp = new StateComparator();
        final StateComparator_beenthere beenthere_comp = new StateComparator_beenthere();
        frontier = new PriorityQueue<State>(frontier_comp);
        beenthere = new TreeSet<State>(beenthere_comp);

        frontier.add(startState);
        beenthere.add(startState);

        while (frontier.size() > 0) {
            State currentState = frontier.poll();
            if (currentState.isEqual(goalState)) {
                return currentState;
            }

            ArrayList<State> newStates = currentState.everyAction();

            for (State childState : newStates) {
                if (beenthere.contains(childState)) {
                    State childState2 = beenthere.floor(childState);
                    if (childState.getCost() < childState2.getCost()) {
                        childState2.setCost(childState.getCost());
                        childState2.setParentState(currentState);
                    }

                } else {
                    State newChild = new State(childState.getCost(), currentState, childState.getX(),
                            childState.getY());
                    newChild.setHeuristic(newChild.findHeuristic(goalState.getX(), goalState.getY()));
                    frontier.add(newChild);
                    beenthere.add(newChild);
                }
            }
        }

        throw new RuntimeException("There is no path to the destination");
    }

    public PriorityQueue<State> getFrontier() {
        return this.frontier;
    }

}