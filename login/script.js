const id_email = document.querySelector("#id-email");
const id_senha_principal = document.querySelector("#id-senha-principal");
const bnt_form_login = document.querySelector("#bnt-login");
const form = document.querySelector("#form");
const erro_senha_email = document.querySelector("#erro-senha-email");
const icon_email = document.querySelector(".bi-envelope-at");
const icon_senha1 = document.querySelector("#icon-senha1");

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
bnt_form_login.addEventListener('click', async function name(event) {
    console.log("Login")
    id_email.setAttribute('required', 'true');
    id_senha_principal.setAttribute('required', 'true');
    event.preventDefault();

    // ENVIAR OBJETO PARA O BACK-END - TELA DE CADASTRO =========================================
    if(form.reportValidity()){
        const objeto = {
            userEmail: id_email.value,
            userPassword: senha_usuario
        };

        console.log(objeto.passwordUser);
        try{
            const fetch_url = await fetch("http://localhost:8080/auth/login", {
                method: 'POST',
                headers:{
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(objeto)
            });

            if(fetch_url.ok){
                alert("Login realizado com sucesso...");
               // window.location.href = "/login/index.html";
               localStorage.setItem("token", data.token);
            
            }else if (fetch_url.status === 409) {
                alert("Falha ao realizar o login!")
                // INPUT
                animacao_Input(id_email);
                // ÍCONE 
                animacao_icones(icon_email);
                
            }
        }catch(error){
            console.log("error" + error);
        }
    }
})

