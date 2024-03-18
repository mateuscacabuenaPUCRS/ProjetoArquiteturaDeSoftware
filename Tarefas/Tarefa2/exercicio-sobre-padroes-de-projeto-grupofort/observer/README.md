# Observer

Ao utilizarmos o padrão **Observer**, temos um objeto que é responsável por manter uma lista de seus dependentes, chamados de observadores, e notificá-los automaticamente de qualquer mudança de estado, geralmente chamando um de seus métodos.

## Estrutura

![Observer](https://refactoring.guru/images/patterns/diagrams/observer/structure.png)

Como podemos ver na figura acima, uma classe "Observável" (Subject) mantém uma lista de observadores (Observer) e possui métodos para que estes se inscrevam (observem) e se desinscrevam (deixem de observar) do objeto. Além disso, a classe "Observável" possui um método que notifica todos os observadores de que houve uma mudança de estado.

### Pontos Fortes

O padrão Observer permite que objetos interessados sejam notificados automaticamente de mudanças de estado em outro objeto, sem que haja acoplamento entre eles.

### Pontos Fracos

Não podemos assumir ordem de notificação dos observadores. Se um observador depende de outro, o padrão Observer pode se tornar complicado de se entender. Uma alternativa seria quebrar o observável em partes menores para garantir a ordem entre as partes.

### Nossa implementação

No nosso código, a classe "Observável" é representada no arquivo `AsyncTaskObservable.java` e envia um sinal para os observadores 5 segundos após sua criação, puramente por motivos de teste.

Temos também 3 observadores, representados pelos arquivos `AnalyticsSystem.java`, `DatabaseConnection.java` e `View.java`. Cada um deles possui um método que é chamado quando o observável notifica que houve uma mudança de estado.

### Melhorias em relação à implementação "Hardwired"

Na implementação "Hardwired", o observável precisava conhecer cada um dos futuros observadores (na prática, o construtor do observável recebia uma instância de cada observador). No entanto, com o padrão Observer, o observável não precisa conhecer os observadores, apenas a interface que eles implementam. Isso permite que novos observadores sejam adicionados sem que o observável precise ser modificado.
