Gerador de Notas de Despesas e Receitas
Este projeto é uma API RESTful em Spring Boot que permite gerenciar despesas e receitas pessoais, além de gerar relatórios PDF com resumo financeiro.

Ele serve como exemplo de aplicação backend completa, demonstrando CRUD, validação, filtros por período e documentação com Swagger.

Funcionalidades

CRUD completo para Despesas e Receitas

Validação de campos (descrição obrigatória, valor positivo, data válida)

Filtros por período (inicio e fim) para relatórios

Relatórios PDF:

Relatório de despesas

Relatório de receitas

Relatório unificado (despesas + receitas + saldo)

Documentação de API com Swagger / OpenAPI

Tratamento de erros padronizado (400 Bad Request, 404 Not Found)

Tecnologias

Java 17+

Spring Boot 3

Spring Data JPA	

PostgreSQL (ou outro banco relacional)

iText (geração de PDF)

Swagger / OpenAPI

Maven

Lombok

Endpoints principais
Despesas
Método	Endpoint	Descrição
POST	/despesas	Criar nova despesa

GET	/despesas	Listar todas as despesas

GET	/despesas/{id}	Buscar despesa por ID

PUT	/despesas/{id}	Atualizar despesa existente

DELETE	/despesas/{id}	Deletar despesa por ID

GET	/despesas/relatorio?inicio=YYYY-MM-DD&fim=YYYY-MM-DD

Receitas
Método	Endpoint	Descrição

POST	/receitas	Criar nova receita

GET	/receitas	Listar todas as receitas

GET	/receitas/{id}	Buscar receita por ID

PUT	/receitas/{id}	Atualizar receita existente

DELETE	/receitas/{id}	Deletar receita por ID

GET	/receitas/relatorio?inicio=YYYY-MM-DD&fim=YYYY-MM-DD	

Relatório Total (Unificado)

Método	Endpoint	Descrição

GET	/relatorio?inicio=YYYY-MM-DD&fim=YYYY-MM-DD	Retorna PDF com receitas, despesas e saldo final
