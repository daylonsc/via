# Teste para desenvolvedor Backend via varejo - Daylon Costa
criar um endpoint para que possamos simular a compra de um produto, deve retornar uma lista de parcelas, 
acrescidas de juros com base na taxa SELIC de 1.15% ao mês (se possível consultar a taxa em tempo real), 
somente quando o número de parcelas for superior a 06 (seis) parcelas.
# Tecnologias utilizadas no projeto
<ul>
<ol>- IDE Intellij;</ol>
<ol>- Dependências do spring;</ol>
<ol>- JUnit + Mockito;</ol>
<ol>- OpenFeign;</ol>
<ol>- Swagger;</ol>
<ol>- Postman para fazer as requisições;</ol>
</ul>

# Passos para subir o projeto
<ul>
<ol>1 - Fazer o clone no github: https://github.com/daylonsc/via.git</ol>
<ol>2 - Importar no intellij ou eclipse;</ol>
<ol>3 - Baixar as dependências do maven;</ol>
<ol>4 - Executar o projeto</ol>
</ul>

# Como testar o endpoint
<ul>
<ol>1 - Utilizar a url no postman: localhost:8080/parcelas</ol>
<ol>2 - Enviar dois objetos: Produto e condição de pagamento com método HTTP POST</ol>
<ol>3 - Enviar no body { "produto": { "codigo": 123, "nome": "Nome do Produto", "valor": 9999.99 }, "condicaoPagamento": { "valorEntrada": 9999.99, "qtdeParcelas": 999 } }</ol>
</ul>



