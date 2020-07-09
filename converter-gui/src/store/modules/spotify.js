import axios from 'axios';

const state = {
    recomendations:[]
};

const getters = {
    allRecommendations: (state) => state.recomendations
};

const actions = {
    async fetchRecommendations({commit, rootState}){
        var titlesToSearch = [];
        rootState.youtube.tracks.map(
            track => {
                console.log(track);
                if(track.checked){
                    titlesToSearch.push(track.snippet.title)
                }
            }
        )
        console.log(titlesToSearch);
        var data = JSON.stringify(titlesToSearch);
        const response = await axios({
            method: "post",
            url: 'http://localhost:8080/api/v1/spotify/search',
            headers: { 
                'Content-Type': 'application/json'
            },
            data: data
        });
        window.test = response;
        commit("setRecommendationFromSpotify", response.data);
    },

    async startMigration({rootState}){
        // dispatch('uuids/authenticateSpotify',null,{root: true})

        var spIds = [];

        state.recomendations.map(recomemendation => {
            let index = recomemendation.chosenIndex;
            let uri = recomemendation.options[index].uri;
            spIds.push(uri);
        })
        
        var data = JSON.stringify(spIds);
        const response = await axios({
            method: "post",
            url: 'http://localhost:8080/api/v1/spotify/migrate?state='+rootState.uuids.uuids.spotify,
            headers: { 
                'Content-Type': 'application/json'
            },
            data: data
        });
        window.test = response;
        // commit("setRecommendationFromSpotify", response.data);
    }
};

const mutations = {
    setRecommendationFromSpotify: (state, recomendations) => {
        for(const [key, value] of Object.entries(recomendations)){
            console.log(key);
            console.log(JSON.parse(value).tracks.items);
            state.recomendations = state.recomendations.concat({
                "key": key,
                "options": JSON.parse(value).tracks.items
            });
        }
        state.recomendations.map(recommendation => {
            recommendation.chosenIndex = 0;
        })
    }
};

export default {
    state,
    getters,
    actions,
    mutations
}