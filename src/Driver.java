import java.io.Serializable;

public abstract class Driver implements Serializable {

    private static final long serialVersionUID = 12345L;
    private String name;
    private String location;
    private final String team;

    public Driver(String name, String location, String team) {
        this.name = name;
        this.location = location;
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTeam() {
        return team;
    }

}
