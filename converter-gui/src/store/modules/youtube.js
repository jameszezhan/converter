import axios from 'axios';

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
        console.log(response);
        commit("setPlaylists", response.data.items)
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
        console.log(response.data);
        commit("setTracks", response.data)
    },

    async toggleAll( {commit}, status){
        commit("toggleAll", status);
    }
};

const mutations = {
    setPlaylists: (state, playlists) => {
      state.playlists = playlists;
      state.playlists.map(
          playlist => playlist.checked = false
      )
      console.log(state.playlists);
    },
    setTracks: (state, tracks) => {
        for(const [key, value] of Object.entries(tracks)){
            console.log(key);
            console.log(JSON.parse(value).items);
            state.tracks = state.tracks.concat(JSON.parse(value).items);
        }
        state.tracks.map(
            tracks => tracks.checked = true
        )
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