const {createApp} = Vue;

createApp({
    data(){
        return{
            clientes: [],
            productos: [],
            servicios: [],
            clientesVisibles: [],
            productosVisibles: [],
            serviciosVisibles: [],
            totalPaginasCli: 1,
            totalPaginasPro: 1,
            totalPaginasSer: 1,
            paginaNumeroCli: 1,
            paginaNumeroPro: 1,
            paginaNumeroSer: 1,
            numPaginasArrayCli: [],
            numPaginasArrayPro: [],
            numPaginasArraySer: [],
            numPaginasVisibleCli: [],
            numPaginasVisiblePro: [],
            numPaginasVisibleSer: [],
            moduloPaginadoActualCli: 0,
            moduloPaginadoActualPro: 0,
            moduloPaginadoActualSer: 0,
            numbersModulus: undefined,
        }
    },

    created(){
        this.cargarDatos();
    },

    methods: {

        cargarDatos: function(){

            let clientes = axios.get('/api/clientes');
            let productos = axios.get('/api/productos');
            let servicios = axios.get('/api/servicios');

            Promise.all([clientes, productos, servicios])
                    .then(respuesta => {
                        console.log(respuesta);
                        this.clientes = respuesta[0].data.map(cliente => ({...cliente})).sort((c1, c2) => c1.id - c2.id);
                        this.productos = respuesta[1].data.map(producto => ({... producto})).sort((p1, p2) => p1.id - p2.id);
                        this.servicios = respuesta[2].data.map(servicio => ({... servicio})).sort((s1, s2)=> s1.id - s2.id);
                    })

        },

        renderClientes: function(){

            let tamanio = this.clientes.length;
            let contador = 0;
            let renglonesElementos = [];

            while(contador < tamanio){
                renglonesElementos.push(this.clientes.slice(contador, contador+=10));
            }

            this.totalPaginasCli = renglonesElementos.length;
            if(this.totalPaginasCli === 1){
                this.paginaNumeroCli = 1;
            }
            this.clientesVisibles = renglonesElementos[this.paginaNumeroCli - 1];
            if(!this.clientesVisibles){
                this.clientesVisibles = [];
            }
            let numeros = [];
            for(let i = 1; i <= this.totalPaginasCli; i++){
                numeros.push(i);
            }
            contador = 0;
            this.numPaginasArrayCli = [];
            while(contador < this.totalPaginasCli){
                this.numPaginasArrayCli.push( numeros.slice(contador, contador+=3) );
            }
            this.numPaginasVisibleCli = this.numPaginasArrayCli[this.moduloPaginadoActualCli];
        },

        cambiarPaginaCli: function(movimientos){
            this.paginaNumeroCli += movimientos;
            this.moduloPaginadoActualCli = Math.floor((this.paginaNumeroCli - 1) / 3);
            console.log(this.moduloPaginadoActualCli);
            this.renderClientes();
        },

        irAPaginaCli: function(page){
            this.paginaNumeroCli = page;
            this.renderClientes();
        },


        renderProductos: function(){

            let tamanio = this.productos.length;
            let contador = 0;
            let renglonesElementos = [];

            while(contador < tamanio){
                renglonesElementos.push(this.productos.slice(contador, contador+=10));
            }

            this.totalPaginasPro = renglonesElementos.length;
            if(this.totalPaginasPro === 1){
                this.paginaNumeroPro = 1;
            }
            this.productosVisibles = renglonesElementos[this.paginaNumeroPro - 1];
            if(!this.productosVisibles){
                this.productosVisibles = [];
            }
            let numeros = [];
            for(let i = 1; i <= this.totalPaginasPro; i++){
                numeros.push(i);
            }
            contador = 0;
            this.numPaginasArrayPro = [];
            while(contador < this.totalPaginasPro){
                this.numPaginasArrayPro.push( numeros.slice(contador, contador+=3) );
            }
            this.numPaginasVisiblePro = this.numPaginasArrayPro[this.moduloPaginadoActualPro];
        },

        cambiarPaginaPro: function(movimientos){
            this.paginaNumeroPro += movimientos;
            this.moduloPaginadoActualPro = Math.floor((this.paginaNumeroPro - 1) / 3);
            console.log(this.moduloPaginadoActualPro);
            this.renderProductos();
        },

        irAPaginaPro: function(page){
            this.paginaNumeroPro = page;
            this.renderProductos();
        },


        renderServicios: function(){

            let tamanio = this.servicios.length;
            let contador = 0;
            let renglonesElementos = [];

            while(contador < tamanio){
                renglonesElementos.push(this.servicios.slice(contador, contador+=10));
            }

            this.totalPaginasSer = renglonesElementos.length;
            if(this.totalPaginasSer === 1){
                this.paginaNumeroSer = 1;
            }
            this.serviciosVisibles = renglonesElementos[this.paginaNumeroSer - 1];
            if(!this.serviciosVisibles){
                this.serviciosVisibles = [];
            }
            let numeros = [];
            for(let i = 1; i <= this.totalPaginasSer; i++){
                numeros.push(i);
            }
            contador = 0;
            this.numPaginasArraySer = [];
            while(contador < this.totalPaginasSer){
                this.numPaginasArraySer.push( numeros.slice(contador, contador+=3) );
            }
            this.numPaginasVisibleSer = this.numPaginasArraySer[this.moduloPaginadoActualSer];
        },

        cambiarPaginaSer: function(movimientos){
            this.paginaNumeroSer += movimientos;
            this.moduloPaginadoActualSer = Math.floor((this.paginaNumeroSer - 1) / 3);
            console.log(this.moduloPaginadoActualSer);
            this.renderServicios();
        },

        irAPaginaSer: function(page){
            this.paginaNumeroSer = page;
            this.renderServicios();
        },
    }

}).mount("#app")