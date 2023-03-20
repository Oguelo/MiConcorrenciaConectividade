<h1 align="center"> Projeto Mi-Concorrencia e conectividade: Consumo de energia inteligente </h1>
##  ✔️ Técnicas e tecnologias utilizadas

- ``Java 18``
- ``Eclipse IDEA``
- ``Protocolo de rede TCP e UDP``


* [Introdução](#introdução)
* [Conceitos](#conceitos)
* [Metodologia](#metodologia)
* [Resultados](#resultados)


## Introdução
  Um serviço público importante é o fornecimento de energia elétrica, pensando nisso, foi desenvolvido um prototipo para a medição e acompanhamento do consumo de energia de maneira remota.
  Este projeto foi desenvolvido para demonstrar uma alternativa a coleta de dados, que atualmente é feita de maneira manual, mas que no futuro poderá ser feita utilizando a tecnologia IoT, 
  onde os dados dos consumidores serão captados e salvos num servidor para que fiquem disponiveis online, assim os usuarios podem monitorar seu consumo de energia, gerar fatura, e observar o consumo
  que foi feito em determinados periodos de tempo. Este projeto foi criado utilizando como base o protocolo Http e implementando uma arquitetura baseada na API REST.



## Conceitos
 - Software: Programas que permitem executar funções específicas em um computador.
 - IoT: Internet das coisas, é uma rede de dispositivos conectados à internet que permitem a coleta e troca de informações.
 - Java: é uma linguagem de programação que é amplamente utilizada para desenvolver aplicações para a Internet e também para desenvolvimento de       aplicativos móveis, sendo uma das mais usadas por desenvolvedores no mundo.
 - Protocolos de rede: Um protocolo de rede é uma série de regras que regulam a maneira como informações são trocadas entre dispositivos em uma       rede.
 - API REST: (Representational State Transfer) é um estilo de arquitetura de software para sistemas distribuídos que utiliza o protocolo HTTP para a comunicação entre diferentes sistemas. É um conjunto de padrões e princípios que definem como as requisições e respostas devem ser feitas, permitindo a integração de diferentes aplicações e sistemas. 
 ## Metodologia
    Para que este trabalho fosse concretizado, houve a necessidade de utilizar dois protocolos de redes, UDP e TCP. Na comunicação entre o medidor/servidor, foi utilizado o protocolo UDP, pois é frequentemente utilizado para aplicações que necessitam de uma transmissão de dados rápida e com baixa latência, como é o caso do envio de dados de um medidor em tempo real, porque o UDP não estabelece uma conexão antes de enviar os dados, o que o torna mais rápido e leve que outros protocolos como o TCP. Além disso, o UDP não faz retransmissão de pacotes perdidos, no caso de transmissões de dados de medidores, não há necessidade de garantir a entrega de todos os pacotes, pois os dados são constantemente atualizados e novas medições serão feitas em breve. Assim, o uso do UDP permite que a transmissão de dados ocorra com baixa latência e sem sobrecarregar a rede com retransmissões desnecessárias.
