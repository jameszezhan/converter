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
                    {{track.snippet.title}} 
                  </b-checkbox>
              </div>
            </div>
            <div class="action">
              <b-button @click="toggleAll(false)" type="is-danger" class="small">Deselect All</b-button>
              <b-button @click="toggleAll(true)" type="is-success" class="small">Select All</b-button>
            </div>
          </div>


          <div class="half right" v-bind:class="showList('spotify')">
            <div class="list">
              <div v-for="recommendation in allRecommendations" v-bind:key="recommendation.options[recommendation.chosenIndex].uri">
                  <b-checkbox v-model="recommendation.options[recommendation.chosenIndex].checked" type="is-success">
                    {{recommendation.options[recommendation.chosenIndex].name}}  
                  </b-checkbox>
              </div>
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
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
  name: 'YtTracks',
  methods: {
      ...mapActions(["getTracksFromPlaylists", "fetchRecommendations", "toggleAll"]),
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

