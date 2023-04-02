const { createApp } = Vue;
createApp({
    data() {
        return {
            fechasOcupadas: null,
            disabledDatesArray: null,
            selectedDate: null,
            selectedHour: null,
            isoString: null,
        }
    },
    computed: {
        flatpickrInstance() {
            return flatpickr(this.$refs.datepicker)
                .set('onChange', this.updateSelectedDate);
        }
    },
    created() {

        //   this.traerFechasOcupadas();
    },

    mounted() {
        let picker = new AppointmentPicker(document.getElementById('time'), {
            interval: 60,
            mode: '12h',
            minTime: 9,
            maxTime: 20,
            startTime: 9,
            endTime: 21,
            disabled: ['16:00', '17:00'],
            title: 'Seleccione un horario',

        });
        const vm = this; // save reference to Vue instance

        document.body.addEventListener('change.appo.picker', function (e) {
            vm.selectedHour = e.displayTime;
            vm.isoDate();
        }, false);

        //    this.disabledDatesArray = this.fechasOcupadas.map((dateString) => new Date(dateString));

    },

    methods: {
        traerFechasOcupadas() {
            axios.get('/api/fechas-ocupadas')
                .then(res => {
                    this.fechasOcupadas = res
                    console.log(this.fechasOcupadas)
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
        }
    },


}).mount("#app")