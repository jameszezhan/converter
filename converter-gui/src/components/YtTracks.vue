<template>
  <div class="hello">
        <b-button @click="getTracksFromPlaylists">getTracksFromPlaylists</b-button>
        <b-button @click="fetchRecommendations">getRecommenationFromSpotify</b-button>
        
        <div>
          <div class="half" v-bind:class="showList()">
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


          <div class="half right" v-bind:class="showList()">
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
          return "disabled";
        }
      },
      showList: function(){
        if(!this.allYtTracks.length > 0){
          return "disabled";
        }
      }
  },
  computed: mapGetters(['allYtTracks', 'allRecommendations'])
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
.tracks-container{
  display: flex;
  flex-wrap: wrap;
  margin: auto;
}
.track{
  width: 33%;
}
</style>
