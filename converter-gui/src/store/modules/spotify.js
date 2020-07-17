import axios from 'axios';
import * as utility from './utility'

const namespaced = true;

const state = {
    recommendations:[]
};

const getters = {
    allRecommendations: (state) => state.recommendations
};

const actions = {
    async fetchRecommendations({ state, commit, rootState}){
        console.log(state);
        var titlesToSearch = [];
        rootState.youtube.tracks.map(
            track => {
                console.log(track);
                if(track.checked){
                    titlesToSearch.push(track.name)
                }
            }
        )
        var data = JSON.stringify(titlesToSearch);
        const response = await axios({
            method: "post",
            url: process.env.VUE_APP_API_BASE_URL + 'spotify/search',
            headers: { 
                'Content-Type': 'application/json',
                "Accept":"application/json"
            },
            data: data
        });
        

        var recommendations = utility.parseApiResponse(response);
        commit("setRecommendationFromSpotify", recommendations);
    },

    async startMigration({rootState}){
        var spIds = [];

        state.recommendations.map(recommendation => {
            let index = recommendation.chosenIndex;
            let uri = recommendation.options[index].id;
            let isChecked = recommendation.checked;
            if(isChecked){
                spIds.push(uri);
            }
        })
        
        var data = JSON.stringify(spIds);
        const response = await axios({
            method: "post",
            url: process.env.VUE_APP_API_BASE_URL + 'spotify/migrate?state='+rootState.uuids.uuids.spotify,
            headers: { 
                'Content-Type': 'application/json'
            },
            data: data
        });
        window.test = response;
    },
    resetRecommendation({commit}){
        commit("resetRecommendation");
    }
};

const mutations = {
    setRecommendationFromSpotify: (state, recommendations) => {
        for (const [key, value] of Object.entries(recommendations)) {
            if(!value.length > 0){
                continue;
            }
            let recommendation = {
                searchText: key,
                options: value,
                chosenIndex: 0,
                checked: true
            }
            state.recommendations = [
                ...state.recommendations,
                recommendation
            ]
        }
    },
    resetRecommendation: (state) => {
        state.recommendations.map(recommendation => {
            recommendation.chosenIndex = 0;
        })
    }
};

export default {
    namespaced,
    state,
    getters,
    actions,
    mutations
}