## MARVEL HEROES

# Sobre o aplicativo

Este aplicativo foi desenvolvido com o objetivo de aprimorar meus conhecimentos em arquitetura
Android e boas práticas de desenvolvimento. O app consome a API da Marvel, exibindo informações
sobre os heróis em uma lista com suas respectivas fotos.

## Funcionalidades

* **Lista de Heróis:** Apresenta uma lista com os heróis da Marvel, incluindo suas fotos.
* **Detalhes do Herói:** Ao clicar na imagem de um herói, uma nova tela é aberta com informações
  detalhadas sobre ele.
* **Favoritos:** Permite favoritar heróis para acesso rápido na aba de favoritos.
* **Pesquisa:** Possibilita a busca de heróis pelo nome.
* **Modo Offline:** Após o primeiro carregamento com internet, os dados dos heróis e favoritos são
  armazenados localmente utilizando a biblioteca Room em conjunto com o SQLite, permitindo o acesso
  offline ao conteúdo.
* **CI/CD:** GitHub Actions para integração contínua e maior segurança do app.
* **Offline First:** Utiliza o conceito de Offline First, permitindo que muitas ações do app sejam
  realizadas de forma offline, sem a necessidade de consumo de internet do dispositivo do usuário.

## Tecnologias Utilizadas

* **Kotlin**
* **Injeção de Dependências:** Utilizando Koin Annotations.
* **Persistência de Dados:** Acesso offline usando Room + SQLite.
* **Comunicação com API:** Retrofit para consultas à API.
* **Arquitetura:** MVVM (Model-View-ViewModel).
* **Testes:** Testes unitários e instrumentados.
* **Gestão de Dependências:** Utilização de catálogo de versões.
* **Offline First:** [Documentação Oficial](https://developer.android.com/topic/architecture/data-layer/offline-first)

## Motivação do Projeto

A criação deste projeto foi motivada pelo meu interesse em aprofundar meus conhecimentos em
desenvolvimento Android, explorando as melhores práticas de arquitetura e otimizando a experiência
do usuário com recursos como o modo offline.

## Explicação da Funcionalidade Offline First

O conceito de **Offline First** visa proporcionar uma experiência de usuário contínua e eficiente,
mesmo na ausência de conexão com a internet. No contexto do nosso aplicativo Marvel Heroes, a
implementação do Offline First é feita através das seguintes práticas:

1. **Armazenamento Local de Dados:** Após a primeira sincronização com a API da Marvel, os dados dos
   heróis são armazenados localmente utilizando Room + SQLite. Isso permite que o usuário acesse as
   informações dos heróis mesmo sem conexão com a internet.

2. **Sincronização Inteligente:** O aplicativo verifica a disponibilidade de conexão com a internet
   e, se disponível, sincroniza os dados locais com a API da Marvel para garantir que as informações
   estejam sempre atualizadas.

3. **Experiência de Usuário Consistente:** A aplicação é desenhada para operar de forma
   transparente, de modo que a transição entre os estados online e offline seja imperceptível para o
   usuário. Isso inclui a capacidade de adicionar heróis aos favoritos, realizar buscas e visualizar
   detalhes dos heróis mesmo quando offline.

4. **Fallback e Cache:** Em caso de falha de conexão durante uma operação, o aplicativo utiliza
   dados previamente cacheados para fornecer uma resposta imediata ao usuário, evitando a
   interrupção do fluxo de uso.

Para mais detalhes sobre a abordagem Offline First, você pode consultar
a [documentação oficial](https://developer.android.com/topic/architecture/data-layer/offline-first).

## Contribuições

Caso deseje contribuir com o projeto, sinta-se à vontade para abrir issues ou enviar pull requests
com sugestões de melhorias ou correções de bugs.

## Screenshots do Aplicativo

<img src="/home/madara/Área de Trabalho/Projetos/marvel-heroes-clean-architeture/imgs/first.jpeg" title="Tela inicial do app" width="200"/>
<img src="/home/madara/Área de Trabalho/Projetos/marvel-heroes-clean-architeture/imgs/second.jpeg" title="Filtrando heróis por nome" width="200"/>
<img src="/home/madara/Área de Trabalho/Projetos/marvel-heroes-clean-architeture/imgs/third.jpeg" title="Detalhes do herói selecionado" width="200"/>
<img src="/home/madara/Área de Trabalho/Projetos/marvel-heroes-clean-architeture/imgs/four.jpeg" title="Tela de heróis favoritados" width="200"/>

---