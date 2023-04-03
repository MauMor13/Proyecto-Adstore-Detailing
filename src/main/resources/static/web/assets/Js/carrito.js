const {createApp} = Vue;

createApp({
    data(){
        return{
            cliente: undefined,
            compra: undefined,
            productos: [],
            servicios: [],
            numTarjeta: "",
            cvv: "",
            usarCuenta: false
        }
    },
    created(){

    },
    mounted(){

    },
    methods: {
        obtenerCarrito(){
            this.compra = JSON.parse(localStorage.getItem("compra"))
            this.productos = this.compra.productos
            this.servicios = this.compra.servicios

        },
        hacerCompra(){
            axios.post('/api/compra',`{
                "productos":${this.productos},
                "servicios":${this.servicios},
                "fechaDelServicio":"2023-03-30T12:34:56.789Z",
                "numeroTarjetaPago":${this.numTarjeta},
                "cvv":${this.cvv},
                "pagarCuentaCliente":${this.usarCuenta}
            }`)
        }

    }
})