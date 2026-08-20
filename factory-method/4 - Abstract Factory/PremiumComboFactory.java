public class PremiumComboFactory implements ComboFactory {

    public Coffee createCoffee() {
        return new PremiumCoffee();
    }

    public Dessert createDessert() {
        return new PremiumDessert();
    }
}
