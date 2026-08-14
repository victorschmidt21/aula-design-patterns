public abstract class NotificationService {

    public abstract Notification createNotification();

    public void sendNotification(String message) {
        Notification notification = createNotification();
        notification.send(message);
    }
}
