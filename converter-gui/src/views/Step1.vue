<template>
  <div class="step1">
    <Playlists />
  </div>
</template>

<script>
// @ is an alias to /src
import Playlists from '@/components/Playlists.vue'
import Vue from 'vue'
import { Button } from 'buefy'
import 'buefy/dist/buefy.css'

Vue.use(Button)

export default {
  name: 'Step1',
  components: {
    Playlists,
  }
}
</script>
