public class Pizza {
    private final String size;
    private final String dough;
    private final String sauce;
    private final boolean cheese;
    private final boolean pepperoni;
    private final boolean bacon;
    private final boolean chicken;
    private final boolean corn;
    private final boolean onion;
    private final boolean tomato;
    private final boolean olive;
    private final boolean stuffedCrust;
    private final boolean chocolate;
    private final boolean strawberry;
    private final boolean condensedMilk;

    Pizza(PizzaBuilder builder) {
        this.size = builder.size;
        this.dough = builder.dough;
        this.sauce = builder.sauce;
        this.cheese = builder.cheese;
        this.pepperoni = builder.pepperoni;
        this.bacon = builder.bacon;
        this.chicken = builder.chicken;
        this.corn = builder.corn;
        this.onion = builder.onion;
        this.tomato = builder.tomato;
        this.olive = builder.olive;
        this.stuffedCrust = builder.stuffedCrust;
        this.chocolate = builder.chocolate;
        this.strawberry = builder.strawberry;
        this.condensedMilk = builder.condensedMilk;
    }

    @Override
    public String toString() {
        StringBuilder ingredients = new StringBuilder();
        if (cheese) ingredients.append("queijo, ");
        if (pepperoni) ingredients.append("pepperoni, ");
        if (bacon) ingredients.append("bacon, ");
        if (chicken) ingredients.append("frango, ");
        if (corn) ingredients.append("milho, ");
        if (onion) ingredients.append("cebola, ");
        if (tomato) ingredients.append("tomate, ");
        if (olive) ingredients.append("azeitona, ");
        if (chocolate) ingredients.append("chocolate, ");
        if (strawberry) ingredients.append("morango, ");
        if (condensedMilk) ingredients.append("leite condensado, ");
        if (ingredients.length() > 0) {
            ingredients.setLength(ingredients.length() - 2);
        }

        return "Pizza " + size + " (massa " + dough + ", molho " + sauce + ")"
                + (stuffedCrust ? " com borda recheada" : "")
                + " - Ingredientes: " + ingredients;
    }
}
