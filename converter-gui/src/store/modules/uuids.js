import axios from 'axios';

const namespaced = true;

const state = {
    uuids:{
        youtube: "initial",
        // youtube: "205d2dac-d61b-4034-aa3a-105f4f7c3aa8",
        spotify: "initial"
    }
};

const getters = {
    allUUIDS: (state) => state.uuids
};

const actions = {
    async authenticateYouTube( { commit } ){
        console.log(process.env.VUE_APP_API_BASE_URL)
        const response = await axios.post(process.env.VUE_APP_API_BASE_URL + 'youtube');
        const urlParams = new URLSearchParams(response.data);
        const uuid = urlParams.get("state");
        console.log(response);
        commit("setUUIDYouTube", uuid);
        window.open(response.data, "_blank");
    },

    async authenticateSpotify( { commit } ){
        const response = await axios.post(process.env.VUE_APP_API_BASE_URL + 'spotify');
        const urlParams = new URLSearchParams(response.data);
        const uuid = urlParams.get("state");
        console.log(response);
        commit("setUUIDSpotify", uuid);
        window.open(response.data, "_blank");
    }
};

const mutations = {
    setUUIDYouTube: (state, uuid) => {state.uuids.youtube = uuid},
    setUUIDSpotify: (state, uuid) => {state.uuids.spotify = uuid}
};

export default {
    namespaced,
    state,
    getters,
    actions,
    mutations
}