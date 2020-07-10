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
        var spIds = [];

        state.recomendations.map(recommendation => {
            let index = recommendation.chosenIndex;
            let uri = recommendation.options[index].uri;
            let isChecked = recommendation.options[index].checked;
            if(isChecked){
                spIds.push(uri);
            }
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
    }
};

const mutations = {
    setRecommendationFromSpotify: (state, recomendations) => {
        for(const [key, value] of Object.entries(recomendations)){
            console.log(key);
            if(JSON.parse(value).tracks.items.length){

                state.recomendations = state.recomendations.concat({
                    "key": key,
                    "options": JSON.parse(value).tracks.items
                });
            }
        }
        state.recomendations.map(recommendation => {
            recommendation.chosenIndex = 0;
            console.log(recommendation.options);
            recommendation.options.map(option => option.checked = true)
        })
    }
};

export default {
    state,
    getters,
    actions,
    mutations
}