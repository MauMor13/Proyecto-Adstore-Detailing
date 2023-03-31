const {createApp} = Vue;

createApp({
    
    data(){
        return{
            cliente: undefined,
            errorEncontrado: false,
            registrado: false,
            nombre: "",
            apellido: "",
            email: "",
            contra: "",
            direccion: "",
            telefono: "",
            emailInicioSesion: undefined,
            contraInicioSesion: undefined,
            compra:{
                productos:[],
                servicios:[]
            },
            productos:[],
            servicios:[],
            servicioElegido: undefined,
        }
    },

    created(){

        this.cargarDatos();
        this.guardarLocalStorage();
        this.cargarDatosServicios();


    },

    mounted(){
        
    },

    methods: {
        logout() {
            axios.post('/api/logout')
            .then(res =>{
                window.location.href = "/web/index.html"
            })
        },
        cargarDatosCliente: function(){
            axios.get('/api/cliente')
                .then(respuesta => {
                    this.cliente = respuesta.data;
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
        limpiarCarrito(){
            localStorage.clear()
            this.compra={
                productos:[],
                servicios:[]
            }
        },

        guardarLocalStorage(){
            if(localStorage.getItem("compra") == null){
                localStorage.setItem("compra", JSON.stringify(this.compra))
            }
        },
        sumarUnidad(productoId,cantidad){
            this.compra = JSON.parse(localStorage.getItem("compra"))
            let productoEnCarro = this.compra.productos.find(element => element.id == productoId)
            if(productoEnCarro != null){
                if(productoEnCarro.cantidad == this.compra.productos.find(element => element.id == productoEnCarro.id).stock){
                    Swal.fire({
                        customClass: 'modal-sweet-alert',
                        title: 'Lo sentimos',
                        text: "No hay más stock disponible",
                        icon: 'warning',
                        confirmButtonColor: '#f7ba24',
                        confirmButtonText: 'Aceptar'
                    })
                }
                else{
                productoEnCarro.cantidad += cantidad
            }}
            this.productos = this.compra.productos
            localStorage.setItem("compra",JSON.stringify(this.compra))
        },
        restarUnidad(productoId, cantidad){
            this.compra = JSON.parse(localStorage.getItem("compra"))
            let productoEnCarro = this.compra.productos.find(element => element.id == productoId)
            if( productoEnCarro == 0){
                productoEnCarro == 0
                }else{
                    productoEnCarro.cantidad -= cantidad  
                }
            this.productos = this.compra.productos
            localStorage.setItem("compra",JSON.stringify(this.compra))
        },
        agregarACarrito(idSeleccion, cantidad){
            this.compra = JSON.parse(localStorage.getItem("compra"))
            let productoEnCarro = this.compra.productos.find(element => element.id == idSeleccion)
            if(productoEnCarro != null){
                if(productoEnCarro.cantidad == this.compra.productos.find(element => element.id == productoEnCarro.id).stock){
                    Swal.fire({
                        customClass: 'modal-sweet-alert',
                        title: 'Lo sentimos',
                        text: "Has excedido la cantidad de productos que tenemos en stock, si quieres puedes agregar algun otro producto a tu compra.",
                        icon: 'warning',
                        confirmButtonColor: '#f7ba24',
                        confirmButtonText: 'Aceptar'
                    })
                }
                else{
                productoEnCarro.cantidad += cantidad
            }}
            else{
                let objeto = {id: 0, cantidad: 0};
                objeto.id = idSeleccion
                objeto.cantidad = cantidad
                this.compra.productos.push(objeto)
            }
            this.productos = this.compra.productos
            localStorage.setItem("compra",JSON.stringify(this.compra))
        },

        },

        cargarServicios:function(){
            axios.get('/api/servicios-activos')
                .then(respuesta => {
                    this.servicios = respuesta.data;
                    console.log(this.servicios);
                })
                .catch(err => console.error(err.message));

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

        verDetallesServicio1: function (value) {
            let form = document.querySelector('.flip-card1 .flip-card-inner1');
            if (value == 'front') {
                form.classList.add('girarServicio');
            }
            else if (value == 'back') {
                form.classList.remove('girarServicio');
            }
        },
        verDetallesServicio2: function (value) {
            let form = document.querySelector('.flip-card2 .flip-card-inner2');
            if (value == 'front') {
                form.classList.add('girarServicio');
            }
            else if (value == 'back') {
                form.classList.remove('girarServicio');
            }
        },
        verDetallesServicio3: function (value) {
            let form = document.querySelector('.flip-card3 .flip-card-inner3');
            if (value == 'front') {
                form.classList.add('girarServicio');
            }
            else if (value == 'back') {
                form.classList.remove('girarServicio');
            }
        },
        verDetallesServicio4: function (value) {
            let form = document.querySelector('.flip-card4 .flip-card-inner4');
            if (value == 'front') {
                form.classList.add('girarServicio');
            }
            else if (value == 'back') {
                form.classList.remove('girarServicio');
            }
        },


        //efecto paginado

        hacerEfectoPagina: function(servicio){
            this.servicioElegido = {... servicio};
            let vistaPrincipal = document.querySelector('.card-principal')
            let todosServicios = document.querySelectorAll('.servicios')

            vistaPrincipal.style.left = '0';
            
        },

        volverPagina: function(){
            let vistaPrincipal = document.querySelector('.card-principal');
            let todosServicios = document.querySelectorAll('.servicios')
            vistaPrincipal.style.left = '-100vw';
        },


    },

 
}).mount("#app")