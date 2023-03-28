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
        cargarDatos: function(){
            axios.get('/api/clientes')
                .then(respuesta => {
                    
                })
        },


    },

}).mount("#app")