import java.util.ArrayList;

public interface ChampionshipManager {

    void showMenu();
    void createDriver(String name, String location, String team);
    void deleteDriver(String name);
    void changeDriver(String team, String name, String location);
    void displayStats(String name);
    void displayAll();
    ArrayList<Formula1Driver> submitRace(String date);
    void save();
    void load();
    void openGUI();
}
