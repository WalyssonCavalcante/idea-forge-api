# 🚀 IdeaForge API

**IdeaForge API** é uma API backend que atua como um **Arquiteto de Software Virtual**, responsável por gerar **especificações técnicas completas de projetos** com base na stack tecnológica, nível de senioridade e áreas de foco do desenvolvedor.

O projeto foi desenvolvido como uma **implementação de referência em Engenharia de Software**, demonstrando na prática:

- Clean Architecture aplicada a APIs reais
- Princípios SOLID
- Integração segura e desacoplada com LLMs (Google Gemini)
- Design orientado a domínio (Domain-First)

---

## 🎯 Objetivo do Projeto

O IdeaForge existe para ajudar desenvolvedores a:

- Praticar stacks específicas com **desafios bem arquitetados**
- Evoluir pensamento arquitetural e tomada de decisão técnica
- Receber **especificações técnicas realistas**, similares às exigidas em ambientes profissionais
- Estudar arquitetura limpa aplicada a integrações com IA

---

## 🧠 Arquitetura e Design

O projeto segue rigorosamente os princípios de:

- **Clean Architecture**
- **Hexagonal Architecture (Ports & Adapters)**
- **Domain-First Design**

Essas escolhas garantem:

- Baixo acoplamento entre regras de negócio e infraestrutura
- Alta testabilidade
- Facilidade de evolução
- Substituição simples do provedor de IA no futuro

---

## 📦 Estrutura de Pacotes

```text
com.ideaforge.api
├── config
│   └── Configurações de infraestrutura
│      (Beans, Security, CORS, Clients)
│
├── domain
│   └── idea
│       ├── IdeaController.java
│       │   └── Adapter de entrada (HTTP / REST)
│       │
│       ├── IdeaService.java
│       │   └── Casos de uso da aplicação
│       │
│       ├── dtos
│       │   └── DTOs de entrada e saída (Records)
│       │
│       └── ports
│           └── AiGenerationService.java
│               └── Porta de saída (contrato)
│
└── infra
    └── ai
        └── gemini
            └── Implementação concreta da integração
                com o Google Gemini
```

## ⚙️ Decisões Técnicas

### Java Records

Utilizados principalmente nos DTOs para:

- Garantir **imutabilidade**
- Reduzir **boilerplate**
- Aumentar a **clareza semântica** dos contratos da API

Essa escolha reforça a ideia de que DTOs são apenas estruturas de dados, sem comportamento.

---

### Dependency Inversion Principle (DIP)

O core da aplicação **não conhece detalhes de infraestrutura**.

- O domínio depende apenas da interface `AiGenerationService`
- A implementação concreta (Google Gemini) vive na camada de infraestrutura
- Permite trocar o provedor de IA sem impacto no domínio

---

### Spring RestClient (Spring Boot 3.2+)

Utilizado como cliente HTTP para comunicação com serviços externos:

- API moderna e fluente
- Melhor legibilidade em comparação ao `RestTemplate`
- Integração natural com o ecossistema Spring

---

### Stateless API

A aplicação é totalmente stateless:

- Nenhum estado é mantido entre requisições
- Pronta para **escalabilidade horizontal**
- Adequada para execução em **Docker / Kubernetes**

---

## 🛠 Tech Stack

- **Linguagem:** Java 21 (LTS)
- **Framework:** Spring Boot 3.x
- **IA Engine:** Google Gemini (REST API)
- **Build Tool:** Maven
- **HTTP Client:** Spring RestClient

---

## ▶️ Como Rodar Localmente

### Pré-requisitos

- Java JDK 21+
- Maven (ou utilizar o wrapper `./mvnw`)
- Chave de API válida do Google AI Studio

---

### Passo a Passo

Clone o repositório:

```bash
git clone [https://github.com/WallyssonCavalcante/idea-forge-api.git](https://github.com/WalyssonCavalcante/idea-forge-api.git)
cd idea-forge-api
```

## Configure as variáveis de ambiente:

O projeto possui **suporte nativo a arquivos `.env`**.  
Para configurar a aplicação, basta criar um arquivo `.env` na raiz do projeto com as mesmas variáveis definidas em `env.example`.

### Passo 1 — Criar o arquivo `.env`

```bash
cp env.example .env
```

### Passo 2 — Configurar a chave da API

Edite o arquivo .env e informe sua chave do Google Gemini:

```bash
GEMINI_API_KEY=sua_chave_aqui
```

🔐 O arquivo .env não deve ser versionado.
Ele já está incluído no .gitignore.

### Passo 3 - Executar a Aplicação

```bash
./mvnw spring-boot:run
```

A API estará disponível em: http://localhost:8080

## 🔌 Documentação da API

### Gerar Ideia de Projeto

Gera uma especificação técnica completa baseada nos parâmetros fornecidos.

- **URL:** `/api/v1/ideas/generate`
- **Method:** `POST`
- **Content-Type:** `application/json`

#### Payload de Requisição (JSON)

```json
{
  "stacks": ["Java", "Spring Boot", "Kafka"],
  "experienceLevel": "Advanced",
  "focusAreas": ["Microservices", "Event-Driven"]
}
```

| Campo             | Tipo           | Descrição                                              |
| ----------------- | -------------- | ------------------------------------------------------ |
| `stacks`          | `List<String>` | Tecnologias que o usuário deseja praticar.             |
| `experienceLevel` | `String`       | Nível do desafio (Iniciante, Intermediário, Avançado). |
| `focusAreas`      | `List<String>` | (Opcional) Áreas específicas como DevOps, QA, etc.     |

#### Exemplo de Resposta (JSON)

```json
{
  "markdownContent": "# EventStream Pro\n\n## Contexto e Objetivo\nDesenvolver uma plataforma de processamento de..."
}
```

### 🧪 Testando com cURL

Você pode testar a API diretamente do terminal sem necessidade de Frontend:

```bash
curl -X POST http://localhost:8080/api/v1/ideas/generate \
  -H "Content-Type: application/json" \
  -d '{
    "stacks": ["Java", "Spring Boot"],
    "experienceLevel": "Intermediário",
    "focusAreas": ["Clean Code"]
  }'
```
