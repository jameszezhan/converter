<template>
  <div class="top-buttons">
    <ul>
        <li class="top-button"><b-button @click="authenticateYouTube">Authenticate Your YouTube Account</b-button></li>
        <li class="top-button"><b-button @click="fetchAllPlaylists">Get Playlists</b-button></li>
        <li class="top-button"><b-button @click="getTracksFromPlaylists">Get Songs</b-button></li>
        <li class="top-button"><b-button @click="fetchRecommendations">Get Recommendations</b-button></li>
        <li class="top-button"><b-button @click="authenticateSpotify">Authenticate Your Spotify Account</b-button></li>
        <li class="top-button"><b-button @click="startMigration">Migrate</b-button></li>
    </ul>
  </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: 'TopButtons',
  methods: {
    ...mapActions(["authenticateYouTube","fetchAllPlaylists","getTracksFromPlaylists","fetchRecommendations","authenticateSpotify","startMigration"])
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
