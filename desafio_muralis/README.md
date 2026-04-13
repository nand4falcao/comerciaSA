# 🚀 Desafio Muralis - Agenda de Contatos

Este projeto é um sistema de gerenciamento de clientes e contatos desenvolvido como parte de um desafio técnico para a Muralis. A aplicação permite o cadastro, consulta, edição e exclusão de clientes, além de gerenciar múltiplos contatos vinculados a cada perfil.

🛠️ Tecnologias Utilizadas
Backend: Java 17 com Spring Boot.

Persistência: Spring Data JPA e MySQL/MariaDB.

Frontend: HTML5, CSS3, JavaScript (Vanilla) e Thymeleaf para renderização dinâmica.

Validação: Bean Validation (Hibernate Validator).

📋 Funcionalidades
Cadastro de Clientes: Registro de Nome, CPF, Data de Nascimento e Endereço completo.

Gestão de Contatos: Cada cliente pode possuir múltiplos contatos (Telefone ou E-mail) com campo para observações.

Busca Inteligente: Consulta de clientes por Nome ou CPF (com ou sem formatação) através de queries nativas.

Interface Responsiva: Design moderno inspirado no Material Design, adaptável a dispositivos móveis.

Segurança de Dados: Tratamento de erros globais e sanitização de inputs para evitar espaços desnecessários nos registros.

🤖 Uso de Inteligência Artificial no Desenvolvimento
Este projeto foi desenvolvido com o apoio de ferramentas de IA (como o Google Gemini), seguindo uma abordagem de "IA-Augmented Development". A utilização da IA foi focada nos seguintes pilares:

Arquitetura e Estruturação: Auxílio na definição de relacionamentos @OneToMany e @ManyToOne entre as entidades Cliente e Contato para garantir a integridade referencial e o comportamento de exclusão em cascata (CascadeType.ALL).

Refatoração de Código: Otimização de métodos de busca no ClienteRepository, incluindo a criação de queries nativas em SQL para tratar a limpeza de caracteres especiais em CPFs durante a consulta.

Desenvolvimento de UI/UX: Geração de estilos CSS modernos e estruturação de scripts JavaScript assíncronos (Fetch API) para manipulação de modais e atualização de contatos sem recarregar a página.

Tratamento de Exceções: Implementação de um GlobalExceptionHandler e validações de integridade de dados para garantir que erros do banco de dados (como CPFs duplicados) sejam retornados de forma amigável ao usuário.

🚀 Como Executar
Clone o repositório:

Bash
git clone https://github.com/nand4falcao/desafio_muralis.git
Configure a conexão com o banco de dados MySQL no arquivo src/main/resources/application.properties.

Execute a aplicação via Maven ou sua IDE de preferência:

Bash
./mvnw spring-boot:run
Acesse http://localhost:8080 no seu navegador.
