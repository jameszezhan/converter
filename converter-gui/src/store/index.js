import Vuex from 'vuex';
import Vue from "vue";
import youtube from './modules/youtube';
import uuids from './modules/uuids';

// Load Vuex
Vue.use(Vuex);

export default new Vuex.Store({
    modules: {
        youtube,
        uuids
    }
})



