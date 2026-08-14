public class WhatsAppNotificationService extends NotificationService {

    @Override
    public Notification createNotification() {
        return new WhatsAppNotification();
    }
}
