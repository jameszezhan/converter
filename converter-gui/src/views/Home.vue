<template>
  <div class="home">
    <TopButtons />
    <!-- <Playlists />
    <YtTracks />
    <SpTracks /> -->
  </div>
</template>

<script>
// @ is an alias to /src
// import Playlists from '@/components/Playlists.vue'
// import YtTracks from '@/components/YtTracks.vue'
// import SpTracks from '@/components/SpTracks.vue'
import TopButtons from '@/components/TopButtons.vue'
import Vue from 'vue'
import { Button } from 'buefy'
import 'buefy/dist/buefy.css'

Vue.use(Button)

export default {
  name: 'Home',
  components: {
    TopButtons,
    // Playlists,
    // YtTracks,
    // SpTracks
  }
}
</script>
