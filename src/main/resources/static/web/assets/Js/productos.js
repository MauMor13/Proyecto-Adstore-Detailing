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

        verDetallesProducto: function (value) {
            let form = document.querySelector('.flip-card .flip-card-inner');
            if (value == 'front') {
                form.classList.add('girarproducto');
            }
            else if (value == 'back') {
                form.classList.remove('girarproducto');
            }
        }
    },

}).mount("#app")