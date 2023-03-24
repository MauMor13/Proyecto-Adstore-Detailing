const {createApp} = Vue;

createApp({
    
    data(){
        return{

        }
    },

    created(){

    },

    methods: {

        scrollFunction() {

            let nav = document.querySelector(".nav-landing-page");
            let fotos = document.querySelector(".fotos-landing");
            let icono = document.querySelector(".icono-landing");

            if (document.body.scrollTop > nav.height + fotos.height || document.documentElement.scrollTop > nav.height + fotos.height || window.pageYOffset > nav.height + fotos.height) {
                nav.style.backgroundColor = "black";  
            } else {
                nav.style.backgroundColor = "transparent";
            }

            if(icono.scrollTop == nav.scrollTop){
                icono.position = inline;
            }
        },


    },

    
})