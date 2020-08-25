<template>
  <div class="hello">
    <b-button @click="getTracksFromPlaylists" v-bind:class="isDisabled('youtube')">getTracksFromPlaylists</b-button>
    <b-button @click="fetchRecommendations" v-bind:class="isDisabled('spotify')">getRecommenationFromSpotify</b-button>
    
    <div v-bind:class="isDisabled('youtube') && isDisabled('spotify')">
      Navigate back to step 1 and fetch playlists first.
    </div>

    <div>
      <div class="half" v-bind:class="showList('youtube')">
        <div class="list">
          <v-row justify="center">
            <v-expansion-panels accordion>
              <v-expansion-panel
                v-for="(track) in allYtTracks"
                :key="track.id"
              >
              <div>

              </div>
              <div>
                <v-expansion-panel-header>
                    <v-checkbox
                      v-model="track.checked"
                      color="deep-purple accent-4"
                      :label="track.displayTitle"
                      @click.native="check($event)"
                    ></v-checkbox>
                </v-expansion-panel-header>
                <v-expansion-panel-content>
                  {{track.description}}
                </v-expansion-panel-content>
              </div>
              </v-expansion-panel>
            </v-expansion-panels>
          </v-row>
        </div>
        <div class="action">
          <b-button @click="toggleAll(false)" type="is-danger" class="small">Deselect All</b-button>
          <b-button @click="toggleAll(true)" type="is-success" class="small">Select All</b-button>
        </div>
        <div class="stats card">
          <div>I found <strong>{{allYtTracks.length}}</strong> videos</div>
          <div>
            <div>Please try to remove elements like artists name and special characters before moving forward</div>
            <div>You can do this with the 
              <b-button @click="isEditYeTrackNamesActive=true" type="is-small" class="small alt">Edit Names</b-button>
            button</div>
          </div>
        </div>
      </div>


      <div class="half right" v-bind:class="showList('spotify')">
        <div class="list">
          <v-row justify="center">
            <v-expansion-panels accordion>
              <v-expansion-panel
                v-for="recommendation in allRecommendations"
                v-bind:key="recommendation.options[recommendation.chosenIndex].id"
              >
                <v-expansion-panel-header>
                    <v-checkbox
                      v-model="recommendation.checked"
                      color="deep-purple accent-4"
                      :label="recommendation.options[recommendation.chosenIndex].displayTitle"
                      @click.native="check($event)"
                    ></v-checkbox>
                </v-expansion-panel-header>
                <v-expansion-panel-content>
                  {{recommendation.options[recommendation.chosenIndex].description}}
                </v-expansion-panel-content>
              </v-expansion-panel>
            </v-expansion-panels>
          </v-row>
          </div>
        <div class="action">
          <b-button @click="toggleAllSp(false)" type="is-danger" class="small">Deselect All</b-button>
          <b-button @click="toggleAllSp(true)" type="is-success" class="small">Select All</b-button>
        </div>
        <div class="stats card">
          <div>I found <strong>{{allRecommendations.length}}</strong> songs</div>
          <div>
            <div>You can select alternatives of these songs</div>
            <div>You can do this with the 
              <b-button @click="isAltModalActive=true" type="is-small" class="small alt">Alternatives</b-button>
            button</div>
          </div>
        </div>
      </div>

    </div>


    <b-button
      v-bind:class="canProceed()"
      tag="router-link"
      to="/step3"
      type="is-link">
      next
    </b-button>

    <b-modal 
      :active.sync="isAltModalActive"
      has-modal-card
      trap-focus
      :destroy-on-hide="false"
      aria-role="dialog"
      aria-modal>
      <Alternatives />
    </b-modal>
    
    <b-modal 
      :active.sync="isEditYeTrackNamesActive"
      trap-focus
      :destroy-on-hide="false"
      aria-role="dialog"
      aria-modal>
      <EditYtTrackNames />
    </b-modal>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import Vue from 'vue'
import { Modal, Select, Collapse } from 'buefy'
import Alternatives from '@/components/Modals/Alternatives.vue'
import EditYtTrackNames from '@/components/Modals/EditYtTrackNames.vue'

Vue.use(Modal)
Vue.use(Select)
Vue.use(Collapse)
export default {
  name: 'YtTracks',
  components: {
    Alternatives,
    EditYtTrackNames,
  },
  data(){
    return {
      isOpen: 0,
      isAltModalActive:false,
      isEditYeTrackNamesActive:false,
      localAltSelections:{}
    }
  },
  methods: {
      ...mapActions({
        getTracksFromPlaylists: "getTracksFromPlaylists", 
        fetchRecommendations: "spotify/fetchRecommendations", 
        toggleAll: "toggleAll", 
        toggleAllSp: "spotify/toggleAll", 
        resetRecommendation: "spotify/resetRecommendation"
      }),
      canProceed: function(){
        if(!this.allYtTracks.length > 0 || !this.allRecommendations.length > 0 ){
          return "hidden";
        }
      },
      showList: function(platform){
        if(platform =="spotify"){
          if(!this.allRecommendations.length > 0){
            return "hidden";
          }
        }else if(platform =="youtube"){
          if(!this.allYtTracks.length > 0){
            return "hidden";
          }
        }
      },
      isDisabled: function(platform){
        if(platform =="spotify"){
          if(!this.allYtTracks.length > 0){
            return "disabled";
          }
        }else if(platform =="youtube"){
          if(!this.allPlaylists.length > 0){
            return "disabled";
          }
        }
      },
      check: function(e) {
        e.cancelBubble = true;
        console.log('checkbox checked')
      },
  },
  computed: mapGetters({
    allPlaylists: 'allPlaylists', 
    allYtTracks: 'allYtTracks', 
    allRecommendations: 'spotify/allRecommendations'
  })
}
</script>

<style scoped>
.card-header-icon{
  justify-content: start;
  padding: 0px;
}
.spread-between{
  justify-content: space-between;
}
.small.alt{
  margin: auto;
  width: auto;
}
</style>