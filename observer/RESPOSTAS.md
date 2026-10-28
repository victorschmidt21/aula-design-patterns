# Observer — Sistema de Monitoramento Meteorológico

## Estrutura da solução

- `TemperatureObserver.java` — a abstração do observador, com `update(double temperature)`.
- `TemperatureSubject.java` — a abstração do sujeito, com `addObserver()`, `removeObserver()` e `notifyObservers()`.
- `WeatherStation.java` — o sujeito concreto: guarda a temperatura atual e uma `List<TemperatureObserver>`. Não recebe painéis pelo construtor.
  - `addObserver()` rejeita `null` (`Objects.requireNonNull`) e ignora uma instância já cadastrada, sem notificar no cadastro.
  - `removeObserver()` usa `List.remove()`, que simplesmente não faz nada para uma instância não cadastrada.
  - `setTemperature()` atualiza o estado e chama `notifyObservers()`, que chama `update()` uma vez em cada observador, mesmo que a temperatura seja igual à anterior.
- `CurrentConditionsDisplay.java` — observador que exibe toda temperatura recebida.
- `TemperatureAlert.java` — observador que só exibe o alerta a partir de 35 °C; a regra continua dentro dele.
- `AverageTemperatureDisplay.java` — o observador do desafio adicional: acumula soma e quantidade das atualizações recebidas desde a inscrição e exibe a média a cada notificação.
- `Main.java` — cadastra painel e alerta, tenta cadastrar o painel de novo, registra 28 °C e 36 °C, remove o alerta e registra 38 °C. Depois remove o alerta outra vez (sem efeito) e o painel, mostrando que a estação continua funcionando sem observadores. Por fim, demonstra o painel de média em uma nova estação.

Saída real do `java -cp out Main`:

```
--- Registering the same display again ---
Observer already registered, ignoring

--- Setting 28.0 C ---
Current temperature: 28.0 C

--- Setting 36.0 C ---
Current temperature: 36.0 C
High temperature alert: 36.0 C

--- Removing the alert and setting 38.0 C ---
Current temperature: 38.0 C

--- Removing the alert again (no effect) and the display ---
Station still running with no observers

--- New station with an average display ---
Average temperature: 20.0 C
Average temperature: 25.0 C
Average temperature: 30.0 C
```

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Observer?**
A `WeatherStation` dependia diretamente de `CurrentConditionsDisplay` e `TemperatureAlert`, recebia ambos pelo construtor e chamava métodos específicos de cada um (`showTemperature`, `checkTemperature`). Um novo painel exigia modificar a estação, e não havia como inscrever ou remover interessados durante a execução. Agora a estação conhece apenas `TemperatureObserver`, mantém uma lista dinâmica de inscritos e notifica todos da mesma forma.

**b) Quais abstrações e classes representam o sujeito, o sujeito concreto, o observador e os observadores concretos na sua implementação?**
Sujeito: `TemperatureSubject`. Sujeito concreto: `WeatherStation`. Observador: `TemperatureObserver`. Observadores concretos: `CurrentConditionsDisplay`, `TemperatureAlert` e `AverageTemperatureDisplay`.

**c) Como o cadastro e a remoção de observadores alteram os destinatários das notificações durante a execução?**
A cada `setTemperature()`, a estação percorre a lista de observadores *naquele momento*. Por isso, um `addObserver()` faz o novo interessado receber as próximas atualizações (mas não as anteriores, nem uma notificação imediata), e um `removeObserver()` faz com que ele deixe de recebê-las a partir da próxima chamada. No `Main`, o alerta recebe 28 °C e 36 °C, mas não 38 °C, porque foi removido antes; e o painel de média calcula 20, 25 e 30 considerando apenas o que recebeu depois de se inscrever.

**d) A solução envia a temperatura no método update(), utilizando a abordagem push. Como seria uma alternativa pull, em que o observador consulta o estado do sujeito?**
No modelo pull, `update()` não levaria os dados: receberia apenas uma referência ao sujeito (por exemplo, `update(WeatherStation station)`), ou o observador guardaria essa referência na inscrição. Ao ser notificado, ele chamaria `station.getTemperature()` (ou outros getters, como umidade e pressão) para buscar só o que lhe interessa. A vantagem é que o contrato de `update()` não muda quando o sujeito ganha novos dados. A desvantagem é que o observador passa a depender da interface concreta do sujeito e que o estado lido pode já ter mudado se houver concorrência.

**e) Como um observador lento ou que lança uma exceção pode afetar os demais em uma notificação síncrona? Que cuidados seriam necessários em um sistema real?**
Como `notifyObservers()` chama cada `update()` em sequência na mesma thread, um observador lento atrasa todos os que vêm depois dele e também quem chamou `setTemperature()`. Uma exceção não tratada interrompe o laço, de modo que os observadores seguintes não recebem a atualização, e ela ainda se propaga para o código que registrou a temperatura. Em um sistema real seria preciso, por exemplo: envolver cada `update()` em `try/catch` e registrar a falha sem interromper os demais; usar notificação assíncrona (filas, `ExecutorService`, mensageria) com timeouts; usar uma coleção segura para alterações durante a notificação (como `CopyOnWriteArrayList`); e garantir que observadores cancelem a inscrição para não vazar memória.
