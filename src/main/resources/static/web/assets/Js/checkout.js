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
            cantidad:[],
            errorEncontrado: false,
            registrado: false,
            nombre: "",
            apellido: "",
            email: "",
            contra: "",
            direccion: "",
            telefono: "",
            numTarjeta:undefined,
            saldo:undefined,
            emailInicioSesion: undefined,
            contraInicioSesion: undefined,
            productos:[],
            servicios:[],
            productosNombre:"",
            servicioNombre:"",
            servicio:"",
            sesion: "0"

        }
    },


    created(){
        this.sesion = localStorage.getItem("sesion")
        if(this.sesion == "1"){
            this.cargarDatosCliente()
        }
        this.guardarLocalStorage()
        this.cargarDatos()
        this.cargarDatosServicios()

    },

    mounted(){
        this.compra = JSON.parse(localStorage.getItem("compra"))
    },

    methods:{
        
        cargarDatosCliente: function(){
            axios.get('/api/cliente')
                .then(respuesta => {
                    this.cliente = respuesta.data;
                    console.log(this.cliente)
                    this.direccion = this.cliente.direccion
                    this.telefono = this.cliente.telefono
                    this.numTarjeta = this.cliente.cuenta.tarjetaAd.numeroTarjeta
                    this.saldo = this.cliente.cuenta.saldo
                })
                .catch(err => console.error(err.message));
        },
    
        cargarDatos: function(){
            axios.get('/api/productos')
                .then(respuesta => {
                    this.productos = respuesta.data.map(producto => ({... producto}));
                    this.productoNombre=this.productos.filter(producto => producto.nombre)
                    this.productosFiltrados = respuesta.data;
                    this.categorias =[... new Set(this.productos.map(producto => producto.categoria))];
                })
        },
        cargarDatosServicios: function(){
            axios.get('/api/servicios')
                .then(respuesta => {
                    this.servicios = respuesta.data.map(servicio => ({... servicio}));
                    this.servicioNombre=this.servicios.filter(servicio => servicio.nombre)
                })
        },

        

        guardarLocalStorage(){
            if(localStorage.getItem("compra") == null){
                localStorage.setItem("compra", JSON.stringify(this.compra))
            }
        },

         //Generar registro
        realizarRegistro: function(){
            axios.post('/api/registrar', {nombre: this.nombre, apellido: this.apellido, email: this.email, claveIngreso: this.contra, direccion: this.direccion, telefono: this.telefono,})
                .then(response => {
                    console.log('registrado');

                    this.emailInicioSesion = this.email;
                    this.contraInicioSesion = this.contra;

                    this.errorEncontrado = false;
                    this.nombre = "";
                    this.apellido = "";
                    this.email = "";
                    this.contra = "";
                    this.direccion = "",
                    this.registro = "",

                    this.iniciarSesion();
                })
                .catch(err => {
                    this.errorEncontrado = true;
                    console.error([err]);
                    let spanError = document.querySelector('.mensaje-error-registro');
                    spanError.innerHTML = err.response.data;
                    
                    if(err.response.data.includes('Email ya registrado')){
                        this.email = "";
                        this.contra = "";
                    }                    
                })
            
        },

        iniciarSesion: function(){
            axios.post('/api/login',`email=${this.emailInicioSesion}&claveIngreso=${this.contraInicioSesion}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response => {
                    console.log('inicio sesion!');
                    this.sesion = "1"
                    localStorage.setItem("sesion", this.sesion)
                    this.cargarDatos();
                })
                .catch(err => {
                    console.error(err.message);
                    console.error(err.response);
                    this.errorEncontrado = true;
                });
        },
        logOut(){
            axios.post('/api/logout')
            .then(() => {
                this.sesion = JSON.stringify(localStorage.getItem("sesion"))
                this.sesion = "0"
                localStorage.setItem("sesion", this.sesion)
                window.location.reload
            })
        },
        verificar(){
            
        },


        loginRegistro: function (value) {
            let form = document.querySelector('.card-3d-wrapper');
            if (value == 'registro') {
                form.classList.add('girarLogin');
            }
            else if (value == 'login') {
                form.classList.remove('girarLogin');
            }
        },

    },

}).mount("#app")