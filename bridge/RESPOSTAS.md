# Bridge — Sistema de Notificações

## Estrutura da solução

- `NotificationSender.java` — a implementação (interface) do canal de envio, com `sendMessage(String message)`.
- `EmailSender.java` / `SmsSender.java` — canais concretos existentes.
- `WhatsAppSender.java` — novo canal, adicionado sem tocar em nenhum arquivo existente.
- `Notification.java` — a abstração: classe abstrata que guarda uma referência a `NotificationSender` e delega o envio a ele.
- `AlertNotification.java` / `ReminderNotification.java` — abstrações refinadas existentes, cada uma só define o prefixo da mensagem e usa o `sender` recebido.
- `WarningNotification.java` — novo tipo de notificação, adicionado sem tocar em nenhum arquivo existente.
- `Main.java` — demonstra várias combinações entre tipos e canais, incluindo as novas classes.

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Bridge?**
A explosão de classes: cada combinação entre tipo de notificação e canal virava uma classe própria (`EmailAlertNotification`, `SmsReminderNotification` etc.). O Bridge separou as duas variações, então elas crescem de forma independente.

**b) O que representa a abstração na sua implementação?**
A classe `Notification` e suas subclasses (`AlertNotification`, `ReminderNotification`, `WarningNotification`). Elas representam o tipo de notificação, sem saber como a mensagem é enviada de fato.

**c) O que representa a implementação na sua solução?**
A interface `NotificationSender` e suas classes concretas (`EmailSender`, `SmsSender`, `WhatsAppSender`). Elas representam o canal de envio, sem saber qual tipo de notificação está sendo enviada.

**d) Qual é a diferença entre utilizar Bridge e criar uma classe para cada combinação possível?**
Sem Bridge, cada novo tipo ou canal multiplica o número de classes (tipos × canais). Com Bridge, tipo e canal são combinados em tempo de execução via composição, então um novo tipo ou um novo canal exige só uma classe nova, não uma para cada combinação.

**e) Em quais situações o padrão Bridge é mais indicado?**
Quando existem duas (ou mais) dimensões de variação independentes numa mesma estrutura e o número de combinações cresceria demais com herança simples, ou quando se quer trocar a implementação em tempo de execução sem alterar a abstração que a utiliza.
