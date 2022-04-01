import java.io.Serializable;

public class Formula1Driver extends Driver implements Comparable<Formula1Driver>, Serializable {

    private static final long serialVersionUID = 1234L;

    private int firstPositions;
    private int secondPositions;
    private int thirdPositions;
    private int points;
    private int raceCount;

    public Formula1Driver(String name, String location, String team) {
        super(name, location, team);

        this.firstPositions = 0;
        this.secondPositions = 0;
        this.thirdPositions = 0;
        this.points = 0;
        this.raceCount = 0;
    }

    public int getFirstPositions() {
        return firstPositions;
    }

    public void setFirstPositions(int firstPositions) {
        this.firstPositions = firstPositions;
    }

    public int getSecondPositions() {
        return secondPositions;
    }

    public void setSecondPositions(int secondPositions) {
        this.secondPositions = secondPositions;
    }

    public int getThirdPositions() {
        return thirdPositions;
    }

    public void setThirdPositions(int thirdPositions) {
        this.thirdPositions = thirdPositions;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getRaceCount() {
        return raceCount;
    }

    public void setRaceCount(int raceCount) {
        this.raceCount = raceCount;
    }

    @Override
    public int compareTo(Formula1Driver formula1Driver) {

        // Sort the drivers in descending order based on the total points they earned in the season
        if(points < formula1Driver.getPoints()) {
            return 1;
        }
        else if(points > formula1Driver.getPoints()) {
            return -1;
        }
        // If the points are equal, check which driver has the most first positions
        else{
            if(firstPositions < formula1Driver.getFirstPositions()) return 1;
            else return -1;
        }
    }
}
