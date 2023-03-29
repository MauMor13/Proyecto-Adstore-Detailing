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

        }
    },


    created(){
        this.cargarDatos();

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



    },

}).mount("#app")