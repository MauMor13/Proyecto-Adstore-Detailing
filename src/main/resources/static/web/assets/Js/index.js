const {createApp} = Vue;

createApp({
    
    data(){
        return{
            cliente: undefined,
            mensajes: [" UN SERVICIO DE CALIDAD...", "VOS Y TU VEHICULO LO MERECEN...", " VIVI LA EXPERCIENCIA ! !"],
            textoDinamico: "",
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

    },

    mounted(){
        window.addEventListener('scroll', this.scrollFunction);
        this.controlCarrusel();
        this.administraAsincronas();
        this.cargarDatos();
    },

    methods: {

        //PARA LA CARGA DE DATOS
        cargarDatos: function(){
            axios.get('/api/cliente')
                .then(respuesta => {
                    this.cliente = respuesta.data;
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
                    this.cargarDatos();
                    window.location.reload
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
        logOut(){
            axios.post('/api/logout')
            .then(() => window.location.href="./index.html")
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

        scrollFunction() {

            let nav = document.querySelector(".nav-landing-page");
            let fotos = document.querySelector(".fotos-landing");
            let icono = document.querySelector(".icono-landing");

            if (document.body.scrollTop > fotos.clientHeight - nav.clientHeight / 2 || document.documentElement.scrollTop > fotos.clientHeight - nav.clientHeight / 2|| window.pageYOffset > fotos.clientHeight - nav.clientHeight / 2) {
                nav.style.backgroundColor = "black";  
                icono.style.height = "5rem";
                icono.src = "../web/assets/Imagenes/iconoFondoNegroPNG.png"
            } else {
                nav.style.backgroundColor = "transparent";
                icono.style.height = "10rem"
                icono.src = "../web/assets/Imagenes/PNG9.png"
            }

            if(icono.scrollTop == nav.scrollTop){
                icono.position = "static";
            }
        },

        administraAsincronas: function(){
            this.manejaAutoTyping();
            this.cambioDeFotos();
        },

        controlCarrusel: function(){
            const miCarrusel = document.getElementById('carouselExampleSlidesOnly');
            /*const botonesDireccion = [... document.querySelectorAll('.btn-carousel')];

            myCarrusel.addEventListener('slide.bs.carousel', event => {
                botonesDireccion[0].style.opacity = '0';
                botonesDireccion[1].style.opacity = '0';
            })

            myCarrusel.addEventListener('slid.bs.carousel', event => {
                botonesDireccion[0].style.opacity = '1';
                botonesDireccion[1].style.opacity = '1';
            })*/

            const carousel = new bootstrap.Carousel(miCarrusel, {
                interval: 7000,
            })

        },

        //autotyping

        async manejaAutoTyping(){

            let i = 0;
            let actual = 0;
            while(true) {
                actual = await this.escribeOracion(actual);
                await this.esperaPorMs(1500);
                actual = await this.borraOracion(actual);
                await this.esperaPorMs(1500);
                i++
                if(i >= this.mensajes.length) {i = 0;}
            }
        },

        async escribeOracion(actual) { 

            let i = 0;
            while(i < this.mensajes[actual].length) {
                await this.esperaPorMs(100);
                this.textoDinamico += this.mensajes[actual].charAt(i);
                i++
            }
            actual ++;
            return actual;                
        },

        async borraOracion(actual){

            while(this.textoDinamico.length > 0) {
                await this.esperaPorMs(100);
                this.textoDinamico = this.textoDinamico.slice(0,-1);
            }
            if(actual > 2){
                actual = 0;
            }
            
            return actual;
        },

        esperaPorMs(ms) {
            return new Promise(resolve => setTimeout(resolve, ms))
        },

        //cambio de foto

        async cambioDeFotos (){
            let i = 1;
            let fotos = document.querySelector(".fotos-landing");
            console.log([fotos]);
            while(i <= 5){
                await this.esperaPorMs(10000);
                for(let j = 1; j > 0; j -= 0.1){
                    await this.esperaPorMs(25);
                    fotos.style.opacity = `${j}`;
                }
                fotos.style.backgroundImage = `url(../web/assets/Imagenes/banner${i}.jpg)`
                // if(i == 0){
                //     fotos.style.backgroundImage = `url(../web/assets/Imagenes/banner${i}.jpg)`
                // }
                // else{
                //     fotos.style.backgroundImage = `url(../web/assets/Imagenes/foto${i}.jpeg)`
                // }
                for(let j = 0; j < 1; j += 0.1){
                    fotos.style.opacity = `${j}`;
                    await this.esperaPorMs(25);
                }
                i ++;
                if(i == 6){
                    i = 0;
                }
            }
        } 



    },

    
}).mount("#app")