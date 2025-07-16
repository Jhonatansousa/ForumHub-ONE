situação atual:
tenho que fazer consulta no banco de dados para pegar o uuid, transformar em formato uuid para fazer a requisição de um post



---
# pra fazer:

- fazer o handler para tratar erros, @RestControllerAdvice, etc.
- listagem (GET) de todos os topicos, preferencialmente implementar paginação de resultados
- metodo PATCH para alteração/edição do topico

### USER

- endpoint Answer, usuário/moderator/admin podem responder um post

### MODERATOR

- softdelete, endpoint onde o moderator/admin "deleta" um post que substitui o conteúdo por: [Conteúdo Removido Pelo Moderador]
- 

### ADMIN

- endpoint onde somente o admin tem acesso que irá alterar a role de um usuário para moderator ou admin
- endpoint onde o admin pode deletar uma conta
- endpoint onde o admin pode deletar um topico
- endpoint onde o admin pode deletar uma resposta


# FEITO
- fazer a rota de cadastro de novo usuário, talves devo separar os dtos, pois vai ficar muito grande - OK agoar
configurar o docker-compose e subir o container para fazer o teste dos endpoints de cadastro e login
- fazer a classe do package util que vai converter um dto para uma entidade (desacoplando do service)






---
---
---
estou fazendo esse projeto chamado forumhub, estou tentando aplicar as melhores práticas de programação usando layered architecture, até o momento fiz essas camadas(packages):
- controller -- com authController com endpoints de login e cadastro(que faz o login também retornando também o token)
- dto
-entity -- User e o enum UserRole(USER, MODERATOR e ADMIN)
- exception-- no momento só tem 1 erro custom
- facade -- uma classe que abstrai dois serviços, ele faz o cadastro e o login, uso ele no endpoint de cadastro
- handler -- package destinado para o @RestControllerAdvice, ainda não fiz por enquanto
- repository -- interfaces para persistir os dados da entidade no banco de dados
- security -- AuthFilter, AuthToken, TokenUtil(encode e decode), WebSecurityConfig
- service -- faço uso de interfaces e suas implementações, desacoplando do controller a implementação
- util -- package destinado para uma classe que vai converter o dto em entidade e vice-versa, ainda não foi realizado

até o momento só fiz isso, o que você acha que devo focar em fazer? em implementar os packages que estão faltando como o util e handler com mais exceptions customizadas? ou devo começar pensar em implementar as classes que faltam do diagrama, até o momento só fiz do usuário.