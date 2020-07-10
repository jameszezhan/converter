import axios from 'axios';
import * as utility from './utility'

const state = {
    playlists:[],
    tracks: []
};

const getters = {
    allPlaylists: (state) => state.playlists,
    allYtTracks: (state) => state.tracks
};

const actions = {
    async fetchAllPlaylists( {state, commit, rootState} ){
        console.log(state);
        const response = await axios.get(
            'http://localhost:8080/api/v1/youtube/playlist?state=' + rootState.uuids.uuids.youtube,
            {
                headers:{
                    Accept: "application/json"
                }
            }
        );
        var playlists = utility.parseApiResponse(response);

        commit("setPlaylists", playlists)
    },

    async getTracksFromPlaylists( {state, commit, rootState} ){
        console.log(state);
        var ids = [];
        state.playlists.map(
            playlist => {
                if(playlist.checked){
                    ids.push(playlist.id)
                }
            }
        )
        console.log(ids);
        var data = JSON.stringify(ids);
        const response = await axios({
          method:"post",
          url: "http://localhost:8080/api/v1/youtube/tracks?state=" + rootState.uuids.uuids.youtube,
          headers: { 
            'Content-Type': 'application/json'
          },
          data: data
        });
        commit("setTracks", response.data)
    },

    async toggleAll( {commit}, status){
        commit("toggleAll", status);
    }
};

const mutations = {
    setPlaylists: (state, playlists) => {
        for (const [key, value] of Object.entries(playlists)) {
            console.log(key);
            value.checked = true;
            state.playlists = [
                ...state.playlists,
                value
            ]
        }
    },
    setTracks: (state, tracks) => {
        state.tracks = tracks;
        state.tracks.map(track => track.checked = true)
    },
    toggleAll: (state, status) => {
        state.tracks = state.tracks.map(
            track => {
                track.checked = status
                return track;
            }
        )
        console.log(state.tracks);
    }
};

export default {
    state,
    getters,
    actions,
    mutations
}