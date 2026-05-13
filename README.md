# Sistema de Reserva de Assentos de Cinema

Projeto desenvolvido em Java para simular um sistema de reserva de assentos de cinema via terminal. A aplicacao permite visualizar a disposicao dos assentos, reservar multiplos lugares e validar tentativas de reserva invalidas, como assentos ja ocupados ou inexistentes.

## Visao Geral

O sistema trabalha com uma sala de cinema composta por 80 assentos numerados. A interacao acontece por meio de um menu no terminal, onde o usuario pode consultar o mapa de assentos, solicitar reservas e encerrar a aplicacao.

O objetivo do projeto e demonstrar o uso de recursos modernos da linguagem Java em uma aplicacao simples, objetiva e com regras de negocio bem definidas.

## Funcionalidades

- Visualizacao dos 80 assentos da sala.
- Reserva de um ou mais assentos informados pelo usuario.
- Validacao de assentos ja reservados.
- Validacao de assentos inexistentes.
- Entrada de dados via terminal.
- Exibicao de mensagens de erro para operacoes invalidas.
- Manutencao do estado atualizado dos assentos durante a execucao.

## Tecnologias e Conceitos Utilizados

O projeto utiliza Java puro, sem dependencias externas.

Principais recursos aplicados:

- `record` para representar o modelo de assento de forma simples e imutavel.
- `List` para armazenar a colecao de assentos.
- `Map` para indexar os assentos por numero e otimizar a busca.
- `Stream API` para criacao, transformacao e reconstrucao da lista de assentos.
- `Collectors.toMap` para gerar o mapa de consulta.
- `IntStream.rangeClosed` para criar os 80 assentos automaticamente.
- `Scanner` para entrada de dados pelo terminal.
- `try/catch` para tratar entradas invalidas durante a conversao de texto para numero.
- `switch expression` para organizar as opcoes do menu.

## Estrutura do Projeto

```text
.
|-- ReservaCinema.java
|-- README.md
`-- .vscode/
    `-- launch.json
```

### Arquivo Principal

`ReservaCinema.java` concentra a logica da aplicacao:

- modelo de dados do assento;
- funcao de reserva;
- exibicao do menu;
- exibicao do mapa de assentos;
- leitura e validacao da entrada do usuario;
- loop principal de execucao.

## Modelo de Dados

Cada assento e representado por um `record` chamado `Assento`.

```java
public record Assento(int numero, boolean disponivel) {
    public Assento reservar() {
        return new Assento(numero, false);
    }
}
```

Esse modelo armazena:

- `numero`: identificacao do assento.
- `disponivel`: estado atual do assento.

Como `record` e imutavel por padrao, a reserva retorna uma nova instancia de `Assento` com o status atualizado.

## Regra de Reserva

A funcao `reservarAssentos` recebe:

- uma lista com os assentos atuais;
- uma lista com os numeros de assentos solicitados pelo usuario.

Durante o processamento, o sistema:

1. Converte a lista de assentos em um `Map<Integer, Assento>`.
2. Busca cada assento solicitado pelo numero.
3. Exibe erro quando o assento nao existe.
4. Exibe erro quando o assento ja esta reservado.
5. Atualiza o estado dos assentos validos.
6. Retorna uma nova lista com as reservas aplicadas.

## Interface via Terminal

Ao iniciar a aplicacao, o usuario encontra o seguinte menu:

```text
========== CINEMA ==========
1 - Ver assentos
2 - Reservar assentos
3 - Sair
Escolha uma opcao:
```

### Visualizacao dos Assentos

Os assentos sao exibidos em linhas de 10 posicoes.

```text
[01-D] [02-D] [03-D] [04-D] [05-R] [06-D] [07-D] [08-D] [09-D] [10-D]
```

Legenda:

- `D`: assento disponivel.
- `R`: assento reservado.

### Reserva de Assentos

O usuario pode informar os assentos separados por espaco ou virgula.

Exemplos de entrada valida:

```text
1 2 3
```

```text
1, 2, 3
```

Caso algum valor nao seja numerico, o sistema exibe uma mensagem de erro e continua processando os demais valores validos.

## Como Executar

### Pre-requisitos

- Java instalado na maquina.
- JDK configurado no `PATH`.

Para verificar a instalacao:

```bash
java -version
javac -version
```

### Compilacao

No terminal, dentro da pasta do projeto, execute:

```bash
javac ReservaCinema.java
```

### Execucao

Depois de compilar, execute:

```bash
java ReservaCinema
```

## Execucao pelo VS Code

O projeto possui um arquivo de configuracao em `.vscode/launch.json`.

Para executar pelo VS Code:

1. Abra a pasta do projeto no VS Code.
2. Abra o arquivo `ReservaCinema.java`.
3. Acesse a aba `Run and Debug`.
4. Selecione `Executar ReservaCinema`.
5. Pressione `F5`.

A aplicacao sera executada no terminal integrado, permitindo digitar as opcoes do menu.

## Exemplo de Uso

Entrada:

```text
2
1, 5, 8, 12, 81
```

Possivel saida:

```text
Erro: assento 5 ja esta reservado.
Erro: assento 12 ja esta reservado.
Erro: assento 81 nao existe.
Reserva processada.
```

Nesse exemplo:

- o assento `1` e reservado com sucesso;
- o assento `8` e reservado com sucesso;
- os assentos `5` e `12` ja estavam reservados;
- o assento `81` nao existe, pois a sala possui apenas 80 assentos.

## Pontos Tecnicos de Destaque

- Uso de `Map` para evitar busca linear repetida durante a reserva.
- Criacao automatica dos assentos com `IntStream`.
- Separacao da logica em metodos menores e com responsabilidades claras.
- Modelo de dados simples com `record`.
- Interface de terminal objetiva e facil de testar.
- Tratamento de entradas invalidas sem interromper a execucao do programa.

## Melhorias Futuras

- Separar o projeto em multiplas classes, como `Assento`, `SalaCinema` e `SistemaReserva`.
- Adicionar persistencia em arquivo ou banco de dados.
- Criar testes automatizados para a regra de reserva.
- Permitir cancelamento de reservas.
- Implementar selecao por fileira e coluna.
- Adicionar diferentes salas e sessoes.
- Criar uma interface grafica ou API REST.
- Melhorar a internacionalizacao das mensagens.

## Status do Projeto

Projeto funcional em versao inicial, com foco em logica de reserva, validacao de entrada e interacao via terminal.
