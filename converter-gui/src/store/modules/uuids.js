import axios from 'axios';

const state = {
    uuids:{
        youtube: "3e893d42-6442-4e27-a9a0-1a703f6f4c54",
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