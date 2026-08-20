public class FitnessComboFactory implements ComboFactory {

    public Coffee createCoffee() {
        return new FitnessCoffee();
    }

    public Dessert createDessert() {
        return new FitnessDessert();
    }
}
