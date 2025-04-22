# WhatsApp Assistant

> ⚠️ **Este projeto ainda está em desenvolvimento.**


## 💡 Visão Geral

Este projeto tem como objetivo oferecer um assistente pessoal financeiro diretamente pelo WhatsApp. Com o uso de inteligência artificial, o bot entende e categoriza os seus gastos, envia resumos, acompanha metas e muito mais — tudo por mensagens.

## 🚀 Funcionalidades

- ✅ Registrar despesas via chat no WhatsApp
- ✅ Visualizar saldo do mês
- ✅ Resumo semanal/mensal com IA
- ✅ Categorizar gastos automaticamente
- ✅ Alertas de metas estouradas

## 🛠 Tecnologias Utilizadas

- **Spring Boot** — Backend da aplicação
- **PostgreSQL** — Banco de dados relacional
- **API do WhatsApp** — Canal de comunicação com o usuário
- **OpenAI API** — Processamento de linguagem natural e inteligência artificial

## 🧪 Como Executar o Projeto

> ⚠️ Em desenvolvimento: esta seção será atualizada com detalhes técnicos.

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/whatsapp-assistant.git
   cd whatsapp-assistant
   ```

2. Configure o arquivo `.env` com suas credenciais:
    - Chave da API do OpenAI
    - URL e credenciais do banco PostgreSQL
    - Dados de integração com a API do WhatsApp

3. Execute a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```

## 💬 Como Interagir com o Bot

A interação será feita por meio de mensagens no WhatsApp. Exemplos de comandos:

- `Gastei 45 reais no mercado`
- `Quanto eu já gastei esse mês?`
- `Me mostra um resumo da semana`
- `Alcancei minha meta?`
