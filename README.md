# HTML Parser – Deepest Text Analyzer

## Descrição

Este projeto consiste em um programa Java que, a partir de uma **URL contendo um arquivo HTML**, identifica e imprime o **trecho de texto localizado no nível mais profundo da estrutura HTML**.

A solução foi desenvolvida de acordo com os requisitos do teste técnico, respeitando todas as restrições impostas, como a ausência de bibliotecas externas e o não uso de APIs de parsing HTML/XML/DOM do JDK.

O programa também é capaz de **identificar estruturas HTML mal-formadas**, retornando mensagens de erro apropriadas.

---

## Funcionalidade

Dado um documento HTML com as seguintes premissas:

- Uma instrução por linha (tag de abertura, tag de fechamento ou texto)
- Tags sempre em pares (`<tag>` e `</tag>`)
- Sem atributos em tags
- Sem tags auto-fechantes
- Indentação opcional (ignorada)
- Linhas em branco ignoradas

O programa:

1. Analisa a estrutura HTML linha a linha  
2. Calcula a profundidade de cada trecho de texto  
3. Retorna o **primeiro texto encontrado no nível máximo de profundidade**  
4. Detecta erros estruturais (HTML mal-formado)

---

## Estrutura do Projeto

O projeto é composto apenas por arquivos `.java`, todos no mesmo diretório, conforme exigido pelo teste:

```
HtmlAnalyser.java   // Classe principal
LineInfo.java      // Representa uma linha do HTML e seu número
Result.java        // Armazena e imprime o resultado da análise
```

---

## Lógica da Solução

- O HTML é baixado a partir da URL utilizando `java.net.URL`
- Cada linha válida é armazenada como um objeto `LineInfo`
- Uma **pilha (`ArrayDeque`)** é utilizada para controlar a abertura e fechamento das tags
- A profundidade atual é incrementada ao abrir tags e decrementada ao fechá-las
- Quando um trecho de texto é encontrado, sua profundidade é comparada com a máxima já registrada
- Ao final:
  - Se houver tags não fechadas ou fechamentos incorretos → HTML mal-formado
  - Caso contrário → retorna o texto mais profundo

---

## Compilação

A partir do diretório que contém os arquivos `.java`, execute:

```bash
javac HtmlAnalyser.java
```

Nenhuma configuração adicional é necessária.

---

## Execução

Após a compilação, execute o programa informando a URL do HTML a ser analisado:

```bash
java HtmlAnalyser https://exemplo.com/arquivo.html
```

---

## Saídas Possíveis

O programa gera **apenas um dos seguintes outputs no console padrão**, conforme o edital:

- Texto mais profundo encontrado no HTML  
- Mensagem de erro indicando HTML mal-formado  
- Mensagem de erro indicando falha na conexão com a URL  

Exemplo de saída válida:

```
Maxima profundidade: 4
Texto mais profundo: Conteúdo profundo.
```

---

## Compatibilidade

- Java: **JDK 17**
- Sistema operacional: independente
- Bibliotecas externas: **não utilizadas**

---

## Observações

- O projeto foi desenvolvido exclusivamente com classes padrão do JDK
- Não utiliza APIs de parsing HTML, XML ou DOM
- Segue rigorosamente os requisitos técnicos e funcionais do teste
