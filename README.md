# TP01_AEDS_3
Repositório para trabalhos da disciplina Algoritmos e Estruturas de Dados 3

--------------------------------------------------------------------------------------------------

O que o trabalho faz?

Nosso trabalho implementa um sistema de gerenciamento de séries e episódios com persistência em arquivos binários. Utilizamos técnicas de organização de arquivos e estruturas de dados indexadas, como Hash Extensível e Árvore B+, para garantir eficiência na realização de operações CRUD (Create, Read, Update, Delete).

Cada série e episódio é armazenado de forma estruturada, com operações que permitem inserir, buscar, atualizar e excluir registros de maneira eficiente. A relação entre séries e episódios é do tipo 1:N, implementada com chave estrangeira e acompanhada por uma árvore B+ que facilita o acesso aos episódios relacionados a uma série. Também nos preocupamos com integridade referencial, impedindo a exclusão de séries que ainda possuem episódios associados e restringindo a inserção de episódios apenas a séries existentes.

Participantes:

Felipe Pereira da Silva
Rikerson Antonio Freitas Silva
Maria Eduarda Pinto Martins
Kauan Gabriel Silva Pereira

Descrição das Classes e Principais Métodos:

Serie.java

Representa a entidade Série.

Métodos: toByteArray(), fromByteArray(), getID(), setID().

Episodio.java

Representa a entidade Episódio.

Métodos: toByteArray(), fromByteArray(), getID(), setID(), getSerieID(), setSerieID().

CRUD.java

Classe genérica para operações CRUD sobre arquivos.

Métodos principais: create(), read(), update(), delete().

HashExtensivel.java

Implementa a Tabela Hash Extensível usada para indexação direta (ID de séries e episódios).

Métodos principais: create(), read(), update(), delete().

ArvoreBMais.java

Implementa a Árvore B+ usada para indexação indireta (séries → episódios).

Métodos principais: create(), read(), delete(), listarPorChave().

Principal.java

Interface principal para interação com o sistema.

Contém o menu e chamadas para as funcionalidades CRUD das entidades.

Relato da Experiência:

O desenvolvimento deste TP foi desafiador, porém recompensador. Implementamos todos os requisitos especificados, o que exigiu estudo aprofundado sobre estruturas de dados como Hash Extensível e Árvores B+. Uma das partes mais complexas foi a integração da árvore B+ com a entidade Episódio, garantindo que a navegação por episódios de uma série fosse eficiente e correta.

Também foi desafiador manter a integridade entre os dados — como impedir que uma série com episódios fosse removida, ou que episódios fossem adicionados sem uma série correspondente. Esses aspectos exigiram validações específicas durante as operações CRUD.

Conseguimos alcançar todos os objetivos propostos, e o trabalho está funcionando corretamente. A implementação foi feita com foco na clareza e eficiência, e nos esforçamos para manter o código organizado e bem documentado.

Checklist
As operações de inclusão, busca, alteração e exclusão de séries estão implementadas e funcionando corretamente? Sim

As operações de inclusão, busca, alteração e exclusão de episódios, por série, estão implementadas e funcionando corretamente? Sim

Essas operações usam a classe CRUD genérica para a construção do arquivo e as classes Tabela Hash Extensível e Árvore B+ como índices diretos e indiretos? Sim

O atributo de ID de série, como chave estrangeira, foi criado na classe de episódios? Sim

Há uma árvore B+ que registre o relacionamento 1:N entre episódios e séries? Sim

Há uma visualização das séries que mostre os episódios por temporada? Sim

A remoção de séries checa se há algum episódio vinculado a ela? Sim

A inclusão da série em um episódio se limita às séries existentes? Sim

O trabalho está funcionando corretamente? Sim

O trabalho está completo? Sim

O trabalho é original e não a cópia de um trabalho de outro grupo? Sim

Obs: Se necessário, compilar com:

javac -encoding UTF-8 Principal.java
ou 
javac -d . -encoding UTF-8 aed3/*.java *.java

java Principal

--------------------------------------------------------------------------------------------------
