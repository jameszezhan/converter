const state = {
    errorMessage: {} 
};

const getters = {
    getErrorMessage: (state) => state.errorMessage
};

const actions = {
    setError({ commit }, errorMessage){
        commit("setError", errorMessage);
    },
    
    clearError( { commit } ){
        commit("clearError");
    }
};

const mutations = {
    setError: (state, errorMessage) => state.errorMessage = errorMessage,
    clearError: (state) => state.errorMessage = {}
};

export default {
    state,
    getters,
    actions,
    mutations
}