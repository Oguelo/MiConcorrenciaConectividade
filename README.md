<h1 align="center"> Projeto Mi-Concorrencia e conectividade: Consumo de energia inteligente </h1>

##  ✔️ Técnicas e tecnologias utilizadas

- ``Java 18``
- ``Eclipse IDEA``
- ``Protocolo de rede TCP e UDP``


* [Introdução](#introdução)
* [Conceitos](#conceitos)
* [Instalação do projeto](#instalação)
* [Metodologia](#metodologia)
* [Resultados](#resultados)


# Introdução
Um serviço público importante é o fornecimento de energia elétrica, pensando nisso, foi desenvolvido um protótipo para a medição e acompanhamento do consumo de energia de maneira remota. Este projeto foi desenvolvido para demonstrar uma alternativa a coleta de dados, que atualmente é feita de maneira manual, mas que no futuro poderá ser feita utilizando a tecnologia IoT, onde os dados dos consumidores serão captados e salvos num servidor para que fiquem disponíveis online, assim os usuários podem monitorar seu consumo de energia, gerar fatura, e observar o consumo que foi feito em determinados períodos de tempo. Este projeto foi criado utilizando como base o protocolo Http e implementando uma arquitetura baseada na API REST.

---

# Conceitos
- Software: Programas que permitem executar funções específicas em um computador.
- IoT: Internet das coisas, é uma rede de dispositivos conectados à internet que permitem a coleta e troca de informações.
- Java: é uma linguagem de programação que é amplamente utilizada para desenvolver aplicações para a Internet e também para desenvolvimento de aplicativos móveis, sendo uma das mais usadas por desenvolvedores no mundo.
- Protocolos de rede: Um protocolo de rede é uma série de regras que regulam a maneira como informações são trocadas entre dispositivos em uma rede.
- API REST: (Representational State Transfer) é um estilo de arquitetura de software para sistemas distribuídos que utiliza o protocolo HTTP para a comunicação entre diferentes sistemas. É um conjunto de padrões e princípios que definem como as requisições e respostas devem ser feitas, permitindo a integração de diferentes aplicações e sistemas.
- UDP: UDP é um protocolo de comunicação de rede que permite a transmissão rápida de dados sem estabelecer uma conexão prévia e sem garantir a confiabilidade dos dados transmitidos.
- TCP: TCP é um protocolo de comunicação de rede que permite uma transmissão de dados mais confiavel, pois estabelece uma conexão entre dois dispositivos e verifica se os dados enviados foram entregues corretamente.

---
# Instalação do projeto
    ## Executando o arquivo jar do servidor:
         java -jar HttpSoc-jar-with-dependencies.jar
        
        
    ## Executando o arquivo jar do medidor:
        java -jar StartMedidor-jar-with-dependencies.jar
       
---

# Metodologia
    
- Para que este trabalho fosse concretizado, houve a necessidade de utilizar dois protocolos de redes, UDP e TCP. Na comunicação entre medidor/servidor, foi utilizado o protocolo UDP, pois é frequentemente utilizado para aplicações que necessitam de uma transmissão de dados rápida e com baixa latência, como é o caso do envio de dados de um medidor em tempo real, porque o UDP não estabelece uma conexão antes de enviar os dados, o que o torna mais rápido e leve que outros protocolos como o TCP. Além disso, o UDP não faz retransmissão de pacotes perdidos, no caso de transmissões de dados de medidores, não há necessidade de garantir a entrega de todos os pacotes, pois os dados são constantemente atualizados e novas medições serão feitas em breve. Assim, o uso do UDP permite que a transmissão de dados ocorra com baixa latência e sem sobrecarregar a rede com retransmissões desnecessárias, no caso do pbl utilizei a porta 8923 para enviar os dados por meio de uma string que continha todas as informações necessarias como id do medidor, data da medição, medição em khw e uma flag que iria informar o mes da medição.
- Para a construção da comunicação Servidor/Cliente, foi usado o protocolo TCP que é comumente usado em processos que necessitam de confiabilidade, como é o caso da passagens de transmissão de dados que seria feita para o usuario após a solicitação, utilizando requisições do tipo HTTP no padrão da API REST, essa comunicação foi feita utilizando metodos "GET",assim utilizando o Software insomnia, pude fazer a comunicação com o servidor e gerar as requisições que seriam o historico(que engloba, o acompanhamento das medições e o aviso de consumo excessivo) e fatura do usuario por meio da passagem de um link com o ip, porta(8922) e endpoints.
    ## Exemplo do link utilizado na requisição
        http://172.16.103.3:8922/generateinvoice/U40028924
    ## Exemplo de resposta dessa requisição
        Content-Type:text/plain
        Content-Length: 110

        ID:U40028924
        -Mes:1 Fatura(R$):24.00
        -Mes:2 Fatura(R$):20.00
        Seu Consumo Atual esta controlado, continue assim
    ## Analise feita
* Os endpoints das requisições seriam analisados após o recebimento do servidor, seriam necessariamente a opção desejada pelo cliente e seu ID, essa  passagem de informações é dividida em Strings onde cada uma é analisada separadamente para encontrar o metodo e conferir as informações do cliente, assim a API serviu na analise e validação das requisições para após isso a busca de respostas no metodos

    ## Multiplos clientes
* Para que a aplicação possibilitasse o uso de multiplos clientes e multiplos medidores ativos, foi necessario a implementação do uso de threads, elas foram uteis em alguns pontos do codigo fonte, dentre eles:
* - Medição continua do consumo de energia (medidor): onde essa thread fica responsavel por atualizar e enviar as medições para a classe que formata e destina os dados para o servidor quando solicitado, assim existe a possibilidade da escalação para outros cenarios.
* - Envio dos dados medidos do medidor para o servidor(medidor): essa thread trabalha nos dados que serão enviados para o servidor, num periodo de 1 min(do qual pode ser alterado) ela envia dados que equivalem a um mes em um cenario ficticio, assim ela monta uma string com os dados que serão enviados, dentre eles, data, medição, id, e flag e a cada 10 seg envia dados ao servidor e apos 1 min essa thread "troca" o mês.
* - Possibilitar a conexão de multiplos clientes e medidores(servidor): Essas threads ficaram responsaveis por receberem requisições de clientes e medidores e trata-las no servidor a thread UDP permanece aberta para o medidor por conta das transmissões continuas e a de TCP fecha após cada solicitação. O medidor ira mandar os dados supracitados e o servidor ira checa-los por meio de um parser, e assim ira adicionar medições no servidor e ele ira salvar nas listas para que seja solicitado pelo cliente em algum momento.

---
    ## Cliente
* O cliente será representado pelo insomnia, o software enviara as requisições, das quais é possivel acompanhar o consumo por meio da busca pelo historico,onde é possivel ver o consumo unitario, o consumo total, e ser alertado se o consumo esta alto, e na geração de fatura é possivel ver as faturas geradas a cada mês e o seu valor
# Resultados 
* O projeto esta funcional, ele cumpre todos os requisitos que foram expressos no barema, mas que podem ser otimizados, o medidor e servidor podem ser atualizados para receber mensagem e requisições no padrão json por ser comumente usado em varias outras linguagens, e o medidor pode ser editado para caso seja usado em um cenario mais realista, onde o tempo de envio deve ser mais curto para que não haja sobrecarga de um processador que deve ser mais simples, e para que os dados que possam ser perdidos não tenham tanta influencia na medição final, e tambem uma interface de cliente é um bom substituto do insomnia



---
