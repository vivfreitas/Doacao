##  🌟 Projeto: Amor de Pata

Amor de Pata é um projeto full stack cujo objetivo é conectar pessoas interessadas em adotar animais e permitir a realização de doações.

### 📌 Funcionalidades

* Cadastro e autenticação de usuários
* Login  com Spring Security
* Visualização de animais disponíveis para adoção
* Cadastro de animais para adoção
* Realização de doações

**Interface web simples e responsiva**
### Tecnologias útilizadas:
1. [x] Java 21.
2. [x] Springboot.
3. [x] SpringSecurity.
4. [x] MySQL.
5. [x] HTML.
6. [x] CSS.
7. [x] JavaScript.
8. [x] Docker.

**O projeto segue uma arquitetura cliente-servidor:**
1. [x] **Front-end:** Interface para o usuário
2. [x] **Back-end:** API REST responsável pelas regras de negócio, autenticação e persistência de dados
****

## UML do projeto
[AmorDePata.pdf](../../PDFs/AmorDePata.pdf)
### Page cadastro e método POST

**Front-end**:
A página de cadastro permite que o usuário informe seus dados pessoais para criar uma conta e, posteriormente, acessar a tela de login. Caso já tenha o cadastro, ela permite que o usuário vá diretamente para a tela de login.

**Fluxo:**

* Usuário preenche o formulário de cadastro 
* Os dados são enviados via método POST para a API 
* O back-end valida e persiste os dados no banco de dados 
* O usuário passa a ter acesso ao sistema
### Layout
![img.png](img.png)

**Endpoint de Cadastro:** `/api/createUser`

**JSON**:
```json
{
"nameUser": "Roberto",
"emailUser": "Roberto123@gmail.com",
"passwordUser": "123456"
}
```

As senhas são salvas em **hash** para proteção do usuário. Exemplo de retorno ao usuário ser salvo:

```json
{
"idUser": 4,
"nameUser": "Roberto",
"emailUser": "roberto@gmail.com",
"passwordUser": "$2a$10$7Rc/hKWUARPp6B/k.rvso.tiuLa4XHT4/DKVgrM3w2CtVZQhS25x2",
"listAnimal": null,
"username": "roberto@gmail.com",
"authorities": [],
"password": "$2a$10$7Rc/hKWUARPp6B/k.rvso.tiuLa4XHT4/DKVgrM3w2CtVZQhS25x2",
"enabled": true,
"accountNonExpired": true,
"accountNonLocked": true,
"credentialsNonExpired": true
```