import Vuex from 'vuex';
import Vue from "vue";
import youtube from './modules/youtube';
import uuids from './modules/uuids';
import spotify from './modules/spotify'
import modals from './modules/modals'
import errors from './modules/errors'

// Load Vuex
Vue.use(Vuex);

export default new Vuex.Store({
    modules: {
        youtube,
        uuids,
        spotify,
        modals,
        errors
    }
})



