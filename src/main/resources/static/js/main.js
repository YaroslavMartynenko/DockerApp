var pointApi = Vue.resource('/point{/id}');

Vue.component('point-form', {
    props: ['points'],
    data: function () {
        return {
            name: '',
            longtitude: '',
            latitude: ''
        }
    },
    template: '<div> ' +
        '<input type="text" placeholder="Point name" v-model="name"/>' +
        '<input type="text" placeholder="Longtitude" v-model="longtitude"/>' +
        '<input type="text" placeholder="Latitude" v-model="latitude"/>' +
        '<input type="button" value="Save" v-on:click="save"/>' +
        '</div>',
    methods: {
        save: function () {
            var point = {
                name: this.name,
                longtitude: this.longtitude,
                latitude: this.latitude
            };
            pointApi.save({}, point).then(result => result.json().then(data => this.points.push(data)));
            this.name = '';
            this.longtitude = '';
            this.latitude = '';
        }
    }
});

Vue.component('point-element', {
    props: ['point'],
    template: '<div>({{point.id}}) {{point.name}} lon: {{point.longtitude}}, lat: {{point.latitude}}</div>'
});

Vue.component('point-list', {
    props: ['points'],
    template: '<div>' +
        '<point-form :points="points"/>' +
        '<point-element v-for="point in points" :key="point.id" :point="point"/>' +
        '</div>',
    created: function () {
        pointApi.get().then(result => result.json().then(data => data.forEach(point => this.points.push(point))))
    }
});

var app = new Vue({
    el: '#app',
    template: '<point-list :points="points"/>',
    data: {
        points: []
    }
});