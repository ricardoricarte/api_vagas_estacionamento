# vaga-estacionamento
Api de vagas de Estacionamento de Condominio

# Dependendencias usadas

- MySQL
- JPA
- Validation
- Dev tools
- Web
- Java 11 
- Spring - Version - 2.7.5

# Parâmetros para testar - Postaman - Fort Cliente(Vscode)

{
  "numeroPlaca": "205B",
  "placaCarro": "RRS8562",
  "marcaCarro": "audi",
  "modeloCarro": "q5",
  "corCarro": "black",
  "nomeResponsavel": "José Ricardo",
  "apartamento": "205",
  "bloco": "R"
}

# Banco de dados MYSQL

* Configuracão

# Altera a estrutura da tabela caso a entidade tenha mudanças.
spring.jpa.hibernate.ddl-auto=update

# Acesso ao banco de dados
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/vaga_estacionamento

# Usuário do banco de dados
spring.datasource.username=root

# Senha do banco de dados
spring.datasource.password=banco123
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true

Comando para rodar projet 

mvn install -g
mvn spring-boot:run

