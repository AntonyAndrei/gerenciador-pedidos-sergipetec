# 📦 Sistema de Gestão de Pedidos (Java Web)

Aplicação desenvolvida em **Java EE** para controle completo de clientes, produtos e pedidos.
## 🛠️ Tecnologias Utilizadas
* **Linguagem:** Java (J2EE)
* **Arquitetura:** MVC (Model-View-Controller)
* **Front-end:** JSP (JavaServer Pages), CSS3 e JavaScript
* **Servidor:** Apache TomCat v11
* **Banco de Dados:** MySQL v8
* **IDE:** Eclipse for Enterprise Java and Web Developers
  
---

## ✅ Guia de Configuração do Ambiente

Siga as etapas abaixo para garantir o funcionamento correto da aplicação:

### 1. IDE Eclipse
Utilize a versão para desenvolvedores Web.
* **Download:** [Eclipse For Enterprise Java and Web Developers](https://www.eclipse.org/downloads/download.php?file=/oomph/epp/2025-12/R/eclipse-inst-jre-win64.exe)

### 2. Servidor Apache TomCat v11
* **Download:** [TomCat v11.0.18](https://dlcdn.apache.org/tomcat/tomcat-11/v11.0.18/bin/apache-tomcat-11.0.18.zip)
* **Instalação:**
  * **Instalação:** Extraia Extraia o conteúdo do arquivo `.zip` diretamente no diretório `C:\` do seu computador.
  * **Vinculação:** No Eclipse, vá em `Window -> Preferences -> Server -> Runtime Environments` e adicione o Tomcat v11 apontando para a pasta extraída.

### 3. Banco de Dados MySQL v8
* **Download:** [MySQL Installer 8.0](https://dev.mysql.com/get/Downloads/MySQLInstaller/mysql-installer-web-community-8.0.45.0.msi)
* **Configuração:** Durante a instalação, escolha o perfil *Development Computer*.
* **Conexão Padrão:**
    * **Host:** `127.0.0.1`
    * **Porta:** `3306`
    * **User:** `root`
    * **Password:** `root`
> [!IMPORTANT]
> Caso suas credenciais de banco de dados sejam diferentes, lembre-se de atualizar `url` na classe **DAO.java** (pacote model).
      
### 4. Driver do Banco de Dados
Para que a aplicação consiga se comunicar com o MySQL, é necessário o driver **MySQL Connector/J**.
* **Download:** [MySQL Connector/J 8.0.33](https://downloads.mysql.com/archives/get/p/3/file/mysql-connector-j-8.0.33.zip)
* **Instalação:** Após baixar, certifique-se de incluir o arquivo `mysql-connector-j-8.0.33` dentro da pasta `webapp/WEB-INF/lib`.

---

## 🗄️ Scripts de Configuração (SQL)
Execute os scripts do arquivo abaixo seu MySQL Workbench para criar as tabelas necessárias:

[CREATE TABLES.sql](https://github.com/user-attachments/files/25532486/CREATE.TABLES.sql)

---

## Como rodar a Aplicação
* Após toda configuração de ambiente executada, inicialize o servidor TomCat no Eclipse.
* Para acessar a interface web interativa acesse: http://localhost:8080/GerenciadorPedidos/documents

**Desenvolvido por:** Antony Andrei de Souza Santos
