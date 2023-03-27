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
        
    },

    mounted(){

    },

    methods:{

        cargarDatos: function(){
            axios.get('')
                .then(respuesta => {
                    this.productos = respuesta.data.map(producto => ({... producto}));
                    this.categorias =[... new Set(this.productos.map(producto => producto.categoria))];
                    
                })
        }
    },

}).mount("#app")