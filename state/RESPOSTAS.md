# State — Sistema de Acompanhamento de Pedidos

## Estrutura da solução

- `OrderState.java` — a abstração de estado, com `pay(Order)`, `ship(Order)`, `cancel(Order)`, `deliver(Order)` (do desafio adicional) e `getName()`.
- `PendingState.java` (`PENDING`) — pagar vai para `PaidState`; cancelar vai para `CancelledState`; enviar e entregar são recusados.
- `PaidState.java` (`PAID`) — enviar vai para `ShippedState`; cancelar vai para `CancelledState`; pagar novamente e entregar são recusados.
- `ShippedState.java` (`SHIPPED`) — entregar vai para `DeliveredState`; pagar, enviar novamente e cancelar são recusados.
- `CancelledState.java` (`CANCELLED`) — recusa todas as operações.
- `DeliveredState.java` (`DELIVERED`) — o estado do desafio adicional: recusa todas as operações, inclusive uma nova entrega.
- `Order.java` — o contexto: começa em `PendingState`, delega `pay()`, `ship()`, `cancel()` e `deliver()` ao estado atual passando `this`, oferece `changeState()` (que rejeita `null`) para os estados realizarem as transições e implementa `getStatus()` delegando a `getName()`. Não há nenhuma condicional por estado.
- `Main.java` — demonstra os cenários pedidos em pedidos distintos, exibindo o estado após cada ação. O cliente nunca chama `changeState()` diretamente.

Saída real do `java -cp out Main`:

```
--- Order 1: ship before paying, pay, ship and try to cancel ---
Status: PENDING
Shipping not allowed: order is not paid yet
Status: PENDING
Payment confirmed
Status: PAID
Order shipped
Status: SHIPPED
Cancellation not allowed: order is already shipped
Status: SHIPPED

--- Order 2: cancel a pending order, then try to pay ---
Status: PENDING
Order cancelled
Status: CANCELLED
Payment not allowed: order is cancelled
Status: CANCELLED

--- Order 3: cancel a paid order ---
Payment confirmed
Status: PAID
Order cancelled
Status: CANCELLED

--- Order 4: deliver before shipping, full flow, then try to cancel ---
Payment confirmed
Status: PAID
Delivery not allowed: order has not been shipped
Status: PAID
Order shipped
Status: SHIPPED
Order delivered
Status: DELIVERED
Cancellation not allowed: order is already delivered
Status: DELIVERED
Delivery not allowed: order is already delivered
Status: DELIVERED
```

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o State?**
A classe `Order` guardava o estado como texto e cada operação repetia condicionais sobre esse texto para decidir se podia agir e qual seria o próximo estado. As regras de todos os estados estavam misturadas em um só lugar, e incluir um estado ou mudar uma transição exigia revisar todos os métodos, com risco de liberar uma operação indevida. Agora cada estado é uma classe que sabe exatamente o que é permitido naquele momento, e `Order` apenas delega.

**b) Quais classes representam o contexto, a abstração de estado e os estados concretos na sua implementação?**
Contexto: `Order`. Abstração de estado: `OrderState`. Estados concretos: `PendingState`, `PaidState`, `ShippedState`, `CancelledState` e `DeliveredState`.

**c) Onde ficam as regras de transição e por que uma operação recusada deve preservar o estado atual?**
As transições ficam nos próprios estados concretos: é o `PendingState` que decide que `pay()` leva a `PaidState`, o `ShippedState` que decide que `deliver()` leva a `DeliveredState`, e assim por diante, sempre via `order.changeState(...)`. Uma operação recusada não pode alterar o estado porque nada aconteceu de fato: o pedido não foi pago, enviado nem cancelado. Se a recusa mudasse o estado, o sistema passaria a representar algo falso (por exemplo, um pedido "enviado" sem pagamento) e liberaria operações seguintes que não deveriam ser possíveis.

**d) Embora State e Strategy utilizem composição e delegação, como suas intenções e a responsabilidade pela troca dos objetos se diferenciam neste sistema?**
No Strategy, a intenção é escolher *qual algoritmo* executar uma mesma operação, e quem troca a estratégia é o cliente (`Main` chama `setStrategy()` na `ShippingCalculator`); as estratégias são independentes e não se conhecem. No State, a intenção é fazer o objeto mudar de comportamento conforme sua *situação interna*, e quem troca o objeto são os próprios estados, como consequência das ações (`PendingState.pay()` coloca o pedido em `PaidState`). Os estados conhecem seus sucessores e o cliente nem sabe que eles existem: ele só chama `pay()`, `ship()` etc. e observa o resultado em `getStatus()`.

**e) Quais partes da solução precisam mudar ao acrescentar um estado e ao acrescentar uma operação comum a todos os estados?**
Para acrescentar um *estado*, cria-se a nova classe que implementa `OrderState` e altera-se apenas o(s) estado(s) que levam a ele. Foi o que aconteceu com `DeliveredState`, que só é alcançado a partir de `ShippedState`; `Order` não muda. Já para acrescentar uma *operação* comum (como `deliver`), é preciso alterar o contrato `OrderState`, implementar a operação em **todos** os estados concretos (mesmo que a maioria apenas recuse) e acrescentar o método público correspondente em `Order`. Ou seja, o State facilita a inclusão de estados e torna mais custosa a inclusão de operações.
