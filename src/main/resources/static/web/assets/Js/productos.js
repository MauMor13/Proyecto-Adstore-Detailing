const { createApp } = Vue;

createApp({
    data() {
        return {
            cliente: undefined,
            categorias: [],
            productosFiltrados: [],
            checked: [],
            inputBusqueda: "",
            compra: {
                productos: [],
                servicios: [],
                total: 0,
            },
            cantidad: [],
            errorEncontrado: false,
            registrado: false,
            nombre: "",
            apellido: "",
            email: "",
            contra: "",
            direccion: "",
            telefono: "",
            numTarjeta: undefined,
            saldo: undefined,
            emailInicioSesion: undefined,
            contraInicioSesion: undefined,
            productos: [],
            servicios: [],
            servicio: "",
            sesion: "0"
        }
    },
    created() {

        this.administrarCargaDatos();
        this.sesion = localStorage.getItem("sesion")
        if (this.sesion == "1") {
            this.cargarDatosCliente()
        }
        
        
    },
    mounted() {
        this.compra = JSON.parse(localStorage.getItem("compra"))
    },
    methods: {

        //CARGA DE DATOS PARA RENDERIZAR
        administrarCargaDatos(){

            this.cargarDatosProductos();
            this.cargarDatosServicios();
            //this.cargarDatosCliente();
            this.guardarLocalStorage()
        },

        guardarLocalStorage() {
            if (localStorage.getItem("compra") == null) {
                localStorage.setItem("compra", JSON.stringify(this.compra))
            }
        },

        cargarDatosProductos: function () {
            axios.get('/api/productos-activos')
                .then(respuesta => {
                    this.productos = respuesta.data.map(producto => ({ ...producto }));
                    this.productosFiltrados = respuesta.data.map(producto => ({ ...producto }));
                    this.categorias = [... new Set(this.productos.map(producto => producto.categoria))];
                })
        },

        cargarDatosServicios: function () {
            axios.get('/api/servicios-activos')
                .then(respuesta => {
                    this.servicios = respuesta.data.map(servicio => ({ ...servicio }));
                })
        },

        busquedaCruzada: function () {
            let filtroInput = this.productos.filter(producto => producto.nombre.toLowerCase().includes(this.inputBusqueda.toLowerCase()))
            if (this.checked.length === 0) {
                this.productosFiltrados = filtroInput
            } else {
                let filtroCheck = filtroInput.filter(categoria => this.checked.includes(categoria.categoria))
                this.productosFiltrados = filtroCheck
            }
        },

        cargarDatosCliente: function () {
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


        //MOVIMIENTOS CARRITO

        agregarACarrito(idSeleccion, cantidad) {

            this.compra = JSON.parse(localStorage.getItem("compra"))
            if(!this.compra){
                this.compra = {
                    productos: [],
                    servicios: [],
                    total: 0,
                }
            }
            let productoEnCarro = this.compra.productos.find(element => element.id == idSeleccion)
            let productoStockDisponible = this.productosFiltrados.find(element => element.id == idSeleccion).stock;
            
            if(productoEnCarro && productoEnCarro.cantidad >= productoStockDisponible || !productoEnCarro && productoStockDisponible <= 0){

                Swal.fire({
                    customClass: 'modal-sweet-alert',
                    title: 'Lo sentimos',
                    text: "Has excedido la cantidad de productos que tenemos en stock, si quieres puedes agregar algun otro producto a tu compra.",
                    icon: 'warning',
                    confirmButtonColor: '#f7ba24',
                    confirmButtonText: 'Aceptar'
                })
            }
            else {
                if(productoEnCarro){
                    productoEnCarro.cantidad += cantidad;
                }
                else{
                    let objeto = { id: 0, cantidad: 0 };
                    objeto.id = idSeleccion
                    objeto.cantidad = cantidad
                    this.compra.productos.push(objeto)
                }
                this.compra.total += this.productos.find(element => element.id == idSeleccion).precio;
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

                localStorage.setItem("compra", JSON.stringify(this.compra))
            }
        },

        sumarUnidadProducto(productoId, cantidad) {
            this.compra = JSON.parse(localStorage.getItem("compra"))
            let productoEnCarro = this.compra.productos.find(element => element.id == productoId)
            let productoStockDisponible = this.productosFiltrados.find(element => element.id == productoId).stock;

            if(productoEnCarro.cantidad >= productoStockDisponible){
                Swal.fire({
                    customClass: 'modal-sweet-alert',
                    title: 'Lo sentimos',
                    text: "No hay más stock disponible",
                    icon: 'warning',
                    confirmButtonColor: '#f7ba24',
                    confirmButtonText: 'Aceptar'
                })
            }
            else {
                productoEnCarro.cantidad += cantidad;
                this.compra.total += this.productos.find(producto => producto.id == productoId).precio;
                localStorage.setItem("compra", JSON.stringify(this.compra))
            }
            
        },

        restarUnidadProducto(productoId, cantidad) {
            this.compra = JSON.parse(localStorage.getItem("compra"))
            let productoEnCarro = this.compra.productos.find(element => element.id == productoId)
            if (productoEnCarro.cantidad <= 1) {
                this.compra.productos.splice(this.compra.productos.indexOf(productoEnCarro), 1);
            } else {
                productoEnCarro.cantidad -= cantidad
            }
            this.compra.total -= this.productos.find(producto => producto.id == productoId).precio;
            localStorage.setItem("compra", JSON.stringify(this.compra))
        },

        sumarUnidadServicio(servicioID, cantidad) {
            
            Swal.fire({
                customClass: 'modal-sweet-alert',
                title: 'Lo sentimos',
                text: "Solo se permite agregar una vez a cada servicio. Si quieres puedes combinar con otro tipo de tratamiento para tu vehículo",
                icon: 'warning',
                confirmButtonColor: '#f7ba24',
                confirmButtonText: 'Aceptar'
            })
        },

        restarUnidadServicio(servicioID, cantidad) {
            this.compra = JSON.parse(localStorage.getItem("compra"))
            let servicioEnCarro = this.compra.servicios.find(element => element.id == servicioID)
            this.compra.servicios.splice(this.compra.servicios.indexOf(servicioEnCarro), 1);
            
            this.compra.total -= this.servicios.find(servicio => servicio.id == servicioID).precio;
            localStorage.setItem("compra", JSON.stringify(this.compra))
        },

        limpiarCarrito() {
            localStorage.removeItem('compra')
            this.compra = {
                productos: [],
                servicios: [],
                total: 0,
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


        logout() {
            axios.post('/api/logout')
                .then(res => {
                    console.log("hola");
                    this.sesion = JSON.stringify(localStorage.getItem("sesion"))
                    this.sesion = "0"
                    localStorage.setItem("sesion", this.sesion)

                    window.location.reload

                })
                .catch(err => console.error(err.message));
        },
        
        
        
        verDetallesProducto: function (id) {
            let foto = document.getElementById(id + "Foto");
            let telon = document.getElementById(id + "Telon");
            let desc = document.getElementById(id + "Desc");
            let boton = document.getElementById(id + "Bot");
            if (boton.textContent.includes("Ver detalles")) {
                foto.style.scale = "0.6"
                foto.style.translate = "0 -20%"
                telon.style.translate = "0 0"
                desc.style.translate = "0 0"
                desc.style.opacity = "1"
                desc.style.zIndex = "1"
                boton.innerText = "Volver"
                boton.style.color = "white"
            }
            else {
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
        
        
        

        //Generar registro
        realizarRegistro: function () {
            axios.post('/api/registrar', { nombre: this.nombre, apellido: this.apellido, email: this.email, claveIngreso: this.contra, direccion: this.direccion, telefono: this.telefono, })
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

                    Swal.fire({
                        customClass: 'modal-sweet-alert',
                        title: 'Cliente regisrado!',
                        text: "Para poder acceder a la cuenta debes verificar tu E-mail. Por favor, revisa tu correo y verificalo",
                        icon: 'success',
                        confirmButtonColor: '#f7ba24',
                        confirmButtonText: 'Aceptar'
                    })

                })
                .catch(err => {
                    this.errorEncontrado = true;
                    console.error([err]);
                    let spanError = document.querySelector('.mensaje-error-registro');
                    spanError.innerHTML = err.response.data;
                    if (err.response.data.includes('Email ya registrado')) {
                        this.email = "";
                        this.contra = "";
                    }
                })
        },
        iniciarSesion: function () {
            axios.post('/api/login', `email=${this.emailInicioSesion}&claveIngreso=${this.contraInicioSesion}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(response => {
                    this.sesion = "1"
                    localStorage.setItem("sesion", this.sesion)
                    this.cargarDatosProductos();
                    this.cargarDatosCliente();

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
                        title: 'Has iniciado sesión con exito!'
                    })
                })
                .catch(err => {
                    Swal.fire({
                        customClass: 'modal-sweet-alert',
                        title: 'Usuario no encontrado',
                        text: "Por favor verifica tus credenciales",
                        icon: 'warning',
                        confirmButtonColor: '#f7ba24',
                        confirmButtonText: 'Aceptar'
                    })
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