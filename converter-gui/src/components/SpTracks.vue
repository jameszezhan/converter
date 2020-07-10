<template>
  <div class="hello">
    <b-button @click="isAuthorizeFetchOkay">Migrate</b-button>
    <b-modal 
      :active.sync="isImageModalActive"
      has-modal-card
      trap-focus
      :destroy-on-hide="false"
      aria-role="dialog"
      aria-modal>
      <div class="card">
        <div>It seems that you haven't authorize your Spotify account yet</div>
        <b-button type="is-success"  @click="authenticateSpotify">Authenticate</b-button>
        <p>You can close this window after you're done</p>
      </div>
    </b-modal>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import Vue from 'vue'
import { Modal } from 'buefy'
// import 'buefy/dist/buefy.css'
Vue.use(Modal)

export default {
  name: 'SpTracks',
  data(){
    return {
      isImageModalActive: false
    }
  },
  methods: {
    ...mapActions(["fetchRecommendations", "startMigration", "authenticateSpotify"]),
    isAuthorizeFetchOkay(){
      if(this.allUUIDS.spotify !== "initial"){
        this.startMigration();
      }else{
        this.isImageModalActive = true
      }
    }
  },
  computed: mapGetters(['allUUIDS'])
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
