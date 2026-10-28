# Mediator — Sistema de Sala de Chat

## Estrutura da solução

- `ChatMediator.java` — a abstração do mediador, com `register(ChatUser)`, `send(String, ChatUser)` e `sendPrivate(String, ChatUser, String)` (do desafio adicional).
- `ChatRoom.java` — o mediador concreto: mantém a `List<ChatUser>` dos participantes e concentra todas as regras.
  - `register()` rejeita `null`, ignora a mesma instância já registrada e recusa outra instância com um nome já usado (comparação exata com `equals`), preservando o cadastro original.
  - `send()` recusa remetentes não registrados e, para um remetente registrado, entrega a mensagem uma vez a cada outro participante, comparando instâncias (`user != sender`) para pular o próprio remetente.
  - `sendPrivate()` valida o remetente registrado, busca o destinatário pelo nome e recusa nomes inexistentes ou o próprio remetente; só então entrega a mensagem ao destinatário.
- `ChatUser.java` — o colega: recebe nome e mediador pelo construtor (associação fixa, campos `final`), expõe `getName()`, delega `send()` e `sendPrivate()` ao mediador passando `this` e apenas exibe a mensagem em `receive()`, sem reenviar nada. Não há lista de contatos nem `addContact()`.
- `Main.java` — registra Ana, Bruno e Carla, tenta registrar Bruno novamente, envia de Ana, registra Diego, envia de Bruno, mostra a recusa de Eduardo (associado à sala, mas não registrado) e de uma segunda "Carla", e demonstra as mensagens privadas.

Saída real do `java -cp out Main`:

```
Ana joined the room
Bruno joined the room
Carla joined the room

--- Registering Bruno again ---
Registration ignored: Bruno is already registered

--- Ana sends a message ---
Bruno received from Ana: Hello, everyone!
Carla received from Ana: Hello, everyone!

--- Diego joins and Bruno sends a message ---
Diego joined the room
Ana received from Bruno: Hello, Ana!
Carla received from Bruno: Hello, Ana!
Diego received from Bruno: Hello, Ana!

--- Eduardo is associated with the room but not registered ---
Message refused: Eduardo is not registered

--- Another user named Carla tries to register ---
Registration refused: name Carla is already in use

--- Private messages ---
Carla received from Ana (private): Are you free later?
Private message refused: Fernanda is not in the room
Private message refused: Ana cannot send a private message to themselves
```

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Mediator?**
Cada `ChatUser` mantinha sua própria lista de contatos e encaminhava as mensagens diretamente aos outros usuários. Isso criava dependências de todos com todos, exigia atualizar várias listas a cada entrada e permitia configurações incompletas em que alguém ficava de fora. As regras de encaminhamento também estavam espalhadas pelos usuários. Com o `ChatRoom`, há um único ponto que conhece os participantes e decide quem recebe cada mensagem.

**b) Quais abstrações e classes representam o mediador, o mediador concreto e os colegas participantes na sua implementação?**
Mediador: `ChatMediator`. Mediador concreto: `ChatRoom`. Colegas: as instâncias de `ChatUser` (Ana, Bruno, Carla, Diego...).

**c) Como a entrada de um novo participante muda em relação ao código inicial e quais dependências deixam de existir entre os usuários?**
No código inicial, a entrada de Diego exigiria um `addContact()` em cada usuário existente e três `addContact()` no próprio Diego; esquecer qualquer um deles faria alguém deixar de receber mensagens. Agora basta um `room.register(diego)`, e a mensagem seguinte de Bruno já chega a Ana, Carla e Diego. Os usuários deixam de ter referências entre si: um `ChatUser` conhece apenas o `ChatMediator`, então a quantidade de dependências cai de N×(N−1) para N.

**d) Por que as regras de encaminhamento e de mensagens privadas devem ficar no mediador? Qual é o risco de concentrar responsabilidades demais nesse objeto?**
Porque são regras sobre a *interação* entre os participantes, e não sobre um participante isolado: quem pode enviar, quem recebe, como achar um destinatário pelo nome, se o nome é único. Mantê-las no mediador evita duplicá-las em cada usuário e permite mudar a forma de comunicação alterando um único lugar, além de impedir que os usuários precisem conhecer uns aos outros. O risco é o mediador virar um "objeto deus": cada nova regra (moderação, silenciamento, salas, histórico, permissões) se acumula nele, tornando-o grande, difícil de testar e um ponto central de falha. Para mitigar isso, pode-se dividir as responsabilidades em colaboradores menores (validação, busca de destinatários, políticas de entrega) chamados pelo mediador.

**e) Embora uma sala de chat e um sujeito observado possam distribuir mensagens a vários objetos, como Mediator e Observer diferem quanto à intenção e à organização das interações?**
O Observer é uma comunicação em um sentido: um sujeito avisa interessados sobre mudanças no *seu próprio* estado, sem saber o que eles farão com isso, e os observadores não conversam entre si nem com o sujeito. O Mediator organiza a comunicação *entre colegas* que são, ao mesmo tempo, emissores e receptores: o mediador contém regras de negócio sobre essa interação (excluir o remetente, validar registro, encaminhar privadamente) e decide quem recebe o quê. Em resumo, o Observer desacopla quem produz um evento de quem reage a ele; o Mediator desacopla os participantes entre si, centralizando a lógica de coordenação.
