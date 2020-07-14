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
    async fetchAllPlaylists( {state, commit, rootState, dispatch} ){
        console.log(state);
        axios.get(
            process.env.VUE_APP_API_BASE_URL + 'youtube/playlist?state=' + rootState.uuids.uuids.youtube,
            {
                headers:{
                    Accept: "application/json"
                }
            }
        ).then(function(response){
            var playlists = utility.parseApiResponse(response);
            commit("setPlaylists", playlists)
        }).catch(function(error){
            dispatch("setError", error, {root:true});
        });
    },

    async getTracksFromPlaylists( {state, commit, rootState, dispatch} ){
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
        axios({
          method:"post",
          url: process.env.VUE_APP_API_BASE_URL + "youtube/tracks?state=" + rootState.uuids.uuids.youtube,
          headers: { 
            'Content-Type': 'application/json'
          },
          data: data
        }).then(function(response){
            commit("setTracks", response.data)
        }).catch(function(error){
            dispatch("setError", error, {root:true});
        });
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