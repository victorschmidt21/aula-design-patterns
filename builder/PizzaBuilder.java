public class PizzaBuilder {
    String size = "Media";
    String dough = "Tradicional";
    String sauce = "Tomate";
    boolean cheese;
    boolean pepperoni;
    boolean bacon;
    boolean chicken;
    boolean corn;
    boolean onion;
    boolean tomato;
    boolean olive;
    boolean stuffedCrust;
    boolean chocolate;
    boolean strawberry;
    boolean condensedMilk;

    public PizzaBuilder size(String size) {
        this.size = size;
        return this;
    }

    public PizzaBuilder dough(String dough) {
        this.dough = dough;
        return this;
    }

    public PizzaBuilder sauce(String sauce) {
        this.sauce = sauce;
        return this;
    }

    public PizzaBuilder cheese(boolean cheese) {
        this.cheese = cheese;
        return this;
    }

    public PizzaBuilder pepperoni(boolean pepperoni) {
        this.pepperoni = pepperoni;
        return this;
    }

    public PizzaBuilder bacon(boolean bacon) {
        this.bacon = bacon;
        return this;
    }

    public PizzaBuilder chicken(boolean chicken) {
        this.chicken = chicken;
        return this;
    }

    public PizzaBuilder corn(boolean corn) {
        this.corn = corn;
        return this;
    }

    public PizzaBuilder onion(boolean onion) {
        this.onion = onion;
        return this;
    }

    public PizzaBuilder tomato(boolean tomato) {
        this.tomato = tomato;
        return this;
    }

    public PizzaBuilder olive(boolean olive) {
        this.olive = olive;
        return this;
    }

    public PizzaBuilder stuffedCrust(boolean stuffedCrust) {
        this.stuffedCrust = stuffedCrust;
        return this;
    }

    // Desafio adicional: pizzas doces
    public PizzaBuilder chocolate(boolean chocolate) {
        this.chocolate = chocolate;
        return this;
    }

    public PizzaBuilder strawberry(boolean strawberry) {
        this.strawberry = strawberry;
        return this;
    }

    public PizzaBuilder condensedMilk(boolean condensedMilk) {
        this.condensedMilk = condensedMilk;
        return this;
    }

    public Pizza build() {
        return new Pizza(this);
    }
}
