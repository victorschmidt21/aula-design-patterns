public class OrderService {

    private final ComboFactory factory;

    public OrderService(ComboFactory factory) {
        this.factory = factory;
    }

    public void createCombo() {
        Coffee coffee = factory.createCoffee();
        Dessert dessert = factory.createDessert();

        coffee.prepare();
        dessert.prepare();
    }
}
