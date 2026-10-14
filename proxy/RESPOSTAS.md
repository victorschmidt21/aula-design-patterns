# Proxy — Sistema de Galeria de Imagens

## Estrutura da solução

- `Image.java` — a abstração comum, com a operação `display()`.
- `HighResolutionImage.java` — o sujeito real: carrega o arquivo no construtor (`loadFromDisk()`) e exibe em `display()`, sem mudanças de comportamento em relação ao código original além de implementar `Image`.
- `ImageProxy.java` — o proxy virtual: o construtor só guarda o `filename`, sem criar nem carregar `HighResolutionImage`. Em `display()`, cria a imagem real apenas na primeira chamada (`realImage == null`) e, nas chamadas seguintes, reaproveita a instância já criada — cada proxy carrega sua imagem no máximo uma vez.
- `ProtectedImageProxy.java` — o proxy de proteção do desafio adicional: recebe uma `Image` (nesta demonstração, um `ImageProxy`) e um booleano de permissão. Com permissão, delega a `display()`; sem permissão, imprime uma mensagem de acesso negado e retorna sem chamar a imagem envolvida, de modo que o carregamento nunca é acionado.
- `Main.java` — monta a galeria como `List<Image>` com três `ImageProxy`, mostra que a criação não carrega nada, exibe `forest.jpg` duas vezes (um carregamento, duas exibições), exibe `beach.jpg` uma vez (carregando só essa) e deixa `mountain.jpg` intocada. Depois demonstra o `ProtectedImageProxy` envolvendo um novo `ImageProxy`: uma tentativa sem permissão não carrega a imagem, e uma tentativa com permissão carrega e exibe normalmente.

Saída real do `java -cp out Main`:

```
Gallery created

--- Displaying forest.jpg twice ---
Loading image from disk: forest.jpg
Displaying image: forest.jpg
Displaying image: forest.jpg

--- Displaying beach.jpg ---
Loading image from disk: beach.jpg
Displaying image: beach.jpg

--- mountain.jpg was never displayed, so it was never loaded ---

--- Protected access to a fourth image ---
Access denied: insufficient permission to display this image
Loading image from disk: sunset.jpg
Displaying image: sunset.jpg
```

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Proxy Virtual?**
Todas as imagens eram carregadas no momento da criação de seus objetos, mesmo que o usuário só fosse visualizar uma delas. Com centenas de imagens, isso tornaria a abertura da galeria lenta e desperdiçaria memória com recursos nunca usados. O `ImageProxy` adia a criação de `HighResolutionImage` até a primeira chamada real de `display()`, então a galeria é montada instantaneamente e só se paga o custo de carregar as imagens que de fato são exibidas.

**b) Quais classes representam o sujeito, o sujeito real e o proxy na sua implementação?**
Sujeito (abstração): `Image`. Sujeito real: `HighResolutionImage`. Proxy: `ImageProxy` (proxy virtual) e `ProtectedImageProxy` (proxy de proteção).

**c) Por que o proxy e o objeto real devem implementar a mesma abstração?**
Porque o cliente (a galeria, em `Main`) precisa tratar proxies e objetos reais de forma intercambiável, sem saber qual dos dois está por trás da referência. Como `ImageProxy` e `HighResolutionImage` implementam `Image`, a coleção `List<Image>` e o código que chama `display()` não mudam nada ao trocar um pelo outro — é essa substituibilidade que permite adiar a criação do objeto real de forma transparente.

**d) Como o carregamento sob demanda altera o comportamento da galeria? Qual custo ainda existe na primeira exibição de uma imagem?**
A galeria passa a ser montada sem nenhum custo de carregamento: criar os três `ImageProxy` é imediato, e "Gallery created" aparece antes de qualquer `"Loading image from disk"`. O custo do carregamento não desaparece, só é adiado — na primeira exibição de cada imagem, o proxy ainda precisa criar o `HighResolutionImage` e pagar o preço de `loadFromDisk()`. O ganho é não pagar esse custo por imagens que nunca são exibidas, e não pagá-lo de novo em exibições repetidas da mesma imagem.

**e) Embora Proxy e Decorator possam envolver outro objeto com a mesma interface, como suas intenções se diferenciam?**
O Decorator existe para adicionar responsabilidades a um objeto que já existe, compondo camadas de comportamento sobre ele (como no `BeverageDecorator`, que soma descrição e custo). O Proxy existe para controlar o acesso a um objeto — adiando sua criação (proxy virtual), verificando permissão antes de chamá-lo (proxy de proteção), ou outras formas de intermediação — sem necessariamente adicionar funcionalidade ao que o objeto real faz. Por isso o `ProtectedImageProxy` pode até impedir que a chamada chegue ao objeto envolvido, algo que não faria sentido para um decorador, cujo papel é sempre delegar e enriquecer o resultado.
