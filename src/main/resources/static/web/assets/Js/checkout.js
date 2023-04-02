const {createApp} = Vue;

createApp({
    data(){
        return{
            cliente: undefined,
            productos: [],
            compra:{
                productos:[],
                servicios:[]
            },
            cuentaCliente:false,
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
            servicio:"",
            sesion: "0",
            nombreCliente:"",
            numcuenta:"",
            compraProducto:[],
            compraServicio:[],
            turnoDeServicio:null,
            numTarjetaPago:null,
            cvv:null,
            opcionPago:"",
            descontarAdstore:"",
        }
    },


    created(){
        this.sesion = localStorage.getItem("sesion")
        if(this.sesion == "1"){
            this.cargarDatosCliente()
        }
        this.cargarDatos()
        this.cargarDatosServicios()
        this.cargarDatosLocalStorage()

    },

    mounted(){
        this.compra = JSON.parse(localStorage.getItem("compra"))
        console.log(this.compra);
    },

    methods:{
        generarCompra(){
            axios.post("/api/compra", 
            {
                "productos": this.compra.productos,
                "servicios":this.compra.productos,
                "fechaDelServicio":this.turnoDeServicio,
                "numeroTarjetaPago": this.numTarjetaPago,
                "cvv": this.cvv, 
                "pagarCuentaCliente":this.cuentaCliente
            })
            .then(res=>{
                console.log(res);
            })
        },
        logout() {
            axios.post('/api/logout')
                .then(res => {
                    this.sesion = JSON.stringify(localStorage.getItem("sesion"))
                    this.sesion = "0"
                    localStorage.setItem("sesion", this.sesion)
                    window.location.reload
                    window.location.href = "/web/index.html"
                })
                .catch(err => console.error(err.message));
        },

        cargarDatosLocalStorage(){
            this.compra = JSON.parse(localStorage.getItem("compra"))
            this.compraProducto= this.compra.productos
            this.compraServicio= this.compra.servicios
        },

        
        cargarDatosCliente: function(){
            axios.get('/api/cliente')
                .then(respuesta => {
                    this.cliente = respuesta.data;
                    this.direccion = this.cliente.direccion
                    this.telefono = this.cliente.telefono
                    this.numTarjeta = this.cliente.cuenta.tarjetaAd.numeroTarjeta
                    this.saldo = this.cliente.cuenta.saldo
                    this.nombreCliente= this.cliente.nombre + " "+this.cliente.apellido 
                    this.numcuenta =this.cliente.cuenta.numeroCuenta 
                    console.log(this.nombreCliente + this.numcuenta )
                })
                .catch(err => console.error(err.message));
        },
    
        cargarDatos: function(){
            axios.get('/api/productos')
                .then(respuesta => {
                    this.productos = respuesta.data.map(producto => ({... producto}));
                    this.idServicio= (this.servicios.find(serv=>serv.id ==compraServicio.map(servicio=>servicio.id))).id
                    this.idProducto= (this.productos.find(prod=>prod.id ==compraProducto.map(producto =>producto.id))).id
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