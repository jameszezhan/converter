<template>
  <div class="hello">
        <b-button @click="authenticateYouTube">Authenticate Your YouTube Account</b-button>
        <b-button @click="fetchAllPlaylists">Get Playlists</b-button>
        
        <div class="pl-list">
          <div v-for="playlist in allPlaylists" v-bind:key="playlist.id">
            <b-checkbox v-model="playlist.checked" type="is-success">
              {{playlist.snippet.title}}
            </b-checkbox>
          </div>
        </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import Vue from 'vue'
import { Button, Checkbox } from 'buefy'
import 'buefy/dist/buefy.css'

Vue.use(Button)
Vue.use(Checkbox)

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
.pl-list{
  width: fit-content;
    margin: auto;
    text-align: left;
}
</style>
