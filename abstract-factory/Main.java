public class Main {

    public static void main(String[] args) {
        new OrderService(new TraditionalComboFactory()).createCombo();
        new OrderService(new FitnessComboFactory()).createCombo();
        new OrderService(new PremiumComboFactory()).createCombo();
        new OrderService(new VeganComboFactory()).createCombo();
    }
}
