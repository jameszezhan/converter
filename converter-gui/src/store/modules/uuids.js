import axios from 'axios';

const state = {
    uuids:{
        youtube: "8c030e9e-6a8e-4b16-a94b-552fc3a561b3",
        spotify: "spotify uuid"
    }
};

const getters = {
    getAllUUIDs: (state) => state.uuids
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