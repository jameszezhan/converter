<template>
  <div class="home">
    <Playlists />
    <YtTracks />
  </div>
</template>

<script>
// @ is an alias to /src
import Playlists from '@/components/Playlists.vue'
import YtTracks from '@/components/YtTracks.vue'

export default {
  name: 'Home',
  components: {
    Playlists,
    YtTracks
  }
}
</script>
