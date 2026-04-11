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

// Animaçao do card.
document.querySelectorAll('.card').forEach(card => {
  card.addEventListener('click', () => {

    document.querySelectorAll('.card').forEach(c => {
      c.classList.remove('ativo');
    });
    card.classList.add('ativo');
  });
});

// <!-- CAROUSEL FROM SLIDEJS.COM -->
var splide = new Splide( '.splide' );
splide.mount();