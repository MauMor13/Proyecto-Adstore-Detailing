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
            compra:{
                productos:[],
                servicios:[]
            },
            cantidad:[]
        }
    },


    created(){
        this.cargarDatos();
        this.guardarLocalStorage();
        // this.cargarDatosCliente();
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
        
        busquedaCruzada: function(){
            let filtroInput = this.productos.filter( producto => producto.nombre.toLowerCase().includes( this.inputBusqueda.toLowerCase()))
            if( this.checked.length === 0 ){
                this.productosFiltrados = filtroInput
            }else{
                let filtroCheck = filtroInput.filter( categoria => this.checked.includes( categoria.categoria ))
                this.productosFiltrados = filtroCheck 
        } 
        },
        guardarLocalStorage(){
            if(localStorage.getItem("compra") == null){
                localStorage.setItem("compra", JSON.stringify(this.compra))
            }
            console.log(this.compra)
        },
        agregarACarrito(idSeleccion, cantidad){
            this.compra = JSON.parse(localStorage.getItem("compra"))
            let productoEnCarro = this.compra.productos.find(element => element.id == idSeleccion)
            if(productoEnCarro != null){
                productoEnCarro.cantidad += cantidad
            }
            else{
                let objeto = {id: 0, cantidad: 0};
                objeto.id = idSeleccion
                objeto.cantidad = cantidad
                this.compra.productos.push(objeto)
            }
            localStorage.setItem("compra",JSON.stringify(this.compra))
        }
    },

}).mount("#app")