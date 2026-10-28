# Template Method — Sistema de Geração de Relatórios

## Estrutura da solução

- `ReportGenerator.java` — a classe abstrata que define o esqueleto do algoritmo:
  - `generate()` — público e `final`, é o Template Method. Executa, nessa ordem, `readData()`, `formatData(data)`, `saveReport(content)` e `afterGeneration()`, passando os dados lidos para a formatação e o conteúdo formatado para o salvamento.
  - `readData()` — protegido e `final`: etapa comum, exibe "Reading sales data" e retorna `{"Notebook", "Mouse"}`.
  - `formatData(String[] data)` e `saveReport(String content)` — protegidos e abstratos: as etapas que variam por formato.
  - `afterGeneration()` — protegido, com comportamento padrão de exibir "Report generated successfully": é o hook.
- `CsvReport.java` e `HtmlReport.java` — estendem `ReportGenerator` e implementam apenas `formatData()` e `saveReport()`, preservando os formatos e os nomes `report.csv` e `report.html` do código inicial.
- `TextReport.java` — o formato do desafio adicional: um produto por linha, salvo em `report.txt`. Sobrescreve o hook `afterGeneration()` chamando `super.afterGeneration()` e, em seguida, exibindo uma notificação simulada.
- `Main.java` — usa referências do tipo `ReportGenerator` para gerar os três relatórios.

Saída real do `java -cp out Main`:

```
--- CSV report ---
Reading sales data
Saving report.csv
Product
Notebook
Mouse
Report generated successfully

--- HTML report ---
Reading sales data
Saving report.html
<ul><li>Notebook</li><li>Mouse</li></ul>
Report generated successfully

--- Text report (overrides the afterGeneration hook) ---
Reading sales data
Saving report.txt
Notebook
Mouse
Report generated successfully
Notification: report.txt is available
```

A saída confirma que as quatro etapas ocorrem sempre na mesma ordem (leitura, salvamento do conteúdo formatado, conclusão) e que só o conteúdo e o nome do arquivo mudam entre os formatos.

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Template Method?**
`CsvReport` e `HtmlReport` repetiam todo o fluxo de geração: a leitura dos dados, a ordem das etapas e a mensagem de conclusão estavam duplicadas. Uma mudança na leitura ou na sequência exigia editar todas as classes, e cada novo formato poderia executar as etapas em outra ordem ou esquecer uma delas. Agora o fluxo está escrito uma única vez em `ReportGenerator.generate()`, e as subclasses só fornecem o que de fato muda.

**b) Qual método representa o Template Method e quais etapas são comuns, abstratas ou hooks na sua implementação?**
O Template Method é `generate()`. Etapa comum: `readData()` (implementada e `final` na classe base). Etapas abstratas: `formatData(String[] data)` e `saveReport(String content)`. Hook: `afterGeneration()`, que tem comportamento padrão e é sobrescrito apenas por `TextReport`.

**c) Por que o método generate() foi declarado como final?**
Para garantir que nenhuma subclasse altere a sequência do algoritmo. Se `generate()` pudesse ser sobrescrito, um novo formato poderia pular a leitura, inverter etapas ou omitir a conclusão, voltando ao problema original. Com `final`, as subclasses só conseguem atuar nos pontos de extensão previstos pela classe base. Pelo mesmo motivo `readData()` também é `final`: é uma etapa comum que não deve ser personalizada.

**d) Qual é a diferença entre uma etapa abstrata obrigatória e um hook com implementação padrão?**
Uma etapa abstrata (`formatData`, `saveReport`) não tem implementação na classe base, então toda subclasse concreta é obrigada pelo compilador a implementá-la, já que não existe um comportamento razoável que sirva para todos os formatos. Um hook (`afterGeneration`) já tem um comportamento padrão que funciona para a maioria dos casos. A subclasse pode sobrescrevê-lo para personalizar ou complementar esse comportamento (como `TextReport`, que chama `super.afterGeneration()` e acrescenta a notificação), mas não é obrigada: `CsvReport` e `HtmlReport` simplesmente herdam o padrão.

**e) Como Template Method e Strategy diferem quanto ao uso de herança ou composição e à possibilidade de variar o comportamento em tempo de execução?**
O Template Method usa herança: a variação é definida pela subclasse escolhida no momento da criação do objeto, e um `CsvReport` será sempre CSV; não é possível trocar a formatação de um objeto já criado. Além disso, ele varia *partes* de um algoritmo cuja estrutura fica fixa na classe base. O Strategy usa composição: o contexto mantém uma referência para um objeto que encapsula o algoritmo *inteiro*, e essa referência pode ser trocada em tempo de execução (como o `setStrategy()` da `ShippingCalculator`). Em troca, o Template Method reaproveita naturalmente o código comum da classe base, enquanto no Strategy cada estratégia é independente.
