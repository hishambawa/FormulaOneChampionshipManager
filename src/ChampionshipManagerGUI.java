import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ChampionshipManagerGUI{

    Formula1ChampionshipManager formula1ChampionshipManager;

    public ChampionshipManagerGUI(Formula1ChampionshipManager championshipManager) {
        this.formula1ChampionshipManager = championshipManager;
        menu();
    }

    private void menu() {

        JFrame menuFrame = new JFrame("Formula 1 Championship Manager");

        JButton displayListButton = new JButton("Display Drivers Table");
        JButton raceButton = new JButton("Race");
        JButton randRaceButton = new JButton("Random Race");
        JButton completedRaceButton = new JButton("Completed Races");
        JButton searchButton = new JButton("Search");
        JButton exitButton = new JButton("Exit");

        displayListButton.setBounds(100, 100, 200, 100);
        raceButton.setBounds(300, 100, 200, 100);
        randRaceButton.setBounds(100, 200, 200, 100);
        completedRaceButton.setBounds(300, 200, 200, 100);
        searchButton.setBounds(100, 300, 200, 100);
        exitButton.setBounds(300, 300, 200, 100);

        displayListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.setVisible(false);
                displayDriverTable("desc", false);
            }
        });

        raceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.setVisible(false);
                race();
            }
        });

        randRaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.setVisible(false);
                raceMod();

            }
        });

        completedRaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.setVisible(false);
                displayCompletedRaces();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.setVisible(false);
                search();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menuFrame.add(displayListButton);
        menuFrame.add(raceButton);
        menuFrame.add(randRaceButton);
        menuFrame.add(completedRaceButton);
        menuFrame.add(searchButton);
        menuFrame.add(exitButton);

        menuFrame.setSize(600, 500);
        menuFrame.setLayout(null);
        menuFrame.setVisible(true);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void displayDriverTable(String order, boolean sortByPosition) {

        JFrame displayStatsFrame = new JFrame("Formula 1 Championship Manager | Driver Statistics");
        JPanel buttonPanel = new JPanel();

        JButton ascSortButton = new JButton("Ascending Sort");
        JButton descSortButton = new JButton("Descending Sort");
        JButton positionSortButton = new JButton("Position Sort");
        JButton backButton = new JButton("Back");

        JTable statsTable = null;

        try {
            String[] columns = {"Name", "Team", "Points", "First Positions", "Second Positions", "Third Positions", "Race Count"};
            Object[][] data = new Object[formula1ChampionshipManager.getDrivers().size()][7];

            // Sort the drivers in order of points
            if(!sortByPosition) {
                if(order.equals("desc")) Collections.sort(formula1ChampionshipManager.getDrivers());
                else Collections.reverse(formula1ChampionshipManager.getDrivers());
            }
            else {
                Collections.sort(formula1ChampionshipManager.getDrivers(), new SortByPositionComparator());
            }

            for(int i = 0; i < formula1ChampionshipManager.getDrivers().size(); i++) {
                data[i][0] = formula1ChampionshipManager.getDrivers().get(i).getName();
                data[i][1] = formula1ChampionshipManager.getDrivers().get(i).getTeam();
                data[i][2] = formula1ChampionshipManager.getDrivers().get(i).getPoints();
                data[i][3] = formula1ChampionshipManager.getDrivers().get(i).getFirstPositions();
                data[i][4] = formula1ChampionshipManager.getDrivers().get(i).getSecondPositions();
                data[i][5] = formula1ChampionshipManager.getDrivers().get(i).getThirdPositions();
                data[i][6] = formula1ChampionshipManager.getDrivers().get(i).getRaceCount();
            }

            statsTable = new JTable(data, columns);

        }

        catch (Exception exception) {
            handleError(displayStatsFrame);
        }

        ascSortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayStatsFrame.setVisible(false);
                displayDriverTable("asc", false);
            }
        });

        descSortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayStatsFrame.setVisible(false);
                displayDriverTable("desc", false);
            }
        });

        positionSortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayStatsFrame.setVisible(false);
                displayDriverTable("desc", true);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayStatsFrame.setVisible(false);
                menu();
            }
        });

        // https://docs.oracle.com/javase/tutorial/uiswing/layout/border.html
        displayStatsFrame.setLayout(new BorderLayout());

        if(order.equals("desc")) buttonPanel.add(ascSortButton);
        else buttonPanel.add(descSortButton);

        buttonPanel.add(positionSortButton);
        buttonPanel.add(backButton);

        displayStatsFrame.add(statsTable.getTableHeader(), BorderLayout.PAGE_START);
        displayStatsFrame.add(statsTable, BorderLayout.CENTER);
        displayStatsFrame.add(buttonPanel, BorderLayout.PAGE_END);

        displayStatsFrame.setSize(700, 400);
        displayStatsFrame.setVisible(true);
        displayStatsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void race() {

        try {
            String date = randomDate();
            ArrayList<Formula1Driver> raceDrivers = formula1ChampionshipManager.submitRace(date);

            String title = "Formula One Race Results - " + date;
            String[] columns = {"Position", "Name"};
            Object[][] data = new Object[raceDrivers.size()][2];

            for(int i = 0; i < raceDrivers.size(); i++) {
                data[i][0] = i+1;
                data[i][1] = raceDrivers.get(i).getName();
            }

            displayTable(title, columns, data);
        }

        catch (Exception e) {
            System.out.println("An error occurred while running a race");
        }

    }

    private void raceMod() {

        try {
            ArrayList<Formula1Driver> drivers = new ArrayList<>(formula1ChampionshipManager.getDrivers());

            HashMap<String, Integer> startingPositions = new HashMap<>();

            // Randomize the arraylist to get random starting positions for the drivers
            // eg: Index 0 is position 1
            Collections.shuffle(drivers);

            // Win probabilities from the last place to first
            int[] winProbabilities = {0, 2, 2, 2, 2, 2, 10, 10, 30, 40};
            int sum = 0;

            // Generate a random number between 1 and 100
            int randomNumber = (int) (Math.random() * (100 - 1)) + 1;
            int index = 0;
            int position = drivers.size();

            // Check up to which position the sum of all the probabilities are equal to the random number generated
            while(sum <= randomNumber && index < drivers.size() - 1) {
                sum += winProbabilities[index];
                index++;
            }

            for(Formula1Driver driver : drivers) {
                startingPositions.put(driver.getName(), position);
                position--;
            }

            ArrayList<String> positions = new ArrayList<>();

            // Add the driver who ended the race in first place at the start of the arraylist
            positions.add(drivers.get(index).getName());

            // Remove the driver who ended the race in first place as this drivers position is already recorded in the race
            drivers.remove(index);

            // Shuffle the arraylist again in order to randomize the positions each driver finished in.
            Collections.shuffle(drivers);

            for(Formula1Driver driver : drivers) {
                positions.add(driver.getName());
            }

            String date = randomDate();
            Race race = new Race(date, positions);
            race.setPoints(formula1ChampionshipManager.getDrivers());

            formula1ChampionshipManager.getRaces().add(race);

            String title = "Formula One Race Results - " + date;
            String[] columns = {"Starting Position", "Name", "Finishing Position"};
            Object[][] data = new Object[positions.size()][3];

            for(int i = 0; i < data.length; i++) {
                data[i][0] = startingPositions.get(positions.get(i));
                data[i][1] = positions.get(i);
                data[i][2] = i + 1;
            }

            displayTable(title, columns, data);

        } catch (Exception exception) {
            System.out.println("An error occurred while running a race");
        }
    }

    private void displayCompletedRaces() {

        try {
            ArrayList<Race> races = formula1ChampionshipManager.getRaces();
            Collections.sort(races);

            // https://docs.oracle.com/javase/tutorial/uiswing/components/table.html#simple
            String title = "Formula 1 Race History";
            String[] columns = {"Race", "Date"};
            Object[][] data = new Object[races.size()][2];

            for(int i = 0; i < races.size(); i++) {
                data[i][0] = "Race " + (i+1);
                data[i][1] = races.get(i).getDate();
            }

            displayTable(title, columns, data);
        }

        catch (Exception exception) {
            System.out.println("An error occurred while displaying the completed races");
        }

    }

    private void search() {

        JFrame searchFrame = new JFrame("Formula 1 | Driver Record");

        JLabel searchLabel = new JLabel("Enter the Drivers name");
        searchLabel.setBounds(125, 75, 200, 25);

        JTextField searchName = new JTextField();
        searchName.setBounds(100, 100, 200, 25);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(100, 200, 200, 50);

        JButton backButton = new JButton("Back");
        backButton.setBounds(100, 250, 200, 50);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String driverName = searchName.getText();

                // Show a warning if the driver doesn't exist
                if(!formula1ChampionshipManager.driverExists(driverName)){
                    //https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
                    JOptionPane.showMessageDialog(searchFrame, "Driver not found", "Warning", JOptionPane.WARNING_MESSAGE);
                }

                else{
                    searchFrame.setVisible(false);

                    try {
                        String title = "Formula 1 | " + driverName + "'s Race History";
                        String[] columns = {"Date", "Position"};
                        Object[][] data = new Object[formula1ChampionshipManager.getRaces().size()][2];

                        for(int i = 0; i < data.length; i++) {
                            data[i][0] = formula1ChampionshipManager.getRaces().get(i).getDate();
                            data[i][1] = formula1ChampionshipManager.getRaces().get(i).findPositionByName(driverName);
                        }

                        displayTable(title, columns, data);
                    }

                    catch (Exception exception) {
                        handleError(searchFrame);
                    }
                }

            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchFrame.setVisible(false);
                menu();
            }
        });

        searchFrame.add(searchLabel);
        searchFrame.add(searchName);
        searchFrame.add(searchButton);
        searchFrame.add(backButton);

        searchFrame.setSize(400, 400);
        searchFrame.setLayout(null);
        searchFrame.setVisible(true);
        searchFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    // Display a table with the given title, columns and data in a new frame
    private void displayTable(String title, String[] columns, Object[][] data) {
        JFrame frame = new JFrame(title);
        frame.setLayout(new BorderLayout());

        JTable table = new JTable(data, columns);
        JButton backButton = new JButton("Back");

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                menu();
            }
        });

        frame.add(table.getTableHeader(), BorderLayout.PAGE_START);
        frame.add(table, BorderLayout.CENTER);
        frame.add(backButton, BorderLayout.PAGE_END);

        frame.setSize(400, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Create a random date by adding a random number of days between 1 and 365 to the date from 2021/01/01
    private String randomDate() {
        LocalDate yearStart = LocalDate.of(2021, 1, 1);
        int randomDay = (int) (Math.random() * (365 - 1)) + 1;

        return yearStart.plusDays(randomDay).toString();
    }

    private void handleError(JFrame frame) {
        JOptionPane.showMessageDialog(frame, "An error occurred. Please try again", "Error", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }
}
