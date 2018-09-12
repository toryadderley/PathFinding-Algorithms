import java.util.Comparator;

//Chnage names of comparator classes 
public class StateComparator_beenthere implements Comparator<State> {

    public int compare(State a, State b) {
        if (a.getX() > b.getX())
            return 1;
        else if (a.getY() > b.getY())
            return 1;
        else if (a.getY() < b.getY())
            return -1;
        else if (a.getX() < b.getX())
            return -1;
        else
            return 0;
    }

}
