import axios from 'axios';
import * as utility from './utility'

const state = {
    recommendations:[]
};

const getters = {
    allRecommendations: (state) => state.recommendations
};

const actions = {
    async fetchRecommendations({ state, commit, rootState}){
        console.log(state);
        var titlesToSearch = [
            "Rain on me",
            "spit it out"
        ];
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
    },
    resetRecommendation({commit}){
        commit("resetRecommendation");
    }
};

const mutations = {
    setRecommendationFromSpotify: (state, recommendations) => {
        for (const [key, value] of Object.entries(recommendations)) {
            let recommendation = {
                searchText: key,
                options: value,
                chosenIndex: 0
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
    state,
    getters,
    actions,
    mutations
}