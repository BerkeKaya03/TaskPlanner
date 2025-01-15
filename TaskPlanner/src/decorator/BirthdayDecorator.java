package decorator;

public class BirthdayDecorator extends MessageDecorator {
    private final String birthday;

    public BirthdayDecorator(Message message, String birthday) {
        super(message);
        this.birthday = birthday;
    }

    @Override
    public String getMessage() {
        String basicMessage = super.getMessage();
        if (birthday.equals("today")) {
            return basicMessage + " | Happy Birthday!";
        } else {
            return basicMessage + " | Have a nice day!";
        }
    }
}

