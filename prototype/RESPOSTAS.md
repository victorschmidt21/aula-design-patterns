# Prototype — Sistema de Personagens de Jogo

## Estrutura da solução

- `Prototype.java` — contrato genérico que declara a capacidade de clonagem (`T clone()`).
- `Character.java` — implementa `Prototype<Character>`; guarda nome, tipo, atributos de combate, arma e uma lista de habilidades. O `clone()` usa um construtor de cópia privado que também copia a lista de habilidades para uma nova `ArrayList` (cópia profunda da coleção).
- `CharacterPrototypeRegistry.java` — mantém os protótipos de `Warrior`, `Mage` e `Archer` num `Map` e devolve sempre um `clone()` do protótipo pedido, nunca a instância original.
- `Main.java` — demonstra a criação de vários personagens a partir dos protótipos, alterando nome, arma e habilidades em cada clone sem afetar o protótipo guardado no registro.

## Respostas

**a) Qual problema existente na criação dos personagens foi resolvido com o Prototype?**
A repetição de código ao instanciar personagens semelhantes chamando o construtor manualmente com todos os atributos. Antes, criar um novo "Warrior" exigia redigitar `100, 30, 20, "Sword"` toda vez; agora basta clonar o protótipo `WARRIOR` e ajustar só o que muda (nome, arma, habilidades). Isso também centraliza os atributos-padrão de cada tipo: se o "Warrior" padrão mudar, ajusta-se só o protótipo no registro, não cada `new Character(...)` espalhado pelo código.

**b) Qual é a principal vantagem de criar objetos a partir de protótipos?**
Criar uma cópia de um objeto já configurado é mais simples, rápido e menos propenso a erro do que reconstruir o objeto do zero informando todos os parâmetros novamente — principalmente quando a maioria dos atributos é compartilhada entre instâncias. Também desacopla o código cliente do construtor concreto da classe: quem cria o personagem não precisa saber quais são todos os parâmetros do construtor, só precisa pedir um clone do protótipo certo.

**c) Onde está o Prototype na sua implementação?**
- A interface `Prototype<T>` é o contrato do padrão (equivalente ao `Prototype` do GoF).
- `Character` é o `ConcretePrototype`, pois implementa `clone()` retornando uma cópia de si mesmo.
- `CharacterPrototypeRegistry` funciona como o registro de protótipos (uma variação comum do padrão), guardando instâncias-modelo de `Warrior`, `Mage` e `Archer` e entregando clones sob demanda.

**d) Qual a diferença entre cópia rasa e cópia profunda?**
- **Cópia rasa (shallow copy):** copia os campos primitivos/imutáveis normalmente, mas para campos que são referências a objetos mutáveis (como a `List<String> skills`), copia apenas a referência. Ou seja, o clone e o protótipo original passam a apontar para a **mesma** lista — alterar as habilidades do clone (`add`/`remove`) afetaria também o protótipo.
- **Cópia profunda (deep copy):** cria novas instâncias dos objetos internos mutáveis. É o que o `Character.clone()` faz com `new ArrayList<>(original.skills)` — o clone recebe sua própria lista, independente da lista do protótipo. Por isso, `mage.addSkill("Ice Storm")` no `Main.java` não altera o protótipo `MAGE` guardado no registro (comprovado no console: um novo clone pedido depois ainda tem só as habilidades originais).

**e) Em quais situações o padrão Prototype é mais indicado do que criar objetos diretamente com `new`?**
- Quando muitos objetos compartilham a maior parte da configuração e só variam em poucos atributos (como os personagens deste desafio).
- Quando a construção do objeto é custosa (cálculos, leitura de arquivo/configuração, chamadas externas) e é mais barato clonar um objeto já pronto do que reconstruí-lo do zero.
- Quando o código cliente não deve depender da classe concreta nem do seu construtor (por exemplo, ao trabalhar só com a abstração `Prototype`/`Character` vindos de um registro, sem saber a classe exata ou os parâmetros exigidos).
- Quando se quer preservar "templates"/predefinições de objetos (como os protótipos de `Warrior`, `Mage`, `Archer`) que servem de base para variações configuradas em tempo de execução.
