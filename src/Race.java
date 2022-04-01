import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Race implements Serializable, Comparable<Race> {

    private static final long serialVersionUID = 2123242L;
    private final String date;
    private final ArrayList<String> positions;

    public Race(String date, ArrayList<String> positions) {
        this.date = date;
        this.positions = positions;
    }

    public String getDate() {
        return date;
    }

    // Return the number of points obtained by a driver based on the position they finished the race
    private int calculatePoints(int position) {

        switch (position){
            case 1: return 25;
            case 2: return 18;
            case 3: return 15;
            case 4: return 12;
            case 5: return 10;
            case 6: return 8;
            case 7: return 6;
            case 8: return 4;
            case 9: return 2;
            case 10: return 1;
            default: return 0;
        }

    }

    // Set the points and first, second, third positions to each driver's statistics
    public void setPoints(ArrayList<Formula1Driver> drivers) {

        for(Formula1Driver driver : drivers) {
            int position = positions.indexOf(driver.getName()) + 1;
            int points = calculatePoints(position);

            driver.setPoints(driver.getPoints() + points);
            driver.setRaceCount(driver.getRaceCount() + 1);

            if(position == 1) driver.setFirstPositions(driver.getFirstPositions() + 1);
            else if (position == 2) driver.setSecondPositions(driver.getSecondPositions() + 1);
            else if (position == 3) driver.setThirdPositions(driver.getThirdPositions() + 1);
        }
    }

    public int findPositionByName(String driverName) {
        for(String name : positions) {
            if(driverName.equalsIgnoreCase(name)) {
                return positions.indexOf(name) + 1;
            }
        }

        return -1;
    }

    @Override
    public int compareTo(Race race2) {
        LocalDate race1Date = LocalDate.parse(date);
        LocalDate race2Date = LocalDate.parse(race2.getDate());
        return race1Date.compareTo(race2Date);
    }
}
