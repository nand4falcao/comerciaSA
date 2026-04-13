# 📋 README - Agenda Comércio S.A
Sistema de Cadastro e Gestão de Clientes
📖 Sobre o Projeto
Sistema completo para gestão de clientes e seus contatos, desenvolvido como parte de um processo seletivo para estágio. A aplicação permite cadastrar, consultar, editar e excluir clientes, além de gerenciar contatos (telefone/email) associados.

# 🏗️ Estrutura do Projeto

agenda/
├── src/main/java/com/muralis/agenda/
│   ├── AgendaApplication.java              # Classe principal Spring Boot
│   ├── controller/
│   │   ├── ClienteController.java          # Controlador MVC e APIs REST
│   │   └── GlobalExceptionHandler.java     # Tratamento global de exceções
│   ├── model/
│   │   ├── Cliente.java                    # Entidade Cliente
│   │   └── Contato.java                    # Entidade Contato
│   └── repository/
│       ├── ClienteRepository.java          # Acesso a dados de Cliente
│       └── ContatoRepository.java          # Acesso a dados de Contato
│
├── src/main/resources/
│   ├── application.properties              # Configurações da aplicação
│   └── templates/                          # Views Thymeleaf
│       ├── clientes.html                   # Página inicial/menu
│       ├── cadastroClientes.html           # Formulário de cadastro
│       └── consultaClientes.html           # Listagem e busca
│
├── src/main/resources/static/
│   ├── css/
│   │   └── style.css                       # Estilos globais
│   └── js/
│       ├── script.js                       # JS do cadastro
│       └── consultaClientes.js             # JS da consulta/API
│
└── pom.xml                                  # Dependências Maven


#📁 Descrição dos Pacotes
Pacote	Responsabilidade
controller	Gerencia requisições HTTP, validações, redirecionamentos e endpoints REST
model	Entidades JPA que representam as tabelas do banco de dados
repository	Interface com Spring Data JPA para operações CRUD e consultas customizadas
templates	Arquivos Thymeleaf para renderização das páginas HTML
static	Arquivos estáticos: CSS, JavaScript e imagens

#🛠️ Principais Tecnologias
Tecnologia	Versão	Finalidade
Java	17+	Linguagem backend
Spring Boot	3.x	Framework principal
Spring MVC	-	Arquitetura de controle
Spring Data JPA	-	Persistência de dados
Thymeleaf	-	Template engine (renderização server-side)
MySQL	8+	Banco de dados relacional
Maven	-	Gerenciador de dependências
HTML5/CSS3	-	Interface do usuário
JavaScript (Vanilla)	ES6	Interatividade e chamadas AJAX/fetch


# 📦 Dependências (pom.xml)
xml
<dependencies>
    <!-- Spring Boot Starter Web (MVC + REST) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Spring Boot Starter Thymeleaf (Views) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>

    <!-- Spring Boot Starter Data JPA (Persistência) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- MySQL Connector -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- Validações Bean Validation -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
</dependencies>

# 🚀 Instruções para Execução
Pré-requisitos
Java 17+ instalado

MySQL 8+ instalado e rodando

Maven instalado (ou usar wrapper)

Passo a Passo
1. Clonar o repositório
bash
git clone https://github.com/seu-usuario/agenda-comercio.git
cd agenda-comercio
2. Configurar o Banco de Dados
Crie o banco no MySQL:

sql
CREATE DATABASE agenda CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
Configure o arquivo src/main/resources/application.properties:

properties
spring.datasource.url=jdbc:mysql://localhost:3306/agenda?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=SUA_SENHA

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
⚠️ Importante: Altere SUA_SENHA para a senha do seu MySQL.

3. Executar a Aplicação
Via Maven:

bash
mvn spring-boot:run
Ou via IDE (Eclipse/IntelliJ): execute a classe AgendaApplication.java

4. Acessar a Aplicação
Abra o navegador em: http://localhost:8080

#✅ Checklist do que foi Implementado
Backend
CRUD completo de clientes

CRUD de contatos (telefone/email)

Relacionamento @OneToMany entre Cliente e Contato

Busca combinada: por nome (ignore case) e por CPF

Busca de CPF ignorando pontos e traços (SQL nativo)

API REST para listar contatos (GET /api/clientes/{id}/contatos)

API REST para editar contato (PUT /api/clientes/{id}/contatos/{idContato})

API REST para excluir cliente (DELETE /api/clientes/{id})

Tratamento global de exceções (@ControllerAdvice)

Validações com @Valid e BindingResult

Cascade delete (CascadeType.ALL)

Frontend
Página inicial com menu (cadastrar/consultar)

Formulário de cadastro com validação HTML5

Tabela de clientes com busca por nome/CPF

Linhas da tabela clicáveis para exibir contatos

Modal para exibir contatos do cliente

Edição de contato via modal e PUT

Exclusão de cliente com confirmação

CSS responsivo (mobile/desktop)

Banco de Dados
Tabela clientes (id, nome, cpf, dataNasc, endereco, cidade, estado, cep)

Tabela contatos (id, tipoContato, valorContato, observacoes, cliente_id)

Chave estrangeira com ON DELETE CASCADE

CPF único no banco

🔗 Links de Referência
Recurso	Link
Spring Boot Documentation	https://spring.io/projects/spring-boot
Spring Data JPA	https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
Thymeleaf Tutorial	https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html
MySQL Connector/J	https://dev.mysql.com/doc/connector-j/en/
Bean Validation	https://beanvalidation.org/
MDN Web Docs (JS/CSS)	https://developer.mozilla.org/
Baeldung - Spring MVC	https://www.baeldung.com/spring-mvc
🤖 Uso de IA no Desenvolvimento
Sim, utilizei Inteligência Artificial como ferramenta de apoio.
Como utilizei:
Tarefa	Ferramenta	Como a IA ajudou
Estrutura inicial do CSS	ChatGPT	Gerou base com variáveis CSS e grid responsivo
JavaScript do modal	ChatGPT/Copilot	Esqueleto da lógica de abrir/fechar modal
Consulta nativa de CPF	ChatGPT	Sugeriu usar @Query com REPLACE para remover formatação
Tratamento de exceções	ChatGPT	Exemplos de @ControllerAdvice e DataIntegrityViolationException
Documentação do README	IA atual	Estruturação deste documento
Impacto da IA:
Positivo:

Acelerou a escrita de código boilerplate

Ajudou a descobrir soluções (ex: consulta nativa para CPF)

Reduziu tempo de pesquisa em documentação

Limitações (importante destacar):

A IA não entende regras de negócio - precisei adaptar sugestões

Código gerado precisou de revisão manual (ex: validação de data)

Problemas como CPF sem validação real não foram identificados pela IA

💡 Conclusão: IA é uma ferramenta de aceleração, mas não substitui o entendimento do domínio da aplicação. Toda sugestão foi revisada, testada e adaptada ao contexto do desafio.

⚠️ Pontos de Melhoria (Identificados)
Problema	Status	Solução Proposta
Validação real de CPF (dígitos)	❌ Não implementado	Implementar algoritmo ou usar biblioteca
Múltiplos contatos por cliente	❌ Não implementado	Adicionar campos dinâmicos no frontend
Modal de sucesso no cadastro	❌ Não implementado	Usar fetch API com then()
Confirmação customizada para exclusão	❌ Não implementado	Modal customizado ao invés de window.confirm
Testes automatizados	❌ Não implementado	Adicionar JUnit + MockMvc
📝 Autor
Maria Fernanda Falcão de Lima

📄 Licença
Este projeto foi desenvolvido para fins educacionais como parte de um processo seletivo.

