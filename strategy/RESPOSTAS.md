# Strategy — Sistema de Cálculo de Frete

## Estrutura da solução

- `ShippingStrategy.java` — a abstração comum, com a operação `calculate(double weight)`.
- `StandardShipping.java`, `ExpressShipping.java`, `StorePickup.java` — as estratégias concretas, cada uma com sua própria fórmula (R$ 10,00 + R$ 2,00/kg, R$ 20,00 + R$ 5,00/kg e frete zero, respectivamente). Nenhuma estratégia conhece as outras nem a calculadora.
- `EconomyShipping.java` — a estratégia do desafio adicional (R$ 5,00 + R$ 1,50/kg), criada sem alterar nenhuma classe existente.
- `ShippingCalculator.java` — o contexto: recebe a estratégia pelo construtor, permite trocá-la com `setStrategy()` e delega `calculate(double weight)` à estratégia atual. Estratégias nulas são rejeitadas (`Objects.requireNonNull`) tanto no construtor quanto no `setStrategy()`, que é reutilizado pelo construtor. Não há nenhuma condicional por modalidade.
- `Main.java` — o cliente: cria uma única calculadora e troca a estratégia em tempo de execução para mostrar as quatro modalidades com um pedido de 3 kg. Também mostra que uma estratégia nula é recusada e que a calculadora mantém a estratégia anterior.

Saída real do `java -cp out Main`:

```
Standard: R$ 16,00
Express: R$ 35,00
Pickup: R$ 0,00

--- New delivery type, no changes to the calculator ---
Economy: R$ 9,50

--- Null strategy is rejected ---
Error: Shipping strategy must not be null
Calculator keeps the previous strategy: R$ 9,50
```

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Strategy?**
A `ShippingCalculator` original concentrava todas as fórmulas em uma cadeia de `if/else` por texto. Cada nova modalidade exigia alterar essa classe, as regras ficavam misturadas com a lógica de seleção e não era possível verificar um algoritmo isoladamente. Além disso, um texto inválido (como `"EXPRES"`) só era descoberto em tempo de execução. Com o Strategy, cada fórmula vive na sua própria classe, a calculadora não escolhe nada e o cliente passa um objeto tipado, de modo que uma modalidade inexistente nem compila.

**b) Quais classes representam o contexto, a estratégia e as estratégias concretas na sua implementação?**
Contexto: `ShippingCalculator`. Estratégia (abstração): `ShippingStrategy`. Estratégias concretas: `StandardShipping`, `ExpressShipping`, `StorePickup` e `EconomyShipping`.

**c) Como a composição e a delegação permitem trocar o algoritmo durante a execução?**
A calculadora não herda uma fórmula: ela *tem* uma referência a `ShippingStrategy` (composição) e, em `calculate()`, apenas repassa a chamada para esse objeto (delegação). Como a referência é um atributo, basta chamar `setStrategy()` para apontá-la para outra implementação, e a próxima chamada já usa o novo algoritmo, sem recriar a calculadora. É exatamente o que o `Main` faz ao passar de `StandardShipping` para `ExpressShipping`, `StorePickup` e `EconomyShipping` na mesma instância.

**d) Qual é a responsabilidade do cliente na seleção da estratégia e por que essa escolha não deve ficar dentro da calculadora?**
O cliente decide qual modalidade usar e instancia a estratégia correspondente, porque é ele quem conhece o contexto da decisão (a opção escolhida pelo comprador, uma configuração, uma regra de negócio). Se a escolha ficasse na calculadora, ela voltaria a conhecer todas as modalidades concretas e precisaria de condicionais por nome ou tipo, reintroduzindo o mesmo acoplamento que o padrão eliminou: cada modalidade nova obrigaria a alterar a calculadora.

**e) Como a inclusão de uma nova modalidade de entrega se relaciona com o princípio Open/Closed (OCP)?**
A inclusão de `EconomyShipping` é o OCP em prática: o sistema ficou *aberto para extensão*, pois bastou criar uma nova classe que implementa `ShippingStrategy`, e *fechado para modificação*, já que `ShippingCalculator`, `ShippingStrategy` e as estratégias existentes não foram tocadas. A única mudança fora da nova classe está no cliente (`Main`), que passa a selecionar a nova estratégia, e é justamente lá que essa decisão deve estar.
