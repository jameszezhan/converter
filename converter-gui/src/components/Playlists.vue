<template>
  <div class="hello">
    <v-btn 
      depressed
      color="secdonary" 
      @click="isAuthorizeFetchOkay"
    >Get Playlists</v-btn>
    <v-divider></v-divider>

    <v-card
      class="mx-auto"
      max-width="500"
      title
      v-if="allPlaylists.length"
    >
      <v-list>
        <v-list-item-group>
          <template v-for="playlist in allPlaylists">
            <v-list-item
              :key="playlist.id"
              active-class="deep-purple--text text--accent-4"
            >
              <template>
                <v-list-item-content>
                  <v-list-item-title v-text="playlist.displayTitle"></v-list-item-title>
                </v-list-item-content>

                <v-list-item-action>
                  <v-checkbox
                    v-model="playlist.checked"
                    color="deep-purple accent-4"
                  ></v-checkbox>
                </v-list-item-action>
              </template>
            
            </v-list-item>
          </template>
        </v-list-item-group>
      </v-list>
    </v-card>

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
.pl-list{
  margin: auto;
  width: 300px;
}
</style>
