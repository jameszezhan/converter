<template>
  <div class="home">
    <Playlists />
    <YtTracks />
    <SpTracks />
  </div>
</template>

<script>
// @ is an alias to /src
import Playlists from '@/components/Playlists.vue'
import YtTracks from '@/components/YtTracks.vue'
import SpTracks from '@/components/SpTracks.vue'

export default {
  name: 'Home',
  components: {
    Playlists,
    YtTracks,
    SpTracks
  }
}
</script>
