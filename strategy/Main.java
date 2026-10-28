public class Main {

    public static void main(String[] args) {

        double weight = 3.0;
        ShippingCalculator calculator = new ShippingCalculator(new StandardShipping());

        System.out.printf("Standard: R$ %.2f%n", calculator.calculate(weight));

        calculator.setStrategy(new ExpressShipping());
        System.out.printf("Express: R$ %.2f%n", calculator.calculate(weight));

        calculator.setStrategy(new StorePickup());
        System.out.printf("Pickup: R$ %.2f%n", calculator.calculate(weight));

        System.out.println();
        System.out.println("--- New delivery type, no changes to the calculator ---");
        calculator.setStrategy(new EconomyShipping());
        System.out.printf("Economy: R$ %.2f%n", calculator.calculate(weight));

        System.out.println();
        System.out.println("--- Null strategy is rejected ---");
        try {
            calculator.setStrategy(null);
        } catch (NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.printf("Calculator keeps the previous strategy: R$ %.2f%n", calculator.calculate(weight));
    }
}
