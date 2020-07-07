<template>
  <div class="hello">
        <button @click="getTracksFromPlaylists">getTracksFromPlaylists</button>
        <div v-for="track in allYtTracks" v-bind:key="track.id">
            <input v-model="track.checked" type="checkbox" @change="toggleTracks(playlist.id)">
            {{track.snippet.title}} 
        </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
  name: 'YtTracks',
  methods: {
      ...mapActions(["getTracksFromPlaylists", "toggleTracks"])
  },
  computed: mapGetters(['allYtTracks'])
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
