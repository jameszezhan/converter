<template>
  <div class="hello">
        <b-button @click="authenticateYouTube">authenticate</b-button>
        <b-button @click="fetchAllPlaylists">fetchAllPlaylists</b-button>
        <div v-for="playlist in allPlaylists" v-bind:key="playlist.id">
          <input v-model="playlist.checked" type="checkbox">
          {{playlist.snippet.title}}
        </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import Vue from 'vue'
import { Button } from 'buefy'
import 'buefy/dist/buefy.css'

Vue.use(Button)

export default {
  name: 'Playlists',
  methods: {
      ...mapActions(["fetchAllPlaylists", "authenticateYouTube"])
  },
  computed: mapGetters(['allPlaylists'])
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
