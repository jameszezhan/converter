<template>
    <div class="card">
        <div class="list">
            <div>Edit Names</div>
            <div v-for="ytTrack in allYtTracks" v-bind:key="ytTrack.id">
                <b-input v-model=ytTrack.id ></b-input>
                <b-button>Submit</b-button>
            </div>
        </div>
        <b-button @click="$parent.close()" class="sticky">Cancel</b-button>
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
      ...mapActions([]),
  },
  computed: mapGetters(['allYtTracks'])
}
</script>

