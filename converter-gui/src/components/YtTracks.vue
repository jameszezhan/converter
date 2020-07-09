<template>
  <div class="hello">
        <b-button @click="getTracksFromPlaylists">getTracksFromPlaylists</b-button>
        <b-button @click="fetchRecommendations">getRecommenationFromSpotify</b-button>
        <div class="list">
          <div v-for="track in allYtTracks" v-bind:key="track.id">
              <b-checkbox v-model="track.checked" type="is-success">
                {{track.snippet.title}} 
              </b-checkbox>
          </div>
        </div>
        <div v-for="recommendation in allRecommendations" v-bind:key="recommendation.key">
            {{recommendation.options[0].name}}  
        </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
  name: 'YtTracks',
  methods: {
      ...mapActions(["getTracksFromPlaylists", "fetchRecommendations"])
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
</style>
