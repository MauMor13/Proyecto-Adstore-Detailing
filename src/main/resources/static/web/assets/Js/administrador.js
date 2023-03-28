const {createApp} = Vue;

createApp({
    data(){
        return{
            clientes: [],
            productos: [],
            servicios: [],
            elementosVisibles: [],
            clientesVisibles: [],
            productosVisibles: [],
            serviciosVisibles: [],
            totalPaginas: 1,
            paginaNumero: 1,
            numPaginasArray: [],
            numPaginasVisible: [],
            moduloPaginadoActual: 0,
            numbersModulus: undefined,
        }
    },

    methods: {

        renderTabla: function(elementos){

            let tamanio = elementos.length;
            let contador = 0;
            let renglonesElementos = [];

            while(contador < tamanio){
                renglonesElementos.push(elementos.slice(contador, contador+=5));
            }

            this.totalPaginas = renglonesElementos.length;
            if(this.totalPaginas === 1){
                this.paginaNumero = 1;
            }
            this.elementosVisibles = renglonesElementos[this.paginaNumero - 1];
            if(!this.elementosVisibles){
                this.elementosVisibles = [];
            }
            let numeros = [];
            for(let i = 1; i <= this.totalPaginas; i++){
                numeros.push(i);
            }
            contador = 0;
            this.numPaginasArray = [];
            while(contador < this.totalPaginas){
                this.numPaginasArray.push( numeros.slice(contador, contador+=3) );
            }
            this.numPaginasVisible = this.numPaginasArray[this.moduloPaginadoActual];

            if(this.elementosVisibles.some(elemento => elemento.email)){
                this.clientesVisibles = this.elementosVisibles.map(elemento => ({... elemento}));
            }
            if(this.elementosVisibles.some(elemento => elemento.stock)){
                this.productosVisibles = this.elementosVisibles.map(elemento => ({... elemento}));
            }
            if(this.elementosVisibles.some(elemento => elemento.duracion)){
                this.serviciosVisibles = this.elementosVisibles.map(elemento => ({... elemento}));
            }
        },

        cambiarPagina: function(movimientos){
            this.paginaNumero += movimientos;
            this.moduloPaginadoActual = Math.floor((this.paginaNumero - 1) / 3);
            console.log(this.moduloPaginadoActual);
            this.renderTabla();
        },

        irAPagina: function(page){
            this.paginaNumero = page;
            this.renderTabla();
        },
    }

}).mount("#app")