public class VeganComboFactory implements ComboFactory {

    public Coffee createCoffee() {
        return new VeganCoffee();
    }

    public Dessert createDessert() {
        return new VeganDessert();
    }
}
