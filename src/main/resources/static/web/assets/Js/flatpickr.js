const { createApp } = Vue;
createApp({
    data() {
        return {
            fechasOcupadas: [],
            disabledDatesArray: [],
            selectedDate: null,
            selectedHour: null,
            isoString: null,
            hoursToDisable: [],
        }
    },
    computed: {
        flatpickrInstance() {
            return flatpickr(this.$refs.datepicker)
                .set('onChange', this.updateSelectedDate);
        }
    },
    created() {
        this.traerFechasOcupadas()
    },

    mounted() {
        this.traerFechasOcupadas()

    },

    methods: {
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
            let datetime = new Date(combinedStr);
            this.isoString = datetime.toISOString();
            console.log(this.isoString)
        },
        horasOcupadas(){
            this.hoursToDisable = this.fechasOcupadas.map(date => {
                let hour = date.slice(11, 16);
                return hour;
              });
              console.log(this.hoursToDisable);
        }
    },


}).mount("#app")