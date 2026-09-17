public class Main {

    public static void main(String[] args) {

        Beverage plainCoffee = new Coffee();
        System.out.println(plainCoffee.getDescription());
        System.out.println("Total: R$ " + plainCoffee.getCost());

        Beverage coffeeWithMilkAndChocolate = new ChocolateDecorator(new MilkDecorator(new Coffee()));
        System.out.println(coffeeWithMilkAndChocolate.getDescription());
        System.out.println("Total: R$ " + coffeeWithMilkAndChocolate.getCost());

        Beverage teaWithMilk = new MilkDecorator(new Tea());
        System.out.println(teaWithMilk.getDescription());
        System.out.println("Total: R$ " + teaWithMilk.getCost());

        Beverage coffeeWithDoubleChocolate = new ChocolateDecorator(new ChocolateDecorator(new Coffee()));
        System.out.println(coffeeWithDoubleChocolate.getDescription());
        System.out.println("Total: R$ " + coffeeWithDoubleChocolate.getCost());

        Beverage coffeeWithMilkChocolateAndCaramel = new CaramelDecorator(
                new ChocolateDecorator(new MilkDecorator(new Coffee())));
        System.out.println(coffeeWithMilkChocolateAndCaramel.getDescription());
        System.out.println("Total: R$ " + coffeeWithMilkChocolateAndCaramel.getCost());
    }
}
