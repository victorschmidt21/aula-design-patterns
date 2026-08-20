public class TraditionalComboFactory implements ComboFactory {

    public Coffee createCoffee() {
        return new TraditionalCoffee();
    }

    public Dessert createDessert() {
        return new TraditionalDessert();
    }
}
