{README EM CONSTRUÇÃO}
# API Lista de tarefas

Este projeto é uma API para cadastro de usuários e também cadastro, atualização e listagem de tarefas.

## Tecnologias

- **Java e SpringBoot**: Desenvolvimento do backend.
- **Maven**: Gerenciador de pacotes.
- **Render e arquivo Dockerfile**: Deploy da aplicação.

## Endpoints

## URL: https://springboot-todolist-ot7r.onrender.com

### 1. Criar usuário
- Método: `POST`
- Endpoint: `/users/`
- Corpo da requisição: 
`{
    "username": "alineccorrea",
    "name":"aline",
    "password": "12345"
}`
- Resposta: 
`HTTP Status Code: 200`
`O corpo da response trás os dados cadastrados do usuário.`

### 2. Criar tarefa 
- Método: `POST`
- Endpoint: `/tasks/`
- Corpo da requisição: 
`{
    "title": "Titulo até 50 caracteres",
    "description":"teste",
    "startAt": "2024-12-10T08:30:00",
    "endAt": "2024-12-10T11:30:00",
    "priority": "Baixa"
}`
- Resposta:
  - `HTTP Status Code: 200` Tarefa cadastrada com sucesso.
`O corpo da response trás os dados cadastrados da tarefa.`
  - `HTTP Status Code: 400` Indica que o título utrapassou 50 caracteres.

### 2. Listar tarefa 
- Método: `GET`
- Endpoint: `/tasks/`
- Requisição: ´No header da requisição deve enviar o Basic Auth de usuário já cadastrado.`
- Resposta:
  - `HTTP Status Code: 200`
`O corpo da response lista as tarefas cadastradas pelo usuário logado na autenticação da request.`
  - `HTTP Status Code: 400`
