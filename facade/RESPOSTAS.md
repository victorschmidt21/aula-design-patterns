# Facade — Sistema de Home Theater

## Estrutura da solução

- `Projector.java`, `SoundSystem.java`, `StreamingPlayer.java`, `RoomLights.java` — os subsistemas, com as interfaces originais preservadas. Nenhum deles conhece a fachada.
- `ProjectionScreen.java` — a tela motorizada do desafio adicional, com `lower()` e `raise()`, seguindo o mesmo padrão dos demais equipamentos.
- `HomeTheaterFacade.java` — a fachada: recebe os cinco equipamentos pelo construtor e expõe `watchMovie(String movie)` e `endMovie()`, apenas coordenando a ordem das chamadas.
- `Main.java` — cria os equipamentos e a fachada, e executa uma sessão completa usando somente `watchMovie` e `endMovie`.

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Facade?**
O cliente precisava conhecer todos os equipamentos e a ordem exata das operações para iniciar e encerrar uma sessão. Essa sequência ficaria duplicada em cada ponto do sistema que precisasse assistir a um filme, e era fácil esquecer uma etapa ou executá-la fora de ordem. O Facade centralizou a sequência em um único lugar.

**b) Quais classes representam a fachada e os subsistemas na sua implementação?**
Fachada: `HomeTheaterFacade`. Subsistemas: `Projector`, `SoundSystem`, `StreamingPlayer`, `RoomLights` e `ProjectionScreen`.

**c) Como a fachada reduz o acoplamento do código cliente com os equipamentos?**
O cliente passa a depender de uma única classe e de duas operações de alto nível, em vez de cinco classes e das onze chamadas que compõem a sessão. Ele não precisa saber quais equipamentos existem, nem em que ordem acioná-los. Foi exatamente isso que permitiu incluir a `ProjectionScreen`: o fluxo do `Main` não mudou, só a montagem da fachada.

**d) Qual é a diferença entre coordenar operações na fachada e transferir toda a lógica dos equipamentos para ela?**
Coordenar significa que a fachada só decide *o que* chamar e *em que ordem* — cada equipamento continua dono do seu próprio comportamento. Transferir a lógica significaria a fachada reproduzir internamente o funcionamento dos equipamentos, transformando-a em uma classe gigante que acumula responsabilidades de todos eles. Nesse caso a fachada vira um God Object: qualquer mudança no comportamento de um equipamento passa a exigir alteração nela, e os equipamentos deixam de ser reutilizáveis fora dela.

**e) O padrão Facade impede o acesso direto aos subsistemas? Em quais situações esse acesso ainda pode ser necessário?**
Não impede. As classes continuam públicas e acessíveis — a fachada oferece um caminho mais simples, não uma barreira. O acesso direto continua sendo necessário em casos fora do fluxo padrão, por exemplo: ajustar o volume durante o filme, ligar só o som para ouvir música, abaixar a tela sem iniciar uma sessão, executar manutenção ou diagnóstico de um equipamento específico, ou testar um equipamento isoladamente.
