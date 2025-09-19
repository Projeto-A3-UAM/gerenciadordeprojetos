# Gerenciador de Projetos

## Sobre o Projeto

Este sistema permite gerenciar usuários, equipes e projetos, além de realizar vínculos entre eles e emitir relatórios detalhados. Ideal para ambientes acadêmicos ou profissionais que precisam organizar atividades e pessoas.

## Funcionalidades

- Cadastro, edição e exclusão de usuários, equipes e projetos
- Vincular usuário a equipe
- Vincular equipe a projeto
- Relatório detalhado de projetos (status, gerente, equipes)
- Sincronização automática dos vínculos ao iniciar o sistema
- Persistência dos dados em arquivos texto

## Como Usar

Execute o programa principal (`Main.java`) e utilize o menu interativo:

1. Cadastrar Usuário
2. Listar Usuários
3. Cadastrar Projeto
4. Listar Projetos
5. Cadastrar Equipe
6. Listar Equipes
7. Vincular Usuário a Equipe
8. Vincular Equipe a Projeto
9. Relatório de Projetos
0. Sair

## Estrutura do Projeto

- `src/main/java/br/com/gerenciadordeprojetos/domain/` — Classes de domínio: `Usuario`, `Equipe`, `Projeto`
- `src/main/java/br/com/gerenciadordeprojetos/service/` — Lógica de negócio e persistência
- `src/main/java/br/com/gerenciadordeprojetos/Main.java` — Menu principal e interação com o usuário
- `dados/` — Arquivos de dados persistentes

## Tecnologias Utilizadas

- Java 11 ou superior
- Maven

## Como Executar

1. Certifique-se de ter o Java instalado
2. Compile o projeto:
   ```bash
   mvn clean package
   ```
3. Execute o sistema:
   ```bash
   java -cp target/classes br.com.gerenciadordeprojetos.Main
   ```

## Exemplos de Uso

- Cadastre usuários, equipes e projetos pelo menu
- Vincule usuários a equipes e equipes a projetos
- Gere relatórios completos dos projetos

---

Para dúvidas, sugestões ou melhorias, entre em contato!