<template>
  <div class="hello">
    <!-- <b-button @click="isAuthorizeFetchOkay">toggle</b-button> -->
    <b-button @click="isAuthorizeFetchOkay">Get Playlists</b-button>
    
    <div class="list" v-bind:class="showList()">
      <div v-for="playlist in allPlaylists" v-bind:key="playlist.id">
        <b-checkbox v-model="playlist.checked" type="is-success">
          {{playlist.displayTitle}}
        </b-checkbox>
      </div>
    </div>

    <b-modal 
      :active.sync="isImageModalActive"
      has-modal-card
      trap-focus
      :destroy-on-hide="false"
      aria-role="dialog"
      aria-modal>
      <div class="card">
        <div>It seems that you haven't authorized your YouTube account yet</div>
        <b-button type="is-success"  @click="authenticateYouTube">Authenticate</b-button>
        <p>Try getting playlists again after you're done</p>
      </div>
    </b-modal>


    <b-button
      v-bind:class="canProceed()"
      tag="router-link"
      to="/step2"
      type="is-link">
      next
    </b-button>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import Vue from 'vue'
import { Button, Checkbox, Modal } from 'buefy'
import 'buefy/dist/buefy.css'

Vue.use(Button)
Vue.use(Checkbox)
Vue.use(Modal)

export default {
  name: 'Playlists',
  data(){
    return {
      isImageModalActive: false
    }
  },
  methods: {
      ...mapActions({
        fetchAllPlaylists: "fetchAllPlaylists", 
        authenticateYouTube: "uuids/authenticateYouTube"
      }),
      isAuthorizeFetchOkay(){
        if(this.allUUIDS.youtube !== "initial"){
          this.fetchAllPlaylists();
        }else{
          this.isImageModalActive = true
        }
      },
      canProceed: function(){
        if(!this.allPlaylists.length > 0){
          return "hidden";
        }
      },
      showList: function(){
        if(!this.allPlaylists.length > 0){
          return "hidden";
        }
      }
  },
  computed: mapGetters({
    allPlaylists: 'allPlaylists', 
    allUUIDS:'uuids/allUUIDS'
  })
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
