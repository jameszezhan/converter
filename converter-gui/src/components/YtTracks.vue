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
              <div v-for="track in allYtTracks" v-bind:key="track.id">
                  <b-checkbox v-model="track.checked" type="is-success">
                    {{track.name}} 
                  </b-checkbox>
              </div>
            </div>
            <div class="action">
              <b-button @click="isEditYeTrackNamesActive=true" class="small">Edit Names</b-button>
            </div>
            <div class="action">
              <b-button @click="toggleAll(false)" type="is-danger" class="small">Deselect All</b-button>
              <b-button @click="toggleAll(true)" type="is-success" class="small">Select All</b-button>
            </div>
          </div>


          <div class="half right" v-bind:class="showList('spotify')">
            <div class="list">
              <div v-for="recommendation in allRecommendations" v-bind:key="recommendation.options[recommendation.chosenIndex].id">
                  <b-checkbox v-model="recommendation.checked" type="is-success">
                    {{recommendation.options[recommendation.chosenIndex].name}}  
                  </b-checkbox>
              </div>
            </div>
            <div class="action">
              <b-button @click="isAltModalActive=true" class="small">Alternatives</b-button>
            </div>
            <div class="action">
              <b-button @click="toggleAll(false)" type="is-danger" class="small">Deselect All</b-button>
              <b-button @click="toggleAll(true)" type="is-success" class="small">Select All</b-button>
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
import { Modal, Select } from 'buefy'
import Alternatives from '@/components/Modals/Alternatives.vue'
import EditYtTrackNames from '@/components/Modals/EditYtTrackNames.vue'

Vue.use(Modal)
Vue.use(Select)
export default {
  name: 'YtTracks',
  components: {
    Alternatives,
    EditYtTrackNames
  },
  data(){
    return {
      isAltModalActive:false,
      isEditYeTrackNamesActive:false,
      localAltSelections:{}
    }
  },
  methods: {
      ...mapActions(["getTracksFromPlaylists", "fetchRecommendations", "toggleAll", "resetRecommendation"]),
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
      }
  },
  computed: mapGetters(['allPlaylists', 'allYtTracks', 'allRecommendations'])
}
</script>

