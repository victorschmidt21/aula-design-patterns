# Decorator — Sistema de Pedidos de Bebidas

## Estrutura da solução

- `Beverage.java` — a abstração comum, com `getDescription()` e `getCost()`.
- `Coffee.java` / `Tea.java` — as bebidas básicas, com preço e descrição fixos.
- `BeverageDecorator.java` — o decorador base: implementa `Beverage` e guarda uma referência a outro `Beverage` (o objeto decorado), recebido pelo construtor.
- `MilkDecorator.java`, `ChocolateDecorator.java`, `WhippedCreamDecorator.java` — decoradores concretos, cada um delega ao `beverage` envolvido e soma sua própria descrição/custo.
- `CaramelDecorator.java` — novo adicional, criado sem alterar nenhuma classe existente.
- `Main.java` — demonstra uma bebida sem adicionais, café com leite e chocolate (R$ 8,50), chá com leite (R$ 5,50), uma bebida com o mesmo adicional duas vezes e uma bebida combinando vários adicionais incluindo o caramelo.

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Decorator?**
A explosão de classes: cada combinação de bebida e adicionais (`CoffeeWithMilk`, `CoffeeWithMilkAndChocolate` etc.) precisava de uma subclasse própria, com preço e descrição duplicados. O Decorator permite compor os adicionais em tempo de execução, sem criar uma classe para cada combinação.

**b) Quais classes representam o componente, os componentes concretos, o decorador base e os decoradores concretos na sua implementação?**
Componente: `Beverage`. Componentes concretos: `Coffee` e `Tea`. Decorador base: `BeverageDecorator`. Decoradores concretos: `MilkDecorator`, `ChocolateDecorator`, `WhippedCreamDecorator` e `CaramelDecorator`.

**c) Por que o decorador deve implementar a mesma abstração do objeto que ele envolve?**
Porque assim um decorador pode envolver tanto uma bebida básica quanto outro decorador, e o resultado continua sendo um `Beverage` válido para o código cliente. É essa compatibilidade de tipo que permite empilhar vários decoradores livremente, sem o cliente precisar saber quantas camadas existem.

**d) Como a composição e a delegação permitem combinar adicionais sem criar uma subclasse para cada combinação?**
Cada decorador guarda uma referência a um `Beverage` (composição) e, ao calcular sua descrição/custo, primeiro chama o método do objeto envolvido (delegação) e depois soma sua própria contribuição. Como decoradores podem envolver outros decoradores, qualquer combinação vira apenas uma cadeia de objetos montada em tempo de execução, não uma classe nova.

**e) Como a inclusão de um novo adicional se relaciona com o princípio Open/Closed (OCP)?**
Para adicionar o caramelo bastou criar `CaramelDecorator`, sem alterar `Beverage`, `Coffee`, `Tea`, `BeverageDecorator` ou os decoradores já existentes. O sistema fica aberto para extensão (novos decoradores) e fechado para modificação (código existente permanece intocado), que é exatamente o que o OCP propõe.
