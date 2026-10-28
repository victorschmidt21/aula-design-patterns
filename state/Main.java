public class Main {

    public static void main(String[] args) {

        System.out.println("--- Order 1: ship before paying, pay, ship and try to cancel ---");
        Order order1 = new Order();
        printStatus(order1);
        order1.ship();
        printStatus(order1);
        order1.pay();
        printStatus(order1);
        order1.ship();
        printStatus(order1);
        order1.cancel();
        printStatus(order1);

        System.out.println();
        System.out.println("--- Order 2: cancel a pending order, then try to pay ---");
        Order order2 = new Order();
        printStatus(order2);
        order2.cancel();
        printStatus(order2);
        order2.pay();
        printStatus(order2);

        System.out.println();
        System.out.println("--- Order 3: cancel a paid order ---");
        Order order3 = new Order();
        order3.pay();
        printStatus(order3);
        order3.cancel();
        printStatus(order3);

        System.out.println();
        System.out.println("--- Order 4: deliver before shipping, full flow, then try to cancel ---");
        Order order4 = new Order();
        order4.pay();
        printStatus(order4);
        order4.deliver();
        printStatus(order4);
        order4.ship();
        printStatus(order4);
        order4.deliver();
        printStatus(order4);
        order4.cancel();
        printStatus(order4);
        order4.deliver();
        printStatus(order4);
    }

    private static void printStatus(Order order) {
        System.out.println("Status: " + order.getStatus());
    }
}
