const id_name = document.querySelector("#id-name");
const id_email = document.querySelector("#id-email");
const id_senha_principal = document.querySelector("#id-senha-principal");
const id_senha_confirmada = document.querySelector("#id-senha-confirmada");
const bnt_form_cadastro = document.querySelector("#bnt-form");
const bnt_form_login = document.querySelector("#bnt-login");
const form = document.querySelector("#form");
const erro_senha_email = document.querySelector("#erro-senha-email");
const icon_email = document.querySelector(".bi-envelope-at");
const icon_senha1 = document.querySelector("#icon-senha1");
const icon_senha2 = document.querySelector("#icon-senha2");

// ANIMAÇÃO DO ANIMATE.CSS
function animacao_Input(elemento){
    elemento.classList.add('animate__animated', 'animate__shakeX');
    elemento.style.setProperty('--animate-duration', '1s');
    elemento.style.borderColor = "red";

    elemento.addEventListener('animationend', () => {
        elemento.classList.remove('animate__animated', 'animate__shakeX');
        elemento.style.borderColor = "#EA9901";
    }, { once: true });
}
// PARA ICON
function animacao_icones(elemento){
    elemento.classList.add('animate__animated', 'animate__shakeX');
    elemento.style.setProperty('--animate-duration', '1s');
    elemento.style.color = "red";
    
    elemento.addEventListener('animationend', () => {
        elemento.classList.remove('animate__animated', 'animate__shakeX');
        elemento.style.color = "#EA9901";
  }, { once: true });
}

// ================== BOTÃO PARA ENVIAR AS INFORMAÇÕES DO USUÁRIO ==============
bnt_form_cadastro.addEventListener('click', async function name(event) {

    id_name.setAttribute('required', 'true');
    id_email.setAttribute('required', 'true');
    id_senha_principal.setAttribute('required', 'true');
    id_senha_confirmada.setAttribute('required', 'true');
    event.preventDefault();

    // CONFERIR SE AS SENHAS SÃO IGUAIS ==================================
    let senha_usuario = "";
    if(id_senha_principal.value == id_senha_confirmada.value){
        senha_usuario = id_senha_principal.value;
    }else{

        // =======================================================================
        erro_senha_email.style.visibility = "visible";
        animacao_Input(id_senha_principal);
        animacao_Input(id_senha_confirmada);
        animacao_icones(icon_senha1);
        animacao_icones(icon_senha2);
        id_senha_principal.addEventListener('click', function(){
            erro_senha_email.style.visibility = "hidden";
        })
        id_senha_confirmada.addEventListener('click', function(){
            erro_senha_email.style.visibility = "hidden";
        })

        return;
    }

    // ENVIAR OBJETO PARA O BACK-END - TELA DE CADASTRO =========================================
    if(form.reportValidity()){
        const objeto = {
            userName: id_name.value,
            userEmail: id_email.value,
            userPassword: senha_usuario
        };

        console.log(objeto.passwordUser);
        try{
            const fetch_url = await fetch("http://localhost:8080/api/user/createUser", {
                method: 'POST',
                headers:{
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(objeto)
            });

            if(fetch_url.ok){
                alert("Usuário criado com sucesso! Redirecionando a tela de login....");
                window.location.href = "/login/index.html";
            
            }else if (fetch_url.status === 409) {

                // INPUT
                animacao_Input(id_email);
                // ÍCONE 
                animacao_icones(icon_email);
                
                // ==================================================================
                erro_senha_email.style.visibility = "visible";
                erro_senha_email.innerHTML = "E-mail usado por outro usuário!";
                id_email.addEventListener('click', function(){
                erro_senha_email.style.visibility = "hidden";
                })
            }
        }catch(error){
            console.log("error" + error);
        }
    }
})

