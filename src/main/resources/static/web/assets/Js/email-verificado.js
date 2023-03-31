const { createApp } = Vue;

createApp({

    data() {
        return {

        }
    },

    created() {
        window.onload = () => {
            setTimeout(this.redirect, 3000);
        };
    },

    mounted() {

    },

    methods: {

        redirect() {
            window.location.href = '/web/index.html';
        }

    }

}).mount("#app")