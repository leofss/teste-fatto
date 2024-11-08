# Task

Este repositório contém uma aplicação para admnistrar tarefas em Java e Spring Boot e ReactJs.

## Descrição do Projeto

Desenvolver uma aplicação de gerenciamento de tarefas (todo list) com as seguintes funcionalidades:

2. **Gerenciamento de Tarefas**:
   - Criar, editar, excluir e listar tarefas.
   - Cada tarefa deve ter um título, custo, prazo
3. **Ordenação**:
   - Permitir que as tarefas sejam remanjadas de posição.

## Instruções para Implementação Com Docker

### Passos para Configuração

1. Clone o repositório:   
    ```bash
    git@github.com:leofss/teste-fatto.git
    cd teste-fatto
    ```
2. Suba o container (**verifique se a porta 8081 e 3000 estão disponíveis**)
   ```bash
    docker-compose up
    ```
3. Acesse [Swagger local](http://localhost:8081/swagger-ui/index.html) para verificar os endpoints
4. Acesse [Localhost](http://localhost:3000) para visualizar o frontend
