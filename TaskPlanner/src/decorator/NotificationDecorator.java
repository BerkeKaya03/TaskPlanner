package decorator;

public class NotificationDecorator extends MessageDecorator {
    private final String notification;

    public NotificationDecorator(Message message, String notification) {
        super(message);
        this.notification = notification;
    }

    @Override
    public String getMessage() {
        String basicMessage = super.getMessage();
        return basicMessage + " | Notification: " + notification;
    }
}
