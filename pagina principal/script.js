// FAZ O MENU DO USUÁRIO 
function toggleMenu() {
    const menu = document.getElementById("user_menu");
    menu.classList.toggle("hidden");
}

window.onclick = function(event) {
    if (!event.target.closest('.user-container')) {
        const menu = document.getElementById("user_menu");
        if (!menu.classList.contains('hidden')) {
            menu.classList.add('hidden');
        }
    }
}


// <!-- CAROUSEL FROM SLIDEJS.COM -->
var splide = new Splide( '.splide' );
splide.mount();