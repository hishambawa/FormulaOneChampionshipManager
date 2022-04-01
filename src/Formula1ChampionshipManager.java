import java.io.*;
import java.util.*;

public class Formula1ChampionshipManager implements ChampionshipManager {

    private final ArrayList<Formula1Driver> drivers;
    private final ArrayList<Race> races;

    public Formula1ChampionshipManager() {
        this.drivers = new ArrayList<>();
        this.races = new ArrayList<>();
    }

    public ArrayList<Formula1Driver> getDrivers() {
        return drivers;
    }

    public ArrayList<Race> getRaces() {
        return races;
    }

    @Override
    public void showMenu() {
        Scanner input = new Scanner(System.in);

        loop:while(true) {

            try {
                System.out.println("""
                Please select an option.\s
                1. Create new driver\s
                2. Delete Driver\s
                3. Change Driver\s
                4. Display Driver Statistics\s
                5. Display Driver Table\s
                6. Add Race Completion\s
                7. Save Data\s
                8. Load Data\s
                9. Open GUI\s
                0. Exit""");

                int operation = input.nextInt();

                // Clear the Scanner
                input.nextLine();

                switch(operation) {
                    case 1:
                        System.out.println("Enter the drivers name");
                        String name = input.nextLine();

                        System.out.println("Enter the drivers location");
                        String location = input.nextLine();

                        System.out.println("Enter the drivers team name");
                        String team = input.nextLine();

                        createDriver(name, location, team);
                        break;
                    case 2:
                        System.out.println("Enter the drivers name to delete");
                        String deleteDriverName = input.nextLine();

                        deleteDriver(deleteDriverName);
                        break;
                    case 3:
                        System.out.println("Enter the team name");
                        String teamName = input.nextLine();

                        System.out.println("Enter the new drivers name");
                        String newDriverName = input.nextLine();

                        System.out.println("Enter the new drivers location");
                        String newDriverLocation = input.nextLine();

                        changeDriver(teamName, newDriverName, newDriverLocation);
                        break;
                    case 4:
                        System.out.println("Enter the drivers name");
                        String findDriverName = input.nextLine().toLowerCase();

                        displayStats(findDriverName);
                        break;
                    case 5:
                        displayAll();
                        break;
                    case 6:
                        System.out.println("Enter a date in yyyy-mm-dd format");
                        String date = input.nextLine();

                        ArrayList<Formula1Driver> raceDrivers = submitRace(date);

                        System.out.println("\nRace Details - " + date);

                        // Show the results of the race.
                        for(int j = 0; j < raceDrivers.size(); j++) {
                            System.out.println((j+1) + ". " + raceDrivers.get(j).getName());
                        }

                        System.out.println();
                        break;
                    case 7:
                        save();
                        break;
                    case 8:
                        load();
                        break;
                    case 9:
                        openGUI();
                        break;
                    case 0:
                        break loop;
                    default:
                        System.out.println("Please select a valid option \n");
                        break;

                }
            }

            catch(InputMismatchException inputMismatchException) {
                System.out.println("Please select a valid option \n");

                // Clear the scanner
                input.nextLine();
            }

            catch(Exception exception) {
                System.out.println("An error occurred. Please try again");
                break;
            }
        }
    }

    @Override
    public void createDriver(String name, String location, String team) {
        try {
            if(!teamExists(team)) {
                drivers.add(new Formula1Driver(name, location, team));
                System.out.println("Successfully added " + name + "\n");
            }

            else {
                System.out.println("A team with that name exists.");
            }
        }

        catch(Exception exception) {
            System.out.println("An error occurred while adding the new driver. Please try again \n");
        }
    }

    @Override
    public void deleteDriver(String name) {
        try {

            if(driverExists(name)) {
                for(int i = 0; i < drivers.size(); i++) {
                    if(drivers.get(i).getName().equalsIgnoreCase(name)) {
                        System.out.println("Successfully deleted " + drivers.get(i).getName() + "\n");

                        drivers.remove(i);
                        break;
                    }
                }
            }

            else {
                System.out.println("Couldn't find a driver with that name \n");
            }

        }

        catch(Exception exception) {
            System.out.println("An error occurred while deleting the driver. Please try again \n");
        }
    }

    @Override
    public void changeDriver(String team, String name, String location) {
        try {

            if(teamExists(team)) {
                for (Formula1Driver driver : drivers) {
                    if (driver.getTeam().equalsIgnoreCase(team)) {

                        System.out.println("Successfully changed " + driver.getTeam() + " driver " + driver.getName() + " to "
                                + name +"\n");

                        driver.setName(name);
                        driver.setLocation(location);

                        break;
                    }
                }
            }

            else {
                System.out.println("Couldn't find a team with that name \n");
            }
        }

        catch(Exception exception) {
            System.out.println("An error occurred while changing the driver. Please try again \n");
        }
    }

    @Override
    public void displayStats(String name) {
        try {

            if(driverExists(name)) {
                for(Formula1Driver driver : drivers) {

                    if(driver.getName().toLowerCase().equalsIgnoreCase(name)) {
                        System.out.printf("%-25s %-20s %-20s %-20s %-20s %-20s %-20s %s", "Name", "Team", "Points", "First Positions", "Second Positions", "Third Positions", "Race Count", "Location");
                        System.out.println();
                        System.out.println("------------------------------------------------------------------------------------------" +
                                "-------------------------------------------------------------------------");
                        System.out.printf("%-25s %-22s %-24s %-21s %-19s %-19s %-15s %s", driver.getName(), driver.getTeam(), driver.getPoints(), driver.getFirstPositions(),
                                driver.getSecondPositions(), driver.getThirdPositions(), driver.getRaceCount(), driver.getLocation());
                        System.out.println();

                        break;
                    }
                }
            }

            else {
                System.out.println("Couldn't find a driver with that name \n");
            }

        }

        catch(Exception exception) {
            System.out.println("An error occurred while displaying the stats. Please try again \n");
        }
    }

    @Override
    public void displayAll() {
        try {
            System.out.printf("%-25s %-20s %-20s %-20s %-20s %-20s %-20s %s", "Name", "Team", "Points", "First Positions", "Second Positions", "Third Positions", "Race Count", "Location");
            System.out.println();
            System.out.println("------------------------------------------------------------------------------------------" +
                    "-------------------------------------------------------------------------");

            // Sort the drivers in descending order of their points
            Collections.sort(drivers);

            for(Formula1Driver driver : drivers) {
                System.out.printf("%-25s %-22s %-24s %-21s %-19s %-19s %-15s %s", driver.getName(), driver.getTeam(), driver.getPoints(), driver.getFirstPositions(),
                        driver.getSecondPositions(), driver.getThirdPositions(), driver.getRaceCount(), driver.getLocation());
                System.out.println();
            }

            System.out.println();
        }

        catch (Exception exception) {
            System.out.println("An error occurred while displaying the table. Please try again \n");
        }
    }

    @Override
    public ArrayList<Formula1Driver> submitRace(String date) {
        try {
            ArrayList<String> positions = new ArrayList<>();

            // Clone the arraylist in order to create a shuffled arraylist without affecting the main arraylist
            // https://www.baeldung.com/java-copy-list-to-another
            ArrayList<Formula1Driver> driversClone = new ArrayList<>(drivers);

            // Shuffling the cloned arraylist in order to randomize the positions
            // https://www.geeksforgeeks.org/shuffle-or-randomize-a-list-in-java/
            Collections.shuffle(driversClone);

            for (Formula1Driver formula1Driver : driversClone) {
                positions.add(formula1Driver.getName());
            }

            Race race = new Race(date, positions);
            race.setPoints(drivers);

            races.add(race);

            // Returning the drivers array which is arranged according to their position
            // in order to use it when displaying the race results in both the console app and the gui
            return driversClone;

        }

        catch (Exception exception) {
            System.out.println("An error occurred while completing a race. Please try again \n");
        }
        return null;
    }

    @Override
    public void save() {
        try {
            File driverFile = new File("drivers.txt");
            File raceFile = new File("races.txt");

            FileOutputStream driverFOS = new FileOutputStream(driverFile);
            FileOutputStream raceFOS = new FileOutputStream(raceFile);

            ObjectOutputStream driverOOS = new ObjectOutputStream(driverFOS);
            ObjectOutputStream raceOOS = new ObjectOutputStream(raceFOS);

            driverOOS.writeObject(drivers);
            raceOOS.writeObject(races);

            driverOOS.close();
            raceOOS.close();

            driverFOS.close();
            raceFOS.close();

            System.out.println("Successfully saved the data \n");
        }

        catch(Exception exception) {
            System.out.println("An error occurred while saving the data. Please try again \n");
        }
    }

    @Override
    public void load() {
        try {
            FileInputStream driverFIS = new FileInputStream("drivers.txt");
            FileInputStream raceFIS = new FileInputStream("races.txt");

            ObjectInputStream driverOIS = new ObjectInputStream(driverFIS);
            ObjectInputStream raceOIS = new ObjectInputStream(raceFIS);

            // Clear existing drivers and races
            drivers.clear();
            races.clear();

            ArrayList<Formula1Driver> driversData = (ArrayList<Formula1Driver>) driverOIS.readObject();
            ArrayList<Race> raceData = (ArrayList<Race>) raceOIS.readObject();

            // Fill drivers and races arraylist with the data from the arraylist saved in the files
            drivers.addAll(driversData);
            races.addAll(raceData);

            System.out.println("Data was successfully loaded to the program \n");

        }

        catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Couldn't find the files to load. Please try again \n");
        }

        catch(Exception exception) {
            System.out.println("An error occurred while loading the file. Please try again \n");
            exception.printStackTrace();
        }
    }

    @Override
    public void openGUI() {
        new ChampionshipManagerGUI(this);
    }

    public boolean driverExists(String driverName) {

        for(Formula1Driver driver : drivers) {
            if(driver.getName().equalsIgnoreCase(driverName)) return true;
        }

        return false;
    }

    public boolean teamExists(String teamName) {

        for(Formula1Driver driver : drivers) {
            if(driver.getTeam().equalsIgnoreCase(teamName)) return true;
        }

        return false;
    }
}
