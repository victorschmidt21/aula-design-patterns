# Flyweight — Sistema de Renderização de Florestas

## Estrutura da solução

- `TreeType.java` — o flyweight: estado intrínseco imutável (`name`, `color`, `texture`), campos privados e finais, sem setters, com cópia defensiva da textura no construtor. Expõe `draw(int x, int y, double scale)`, que recebe o estado extrínseco e simula a renderização.
- `Tree.java` — guarda apenas as coordenadas, a escala (estado extrínseco do desafio adicional) e uma referência a `TreeType`. `draw()` só delega ao tipo, passando a posição e a escala.
- `TreeTypeFactory.java` — mantém o cache de tipos (`Map<TreeTypeKey, TreeType>`) e expõe `getTreeType(String name, String color, String textureId)`. A chave é uma classe privada (`TreeTypeKey`) com `equals`/`hashCode` por valor, evitando a ambiguidade de concatenar os três campos em uma única `String` (por exemplo, `"Oak" + "GreenBark01"` colidiria com `"OakGreen" + "Bark01"`). A textura só é "carregada" (array de 100.000 bytes) quando um tipo precisa ser criado; o carregamento é logado no console para evidenciar quando isso ocorre.
- `Main.java` — cria 200 árvores (100 Oak + 100 Pine) em posições variadas, alternando a escala entre `0.5` e `1.5` a cada árvore, usando a mesma instância de `TreeTypeFactory`. Ao final, imprime a quantidade de árvores e de tipos criados, e usa `==` para comprovar que requisições equivalentes reaproveitam o mesmo `TreeType` e que um `textureId` diferente produz uma instância distinta.

Executando `Main`, a mensagem `"Loading texture: ..."` aparece apenas duas vezes durante a criação das 200 árvores (uma para `oak-bark-01`, outra para `pine-bark-01`), confirmando que o array de textura não é duplicado:

```
Loading texture: oak-bark-01
Loading texture: pine-bark-01
... (200 linhas de draw, com posições e escalas variadas)
Trees created: 200
Tree types created: 2
Loading texture: oak-bark-02
Oak type reused across equivalent requests: true
Pine type reused across equivalent requests: true
Different textureId produces a distinct type: false
```

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Flyweight?**
Cada `Tree` armazenava sua própria cópia de `name`, `color` e, principalmente, do array de `texture` (100.000 bytes), mesmo quando várias árvores eram do mesmo tipo. Em um cenário com milhares de árvores, esses dados idênticos seriam duplicados milhares de vezes. O Flyweight extraiu esse estado para `TreeType` e passou a compartilhar uma única instância entre todas as árvores do mesmo tipo, de modo que a textura existe uma vez por tipo, não uma vez por árvore.

**b) Quais dados representam o estado intrínseco e o estado extrínseco na sua implementação?**
Intrínseco (compartilhado, dentro de `TreeType`): `name`, `color` e `texture`. Extrínseco (específico de cada árvore, dentro de `Tree`): as coordenadas `x`/`y` e a `scale`. O extrínseco é informado ao flyweight no momento do uso, como parâmetros de `draw(x, y, scale)`, em vez de ser armazenado no tipo compartilhado.

**c) Qual é o papel da fábrica e da chave do cache no compartilhamento dos objetos?**
A fábrica (`TreeTypeFactory`) é o único ponto de criação de `TreeType`: antes de instanciar um novo tipo, ela verifica no cache se já existe um com a mesma combinação de `name`, `color` e `textureId`. A chave do cache é o que define "mesma combinação" — por isso precisa comparar os três campos por valor, sem ambiguidade. Sem uma fábrica controlando a criação, nada impediria que o código cliente instanciasse `TreeType` diretamente e duplicasse o estado que deveria ser compartilhado.

**d) Por que o estado compartilhado deve ser imutável? O que poderia acontecer se a textura de um tipo fosse alterada?**
Como a mesma instância de `TreeType` é referenciada por todas as árvores daquele tipo, qualquer alteração nela afetaria simultaneamente todas essas árvores — mesmo as que não deveriam ser modificadas. Se a textura de um tipo fosse trocada (ou o array interno exposto e alterado de fora), todas as árvores desse tipo no cenário mudariam de aparência ao mesmo tempo, um efeito colateral inesperado e difícil de rastrear. A imutabilidade (campos finais, sem setters, sem expor o array original) é o que torna o compartilhamento seguro.

**e) Em quais situações o Flyweight pode reduzir significativamente o consumo de memória e quais custos de complexidade ele acrescenta?**
Ele compensa quando existe um número grande de objetos e uma parte substancial do seu estado se repete entre eles, como texturas, fontes, ícones ou estilos reaproveitados por muitas instâncias — exatamente o caso de milhares de árvores com poucos tipos visuais. Os custos: o design fica menos direto, pois é preciso separar deliberadamente estado intrínseco e extrínseco e introduzir uma fábrica com cache; o estado extrínseco passa a ser gerenciado (e recalculado ou armazenado) por quem usa o flyweight, não pelo objeto em si; e o cache de tipos consome memória e exige cuidado de concorrência caso a fábrica seja compartilhada entre threads.
