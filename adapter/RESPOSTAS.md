# Adapter — Sistema de Pagamentos

## Estrutura da solução

- `PaymentProcessor.java` — abstração usada pelo sistema, com `pay(double amount)`.
- `CreditCardPayment.java` — implementação já existente, agora implementando `PaymentProcessor`.
- `ExternalPaymentGateway.java` — serviço externo, mantido sem nenhuma alteração, com `makePayment(String currency, double value)`.
- `ExternalPaymentGatewayAdapter.java` — o Adapter: implementa `PaymentProcessor` e, por dentro, chama `ExternalPaymentGateway.makePayment(...)`. Recebe a moeda no construtor, então cada instância já sabe em qual moeda vai cobrar.
- `Currency.java` — enum com `BRL`, `USD`, `EUR`, usado para não espalhar strings de moeda pelo código.
- `OrderService.java` — depende só de `PaymentProcessor` (recebido por injeção no construtor), não conhece `CreditCardPayment`, `ExternalPaymentGateway` nem moeda nenhuma.
- `Main.java` — demonstra `checkout` usando cartão de crédito e usando o Adapter nas três moedas.

## Questões para reflexão

**a) Qual problema existente na integração com o serviço externo foi resolvido com o Adapter?**
A incompatibilidade de interfaces: o `OrderService` sabe chamar `pay(amount)`, mas o serviço externo só entende `makePayment(currency, value)`. O Adapter faz essa tradução, então o resto do sistema não precisa mudar para se encaixar na API externa.

**b) Qual classe representa o Adapter na sua implementação?**
`ExternalPaymentGatewayAdapter`.

**c) Qual é a diferença entre a interface esperada pelo sistema e a interface fornecida pelo serviço externo?**
O sistema espera um método simples, `pay(double amount)`, sem se preocupar com moeda. O serviço externo exige `makePayment(String currency, double value)`, um método com uma assinatura diferente e um parâmetro a mais (a moeda) que o sistema original nem tinha.

**d) Por que não é recomendado alterar diretamente a classe ExternalPaymentGateway?**
Ela pertence a outra equipe/sistema externo: alterá-la exigiria coordenar mudanças fora do nosso controle, poderia quebrar outros consumidores dessa classe e cria acoplamento direto entre o nosso domínio e os detalhes de implementação de um serviço de terceiros. O Adapter isola essa dependência numa única classe.

**e) Em quais situações o padrão Adapter é mais indicado do que modificar todas as classes clientes?**
Quando a classe/API que precisa ser integrada não pode ou não deve ser alterada (código de terceiros, biblioteca externa, outra equipe), quando há vários pontos no sistema que dependeriam dessa integração (mudar todos aumentaria o acoplamento e o risco de erro) e quando se quer poder trocar a implementação externa no futuro sem impactar quem consome a abstração — como fica fácil trocar de gateway de pagamento aqui, bastando criar outro Adapter.
