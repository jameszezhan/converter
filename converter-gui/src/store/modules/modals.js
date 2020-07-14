const state = {
    isDisplayErr: false 
};

const getters = {
    isDisplayErr: (state) => state.isDisplayErr
};

const actions = {
    toggleErrModal({commit}){
        commit("toggleErrModal");
    }
};

const mutations = {
    toggleErrModal: (state) => state.isDisplayErr = !state.isDisplayErr
};

export default {
    state,
    getters,
    actions,
    mutations
}