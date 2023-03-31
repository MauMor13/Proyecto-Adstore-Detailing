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

        verDetallesProducto: function (id) {
            let foto = document.getElementById(id + "Foto");
            let telon = document.getElementById(id + "Telon");
            let desc = document.getElementById(id + "Desc");
            let boton = document.getElementById(id + "Bot");

            if(boton.textContent.includes("Ver detalles")){

                foto.style.scale = "0.6"
                foto.style.translate = "0 -20%"
                telon.style.translate = "0 0"
                desc.style.translate = "0 0"
                desc.style.opacity = "1"
                desc.style.zIndex = "1"
                boton.innerText = "Volver"
                boton.style.color = "white"
            }
            else{

                foto.style.scale = "1"
                foto.style.translate = "0 0"
                telon.style.translate = "0 100%"
                desc.style.translate = "0 -10rem"
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
        limpiarCarrito(){
            localStorage.clear()
            this.compra={
                productos:[],
                servicios:[]
            }
            localStorage.setItem("compra", JSON.stringify(this.compra))

            const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true,
                didOpen: (toast) => {
                  toast.addEventListener('mouseenter', Swal.stopTimer)
                  toast.addEventListener('mouseleave', Swal.resumeTimer)
                }
              })

              Toast.fire({
                customClass: 'modal-sweet-alert',
                icon: 'success',
                title: 'Has vaciado el carro de compras'
              })
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
            localStorage.setItem("compra",JSON.stringify(this.compra))
        },
        restarUnidad(productoId, cantidad){
            this.compra = JSON.parse(localStorage.getItem("compra"))
            let productoEnCarro = this.compra.productos.find(element => element.id == productoId)
            if( productoEnCarro.cantidad == 0){
                this.compra.productos.splice(this.compra.productos.indexOf(productoEnCarro), 1)
                }else{
                    productoEnCarro.cantidad -= cantidad  
            }
            
            localStorage.setItem("compra",JSON.stringify(this.compra))
        },


        agregarACarrito(idSeleccion, cantidad){
            this.compra = JSON.parse(localStorage.getItem("compra"))
            console.log(this.compra.productos);
            let productoEnCarro = this.compra.productos.find(element => element.id == idSeleccion)
            if(productoEnCarro != null){
                if(productoEnCarro.cantidad == this.productosFiltrados.find(element => element.id == productoEnCarro.id).stock){
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
            localStorage.setItem("compra",JSON.stringify(this.compra))

            const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true,
                didOpen: (toast) => {
                  toast.addEventListener('mouseenter', Swal.stopTimer)
                  toast.addEventListener('mouseleave', Swal.resumeTimer)
                }
              })

              Toast.fire({
                customClass: 'modal-sweet-alert',
                icon: 'success',
                title: 'Has agregado un producto!'
              })
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