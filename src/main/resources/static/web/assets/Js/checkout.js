const {createApp} = Vue;

createApp({
    data(){
        return{
            cliente: undefined,
            productos: [],
            compra:{
                productos:[],
                servicios:[],
                total: 0,
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
            turnoDeServicio:"2023-03-30T12:34:56.789Z",
            numTarjetaPago:null,
            cvv:null,
            opcionPago:"",
            descontarAdstore:"",

            fechasOcupadas: [],
            disabledDatesArray: [],
            selectedDate: null,
            selectedHour: null,
            isoString: null,
            hoursToDisable: [],
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
        this.traerFechasOcupadas();
    },

    mounted(){
        this.compra = JSON.parse(localStorage.getItem("compra"))
        console.log(this.compra);
        this.traerFechasOcupadas();

        //FECHAS
             

        config = {
            locale: "es",
            altInput: true,
            altFormat: "F  j, Y  ",
            minDate: "today",
            maxDate: new Date().fp_incr(20),
        }
        flatpickr(document.getElementById('flatpickr'), config);
    },

    updated(){
        console.log("object");
        config = {
            locale: "es",
            altInput: true,
            altFormat: "F  j, Y  ",
            minDate: "today",
            maxDate: new Date().fp_incr(20),
        }
        flatpickr(document.getElementById('flatpickr'), config);
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

        generarPago: function(){
            Swal.fire({
                customClass: 'modal-sweet-alert',
                title: 'Por favor confirme la compra',
                text: "Si acepta se procederá a la ejecución de la compra. Si desea anular la petición, solo haga clic en el boton 'Cerrar'.",
                icon: 'warning',
                showCancelButton: true,          
                cancelButtonColor: '#d33',
                confirmButtonColor: '#f7ba24',
                cancelButtonText: 'Cerrar',
                confirmButtonText: 'Aceptar'
                }).then((result) => {
                    if (result.isConfirmed) {
                        axios.post("/api/compra", 
                        {
                            "productos": this.compra.productos,
                            "servicios":this.compra.servicios,
                            "fechaDelServicio":this.isoString,
                            "numeroTarjetaPago": this.numTarjetaPago,
                            "cvv": this.cvv, 
                            "pagarCuentaCliente":this.cuentaCliente
                        })
                        .then(res=>{
                            Swal.fire({
                                customClass: 'modal-sweet-alert',
                                text: "Compra realizada!",
                                icon: 'success',
                                confirmButtonColor: '#f7ba24',
                                confirmButtonText: 'Aceptar'
                            }).then((result) => {
                                this.compra={
                                    productos:[],
                                    servicios:[],
                                    total: 0,
                                };
                                localStorage.setItem("compra", JSON.stringify(this.compra))
                                window.location.href = "/web/index.html"
                            })
                        })
                        .catch(err =>{
                            console.log([err])
                
                            Swal.fire({
                                customClass: 'modal-sweet-alert',
                                icon: 'error',
                                title: 'Ups...',
                                text: err.message.includes('403')? err.response.data: "Hubo un error inesperado",
                            })
                        })
                    }
            })
        },


        //FECHAS
        traerFechasOcupadas() {
            axios.get('/api/fechas-ocupadas')
                .then(res => {
                    this.fechasOcupadas = res.data
                    this.horasOcupadas()
                    let picker = new AppointmentPicker(document.getElementById('time'), {
                        interval: 60,
                        mode: '12h',
                        minTime: 9,
                        maxTime: 20,
                        startTime: 9,
                        endTime: 21,
                        disabled: this.hoursToDisable,
                        title: 'Seleccione un horario',

                    });
                    const vm = this; // save reference to Vue instance

                    document.body.addEventListener('change.appo.picker', function (e) {
                        vm.selectedHour = e.displayTime;
                        vm.isoDate();
                        let picker = document.getElementById('time');
                        picker.close();
                    }, false);

                })
        },

        log(event) {
            console.log(event)
            console.log(document.getElementById('time').addEventListener('change.appo.picker', function (e) { }),
                document.getElementById('time').addEventListener('close.appo.picker', function (e) { }),
                document.getElementById('time').addEventListener('open.appo.picker', function (e) { }))
        },
        updateSelectedDate(event) {
            this.selectedDate = event.target.value;
            console.log(this.selectedDate)
        },
        updateSelectedHour(event) {
            this.selectedHour = event.target.value;
            console.log(this.selectedHour)
        },
        isoDate() {
            let combinedStr = this.selectedDate + ' ' + this.selectedHour;
            let datetime = new Date(new Date(combinedStr ).getTime() - (3 * 60 * 60 * 1000));
            this.isoString = datetime.toISOString();
            console.log(this.isoString)
        },
        horasOcupadas(){
            this.hoursToDisable = this.fechasOcupadas.map(date => {
                let hour = date.slice(11, 16);
                return hour;
              });
              console.log(this.hoursToDisable);
        },

    },

}).mount("#app")