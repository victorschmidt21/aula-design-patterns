public class Main {

    public static void main(String[] args) {

        Notification emailAlert = new AlertNotification(new EmailSender());
        emailAlert.send("Your account was accessed from a new device.");

        Notification smsReminder = new ReminderNotification(new SmsSender());
        smsReminder.send("Your appointment starts in one hour.");

        Notification smsAlert = new AlertNotification(new SmsSender());
        smsAlert.send("Unusual login attempt detected.");

        Notification emailReminder = new ReminderNotification(new EmailSender());
        emailReminder.send("Don't forget to submit your report.");

        Notification whatsAppWarning = new WarningNotification(new WhatsAppSender());
        whatsAppWarning.send("Your subscription will expire soon.");

        Notification whatsAppAlert = new AlertNotification(new WhatsAppSender());
        whatsAppAlert.send("Payment failed for your last order.");
    }
}
