import java.util.Comparator;

public class SortByPositionComparator implements Comparator<Formula1Driver> {

    @Override
    public int compare(Formula1Driver o1, Formula1Driver o2) {
        if(o1.getFirstPositions() < o2.getFirstPositions()) {
            return 1;
        }
        else if (o1.getFirstPositions() > o2.getFirstPositions()) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
