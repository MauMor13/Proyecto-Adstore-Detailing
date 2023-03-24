const {createApp} = Vue;

createApp({
    
    data(){
        return{

        }
    },

    created(){

    },

    mounted(){
        window.addEventListener('scroll', this.scrollFunction);
    },

    methods: {

        scrollFunction() {

            let nav = document.querySelector(".nav-landing-page");
            let fotos = document.querySelector(".fotos-landing");
            let icono = document.querySelector(".icono-landing");
            let divNavLista = document.querySelector(".collapse-nav-lista")


            if (document.body.scrollTop > fotos.clientHeight - nav.clientHeight / 2 || document.documentElement.scrollTop > fotos.clientHeight - nav.clientHeight / 2|| window.pageYOffset > fotos.clientHeight - nav.clientHeight / 2) {
                nav.style.backgroundColor = "black";  
                divNavLista.style.backgroundColor = "black";
            } else {
                nav.style.backgroundColor = "transparent";
                divNavLista.style.backgroundColor = "transparent";
            }

            if(icono.scrollTop == nav.scrollTop){
                icono.position = "static";
            }
        },


    },

    
}).mount("#app")