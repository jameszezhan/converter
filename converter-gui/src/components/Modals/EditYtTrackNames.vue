<template>
    <div class="card">
        <div class="list">
            <div>Edit Names</div>
            <div class ="item" v-for="ytTrack in allYtTracks" v-bind:key="ytTrack.id">
                <b-input class="item-name" v-model=ytTrack.name></b-input>
                <b-dropdown aria-role="list" class="is-pulled-right" position="is-bottom-left">
                    <b-icon icon="dots-vertical" slot="trigger"></b-icon>
                    <b-dropdown-item aria-role="listitem" @click="resetName(ytTrack.id)">Reset</b-dropdown-item>
                    <b-dropdown-item aria-role="listitem" @click="toggleCheckbox(ytTrack.id)">Remove</b-dropdown-item>
                </b-dropdown>
            </div>
        </div>
        <b-button @click="$parent.close()" class="sticky">Close</b-button>
    </div>
    
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import Vue from 'vue'
import { Modal, Input, Icon, Dropdown } from 'buefy'
Vue.use(Modal)
Vue.use(Input)
Vue.use(Icon)
Vue.use(Dropdown)
export default {
  name: 'EditYtTrackNames',
  methods: {
      ...mapActions(["resetName", "toggleCheckbox"]),
  },
  computed: mapGetters(['allYtTracks'])
}
</script>

<style scoped>
.item{
    margin-bottom: 10px;
}
.item-name{
    display: inline-block;
    width: 80%;
}
.item-cancel{
    display: inline-block;
    margin: 0px auto;
    min-width: 15%;
    width: 15%;
    max-width: 15%;
    float: right;
}
</style>