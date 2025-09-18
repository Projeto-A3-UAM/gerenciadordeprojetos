# Gerenciador de Projetos

## Sobre o Projeto

Este sistema foi desenvolvido para ajudar na gestão de projetos e equipes, permitindo o cadastro e acompanhamento de usuários, projetos, equipes e a vinculação entre eles. O objetivo é facilitar o controle e a organização de atividades em ambientes acadêmicos ou profissionais.


## Funcionalidades

- **Cadastro de Usuários:**
	- Permite registrar pessoas que irão utilizar o sistema, informando nome completo, CPF, e-mail, cargo, login, senha e perfil (administrador, gerente ou colaborador).
	- Os dados dos usuários são salvos automaticamente em arquivo texto e carregados ao iniciar o sistema.

- **Cadastro de Projetos:**
	- Permite criar projetos, informando nome, descrição, data de início, data de término prevista, status (planejado, em andamento, concluído, cancelado) e gerente responsável.
	- Os projetos são salvos em arquivo texto, incluindo os vínculos com equipes e gerente.

- **Cadastro de Equipes:**
	- Permite criar equipes, informando nome, descrição e membros (usuários vinculados).
	- As equipes e seus membros são salvos em arquivo texto e carregados automaticamente.

- **Vincular Equipes a Projetos:**
	- Permite associar equipes já cadastradas aos projetos existentes. Os vínculos são persistidos e restaurados ao iniciar o sistema.

- **Vincular Usuários a Equipes:**
	- Permite associar usuários já cadastrados como membros das equipes. Os vínculos são persistidos e restaurados ao iniciar o sistema.

- **Sincronização Automática:**
	- Ao iniciar o sistema, todos os vínculos entre usuários, equipes e projetos são sincronizados automaticamente, garantindo que os dados exibidos estejam completos.

- **Editar e Excluir Registros:**
	- Possibilita alterar ou remover usuários, projetos e equipes, com atualização automática dos arquivos.

- **Relatório de Projetos:**
	- Gera um relatório mostrando os projetos cadastrados, seus status, gerente responsável e equipes vinculadas.

## Como Usar

O sistema funciona via menu no console. Basta executar o programa principal (`Main.java`) e seguir as opções apresentadas:

1. **Cadastrar Usuário:**  
	Informe os dados solicitados para criar um novo usuário.

2. **Listar Usuários:**  
	Exibe todos os usuários cadastrados.

3. **Cadastrar Projeto:**  
	Informe os dados do projeto e o login do gerente responsável.

4. **Listar Projetos:**  
	Exibe todos os projetos cadastrados.

5. **Cadastrar Equipe:**  
	Informe os dados da equipe.

6. **Listar Equipes:**  
	Exibe todas as equipes cadastradas.

7. **Vincular Equipe a Projeto:**  
	Escolha um projeto e uma equipe para realizar a vinculação.

8. **Editar/Excluir Usuário, Projeto ou Equipe:**  
	Permite alterar ou remover registros existentes.

9. **Relatório de Projetos:**  
	Exibe um resumo dos projetos, mostrando status, gerente e equipes vinculadas.

0. **Sair:**  
	Encerra o sistema.

## Estrutura do Projeto

- `src/main/java/br/com/gerenciadordeprojetos/domain/`  
  Contém as classes principais: `Usuario`, `Projeto`, `Equipe`.

- `src/main/java/br/com/gerenciadordeprojetos/service/`  
  Contém as classes de serviço responsáveis pela lógica de cadastro, edição, exclusão e busca.

- `src/main/java/br/com/gerenciadordeprojetos/Main.java`  
  Contém o menu principal e a interação com o usuário.

## Tecnologias Utilizadas

- **Java 17**
- **Maven** (para organização e dependências)


## Observações

- Os dados são armazenados em arquivos texto na subpasta `dados/`, permitindo que os cadastros e vínculos sejam mantidos entre execuções.
- O sistema é simples e ideal para aprendizado de lógica de programação, orientação a objetos, manipulação de listas e persistência básica.
- Pode ser expandido para incluir cadastro de tarefas, persistência em banco de dados, e interface gráfica.


## Como Executar

1. Certifique-se de ter o Java instalado.
2. Compile o projeto usando o Maven:
	```bash
	mvn clean package
	```
3. Execute o sistema:
	```bash
	java -cp target/gerenciadordeprojetos-1.0-SNAPSHOT.jar br.com.gerenciadordeprojetos.Main
	```
4. O menu será exibido no terminal e você poderá interagir normalmente.

---


## Exemplos de Uso

- Cadastre usuários, equipes e projetos normalmente pelo menu.
- Os dados serão mantidos mesmo após fechar e abrir o sistema.
- Vincule equipes a projetos e usuários a equipes, e veja os vínculos refletidos nos relatórios.

---

Se quiser incluir mais exemplos, expandir funcionalidades ou tirar dúvidas, fique à vontade para pedir!