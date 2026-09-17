# Composite — Sistema de Arquivos e Pastas

## Estrutura da solução

- `FileSystemComponent.java` — a abstração comum, com `getName()`, `getSize()` e `display(String indent)`.
- `DocumentFile.java` — a folha: representa um arquivo individual, não tem filhos.
- `Folder.java` — o composto: guarda uma lista de `FileSystemComponent` (arquivos e/ou outras pastas), permite `add`/`remove` e calcula o tamanho somando recursivamente o de cada filho.
- `Main.java` — demonstra um arquivo isolado, uma pasta vazia e uma árvore com pelo menos dois níveis de subpastas (`root` → `documents` → `invoices`), consultando tudo pela abstração `FileSystemComponent`.

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Composite?**
A `Folder` só aceitava arquivos e usava uma operação diferente (`getTotalSize`) da usada para um arquivo isolado (`getSize`). Isso impedia pastas dentro de pastas e obrigava o código cliente a tratar arquivo e pasta de formas diferentes. O Composite unifica isso numa única abstração, permitindo hierarquia com vários níveis.

**b) Quais elementos representam o componente, a folha e o composto na sua implementação?**
Componente: `FileSystemComponent`. Folha: `DocumentFile`. Composto: `Folder`.

**c) Como o polimorfismo permite tratar arquivos e pastas de maneira uniforme?**
Tanto `DocumentFile` quanto `Folder` implementam `FileSystemComponent`, então qualquer código que trabalhe com essa interface chama `getSize()`, `getName()` ou `display()` sem saber (nem precisar verificar) se está lidando com um arquivo ou uma pasta — cada classe concreta resolve a chamada do seu próprio jeito.

**d) Qual é o papel da recursão no cálculo do tamanho das pastas?**
`Folder.getSize()` soma o `getSize()` de cada filho. Quando o filho é outra `Folder`, essa chamada dispara o mesmo cálculo de novo, e assim por diante até chegar nos arquivos (que retornam seu tamanho direto). É essa recursão que propaga o cálculo por todos os níveis da árvore sem a `Folder` precisar saber a profundidade da hierarquia.

**e) Quais são as vantagens e limitações de manter as operações de adicionar e remover filhos apenas na classe Folder?**
Vantagem: a interface `FileSystemComponent` fica mais enxuta e um `DocumentFile` não expõe operações que não fazem sentido para ele (arquivo não tem filhos), evitando erros em tempo de execução. Limitação: código que só enxerga o componente pela abstração comum não consegue adicionar/remover elementos sem primeiro saber (ou verificar) que está lidando com uma `Folder`, o que quebra um pouco a transparência total do Composite — é a troca clássica entre segurança de tipos e uniformidade da interface.
