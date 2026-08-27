# Singleton — Sistema de Configuração da Aplicação

## Estrutura da solução

- `AppConfig.java` — implementa o Singleton: construtor `private`, instância guardada num campo `static` e único ponto de acesso pelo método `AppConfig.getInstance()`.
- `UserService.java` / `ReportService.java` — não criam mais `new AppConfig()`; ambos pedem a instância compartilhada via `AppConfig.getInstance()`.
- `Main.java` — mostra que `UserService`, `ReportService` e chamadas diretas a `getInstance()` recebem sempre o mesmo objeto (`==`), e depois dispara 20 threads simultâneas chamando `getInstance()` para provar que o Singleton continua consistente sob concorrência.

## Por que a versão simples (lazy, sem sincronização) não é segura em múltiplas threads

Uma implementação "ingênua" seria:

```java
public static AppConfig getInstance() {
    if (instance == null) {
        instance = new AppConfig();
    }
    return instance;
}
```

O problema: `if (instance == null)` e `instance = new AppConfig()` não são uma operação atômica. Se duas threads chamarem `getInstance()` ao mesmo tempo, ambas podem ler `instance == null` **antes** de qualquer uma delas terminar de atribuir o valor. Resultado: as duas criam um `new AppConfig()`, cada uma fica com sua própria referência, e a "garantia" de instância única é quebrada silenciosamente — sem lançar nenhum erro, o sistema passa a ter dois `AppConfig` diferentes convivendo, o que é exatamente o problema que o Singleton deveria evitar (estados divergentes entre partes do sistema).

Há ainda um segundo problema, mais sutil, mesmo sincronizando com `synchronized` no corpo inteiro do método: sem a palavra-chave `volatile` no campo `instance`, o compilador/JIT ou o processador podem reordenar as instruções de `new AppConfig()` (alocar memória, rodar o construtor, atribuir a referência ao campo `instance`). Uma outra thread poderia enxergar `instance` já não-nulo mas apontando para um objeto ainda **parcialmente construído**, obtendo valores inconsistentes de `databaseUrl`/`environment`.

## Como a implementação resolve isso

`AppConfig` usa **double-checked locking** com `volatile`:

```java
private static volatile AppConfig instance;

public static AppConfig getInstance() {
    if (instance == null) {                 // 1ª checagem, sem lock (rápido no caminho comum)
        synchronized (AppConfig.class) {     // só uma thread por vez entra aqui
            if (instance == null) {          // 2ª checagem, agora dentro do lock
                instance = new AppConfig();
            }
        }
    }
    return instance;
}
```

- O `synchronized` garante que, se duas threads chegarem juntas na primeira checagem e virem `instance == null`, apenas uma de cada vez entra no bloco crítico.
- A segunda checagem (`if (instance == null)` de novo, já dentro do `synchronized`) evita que a segunda thread, ao finalmente conseguir o lock, crie uma nova instância por cima da que a primeira thread acabou de criar.
- O `volatile` garante *happens-before*: quando uma thread lê `instance` e o vê não-nulo, ela também enxerga todos os campos do objeto já totalmente inicializados — elimina o risco de pegar uma instância "pela metade".
- A primeira checagem sem lock existe por desempenho: depois que a instância já existe (praticamente todas as chamadas, na prática), `getInstance()` não paga o custo de sincronização a cada chamada, só compara um ponteiro.

O teste em `Main.demonstrateConcurrentAccess()` cria 20 threads que chamam `getInstance()` simultaneamente e confirma que todas recebem a mesma referência (`System.identityHashCode` idêntico), validando que a proteção funciona.
