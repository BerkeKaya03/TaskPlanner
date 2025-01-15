package decorator;

public class BasicMessage implements Message {
    private final String day;
    private final String date;

    public BasicMessage(String day, String date) {
        this.day = day;
        this.date = date;
    }

    @Override
    public String getMessage() {
        return "Day: " + day + ", Date: " + date;
    }
}
