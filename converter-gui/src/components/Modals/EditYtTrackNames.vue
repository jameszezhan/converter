<template>
    <div class="card">
        <div class="list">
            <div>Edit Names</div>
            <div class ="item" v-for="ytTrack in allYtTracks" v-bind:key="ytTrack.id">
                <b-input class="item-name" v-model=ytTrack.name></b-input>
                <b-button class="item-cancel" @on-click="resetName(ytTrack.id)">Cancel</b-button>
            </div>
        </div>
        <b-button @click="$parent.close()" class="sticky">Close</b-button>
    </div>
    
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import Vue from 'vue'
import { Modal, Input, Icon } from 'buefy'
Vue.use(Modal)
Vue.use(Input)
Vue.use(Icon)
export default {
  name: 'EditYtTrackNames',
  methods: {
      ...mapActions(["resetName"]),
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