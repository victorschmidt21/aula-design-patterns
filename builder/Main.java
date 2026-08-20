public class Main {
    public static void main(String[] args) {
        Pizza calabresa = new PizzaBuilder()
                .size("Grande")
                .dough("Tradicional")
                .sauce("Tomate")
                .cheese(true)
                .pepperoni(true)
                .onion(true)
                .build();

        Pizza portuguesa = new PizzaBuilder()
                .size("Grande")
                .dough("Fina")
                .sauce("Tomate")
                .cheese(true)
                .bacon(true)
                .chicken(true)
                .corn(true)
                .onion(true)
                .tomato(true)
                .olive(true)
                .build();

        Pizza personalizada = new PizzaBuilder()
                .size("Media")
                .dough("Integral")
                .sauce("Barbecue")
                .cheese(true)
                .bacon(true)
                .chicken(true)
                .stuffedCrust(true)
                .build();

        Pizza doce = new PizzaBuilder()
                .size("Pequena")
                .dough("Tradicional")
                .sauce("")
                .chocolate(true)
                .strawberry(true)
                .condensedMilk(true)
                .build();

        System.out.println(calabresa);
        System.out.println(portuguesa);
        System.out.println(personalizada);
        System.out.println(doce);
    }
}
