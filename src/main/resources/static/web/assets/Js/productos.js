const {createApp} = Vue;

createApp({
    data(){
        return{
            cliente: undefined,
            categorias: [],
            productos: [],
            productosFiltrados: [],
            checked:[],
            inputBusqueda:"",

        }
    },

    created(){
        this.cargarDatos();
        this.cargarDatosCliente();
    },

    mounted(){

    },

    methods:{
        cargarDatosCliente: function(){
            axios.get('/api/cliente')
                .then(respuesta => {
                    this.cliente = respuesta.data;
                })
                .catch(err => console.error(err.message));
        },

        cargarDatos: function(){
            axios.get('/api/productos')
                .then(respuesta => {
                    this.productos = respuesta.data.map(producto => ({... producto}));
                    this.productosFiltrados = respuesta.data;
                    this.categorias =[... new Set(this.productos.map(producto => producto.categoria))];
                    console.log(this.productos);
                    console.log(this.categorias);
                })
        },
        busquedaCruzada: function(){
            let filtroInput = this.productos.filter( producto => producto.nombre.toLowerCase().includes( this.inputBusqueda.toLowerCase()))
            if( this.checked.length === 0 ){
                this.productosFiltrados = filterBySearch
            }else{
                let filtroCheck = filtroInput.filter( categoria => this.checked.includes( categoria.categoria ))
                this.productosFiltrados = filtroCheck 
        } 
        }


    },

}).mount("#app")