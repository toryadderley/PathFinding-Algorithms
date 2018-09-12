import java.util.Comparator;

//Alter name and logic

public class StateComparator implements Comparator<State> {

    public int compare(State x, State y) {

        double x_cost = x.getCost();
        double x_heuristic = x.getHeuristic();
        double y_cost = y.getCost();
        double y_heuristic = y.getHeuristic();

        if (x_cost + x_heuristic > y_cost + y_heuristic)
            return 1;
        else if (x_cost + x_heuristic < y_cost + y_heuristic)
            return -1;
        else if (x.getX() > y.getX())
            return 1;
        else if (x.getX() < y.getX())
            return -1;
        else if (x.getY() > y.getY())
            return 1;
        else if (x.getY() < y.getY())
            return -1;
        else
            return 0;
    }

}
