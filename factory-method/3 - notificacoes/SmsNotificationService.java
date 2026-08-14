public class SmsNotificationService extends NotificationService {

    @Override
    public Notification createNotification() {
        return new SmsNotification();
    }
}
