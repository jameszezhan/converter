import axios from 'axios';

const state = {
    uuids:{
        youtube: "initial",
        // youtube: "2bf19030-e826-4f52-9169-a8c42afa9ba6",
        spotify: "initial"
    }
};

const getters = {
    allUUIDS: (state) => state.uuids
};

const actions = {
    async authenticateYouTube( { commit } ){
        const response = await axios.post('http://localhost:8080/api/v1/youtube');
        const urlParams = new URLSearchParams(response.data);
        const uuid = urlParams.get("state");
        console.log(response);
        commit("setUUIDYouTube", uuid);
        window.open(response.data, "_blank");
    },

    async authenticateSpotify( { commit } ){
        const response = await axios.post('http://localhost:8080/api/v1/spotify');
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
    state,
    getters,
    actions,
    mutations
}