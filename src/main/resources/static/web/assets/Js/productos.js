const {createApp} = Vue;

createApp({
    data(){
        return{
            client: undefined,
            categorias: [],
            productos: [],
            productosFiltrados: [],

        }
    },

    created(){
        this.cargarDatos();
    },

    mounted(){

    },

    methods:{

        cargarDatos: function(){
            axios.get('/api/productos')
                .then(respuesta => {
                    this.productos = respuesta.data.map(producto => ({... producto}));
                    this.categorias =[... new Set(this.productos.map(producto => producto.categoria))];
                    console.log(this.productos);
                })
        },

        verDetallesProducto: function (id) {
            let foto = document.getElementById(id + "Foto");
            let telon = document.getElementById(id + "Telon");
            let desc = document.getElementById(id + "Desc");
            let boton = document.getElementById(id + "Bot");

            if(boton.textContent.includes("Ver detalles")){

                foto.style.scale = "0.6"
                foto.style.translate = "0 -20%"
                telon.style.translate = "0 0"
                desc.style.translate = "0 -5rem"
                desc.style.opacity = "1"
                desc.style.zIndex = "1"
                boton.innerText = "Volver"
                boton.style.color = "white"
            }
            else{

                foto.style.scale = "1"
                foto.style.translate = "0 0"
                telon.style.translate = "0 100%"
                desc.style.translate = "0 0"
                desc.style.opacity = "0"
                desc.style.zIndex = "0"
                boton.innerText = "Ver detalles"
                boton.style.color = "black"
            }
            
        },




    },

}).mount("#app")