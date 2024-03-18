# Hardwired

## Estrutura

A estrutura consiste em uma classe que executa uma tarefa assíncrona genérica, exemplo consiste em uma tarefa assíncrona que emite um evento que é processado por vários sistemas diferentes (AnalyticsSystem, DatabaseConnection e View). Na implementação hardwired, a tarefa assíncrona chama todas as dependências quando a tarefa é finalizada.

### Pontos Fortes
É um código fácil de ser implementado, dividindo os métodos que são necessários e apenas passando para o emissor de eventos.

Pode ser implementado o "polling", perguntando para o emissor de eventos se o evento já aconteceu toda vez que for chamado.

É simples, sendo fácil de entender o fluxo de dependências e consequentemente facilitando a navegação e o debug do sistema.

### Pontos Fracos
O problema deste uso é a dependência: o emissor de eventos depende dos objetos que são os receptores dos eventos, deixando muito difícil a manutenção e alteração do código.

É muito mais difícil adicionar ou remover receptores de eventos sem ter que alterar a lógica de emissão, obrigando o desenvolvedor a sempre mexer em diferentes métodos que não tem relação com a mudança desejada.

Apesar do "polling" ser uma opção, a ineficiência dele seria muito grande para cada evento novo adicionado. Apesar de usar menos memória, consome bastante o tempo de CPU.