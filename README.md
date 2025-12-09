# 💰 Sistema Especialista de Gestão Financeira

> Projeto acadêmico (Extensão - 6º Período) desenvolvido para auxiliar usuários na gestão da sobra do salário mensal, aplicando regras de prioridade e perfis de comportamento financeiro.

O sistema coleta dados financeiros e pessoais (como perfil de consumo) e utiliza um algoritmo especialista para sugerir a alocação ideal de recursos, focando em quitação de dívidas e formação de reservas.

## 🧠 A Lógica do Sistema (Regras de Negócio)

O sistema não faz apenas cálculos matemáticos simples; ele aplica **regras de prioridade** baseadas em boas práticas financeiras:

1.  **1ª Prioridade - Dívidas:** Aloca até 70% da sobra para quitar dívidas existentes.
2.  **2ª Prioridade - Reserva de Emergência:** Foca em atingir o equivalente a 6 meses de gastos fixos.
3.  **3ª a 6ª Prioridade:** Distribui o restante entre **Investimentos Fixos**, **Investimentos Variáveis**, **Objetivo Pessoal** e **Lazer**.

### 📊 Perfis de Consumo
A distribuição final para "Lazer" e "Investimentos" é ajustada automaticamente baseada no perfil:
* 🟢 **Conservador:** Menos lazer, mais investimentos.
* ⚖️ **Equilibrado:** Distribuição balanceada.
* 🔴 **Gastador:** Maior fatia para lazer (respeitando limites).

---

## 🏗️ Arquitetura e Design

O projeto segue rigorosamente o padrão **MVC-DAO** para garantir organização e escalabilidade:

* **Controller:** Gerencia as requisições REST e DTOs (`UserController`).
* **Service:** Contém a inteligência e o algoritmo especialista (`UserService`).
* **Repository (DAO):** Persistência de dados com Spring Data JPA (`UserRepository`).
* **Frontend:** Interface reativa em Vue.js consumindo a API via JSON.

---

## 🛠️ Tecnologias Utilizadas

### Backend (API)
* **Linguagem:** Java 17
* **Framework:** Spring Boot
* **Banco de Dados:** PostgreSQL (Produção) / H2 (Testes)
* **Gerenciador:** Maven/Gradle

### Frontend (Web)
* **Framework:** Vue.js
* **Comunicação:** Axios / REST API

---

## 💾 Banco de Dados

O projeto inclui o script SQL completo para criação da estrutura no PostgreSQL.
* Arquivo: `sistema.sql` (Disponível na raiz deste repositório).

---

## 🚀 Como Executar o Projeto

Este é um projeto **Fullstack** (Backend + Frontend).

### 1️⃣ Backend (Spring Boot)
1.  Navegue até a pasta do backend.
2.  Crie um banco de dados no PostgreSQL chamado `financeiro_db` (ou configure o `application.properties`).
3.  **Importe o arquivo `sistema.sql`** para criar as tabelas e estrutura inicial.
4.  Execute a aplicação no IntelliJ IDEA.

### 2️⃣ Frontend (Vue.js)
1.  Navegue até a pasta do frontend.
2.  Abra no VS Code.
3.  Instale as dependências e rode o servidor:
    ```bash
    npm install
    npm run serve
    ```
4.  Acesse no navegador (geralmente em `http://localhost:8080`).

---

## 👨‍💻 Autor

**Gabriel Diniz Prates**
* Ciência da Computação - Centro Unifor-MG
