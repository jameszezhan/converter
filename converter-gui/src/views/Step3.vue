<template>
  <div class="step3">
    <SpTracks />
  </div>
</template>

<script>
// @ is an alias to /src
import SpTracks from '@/components/SpTracks.vue'
import Vue from 'vue'
import { Button } from 'buefy'
import 'buefy/dist/buefy.css'

Vue.use(Button)

export default {
  name: 'Step3',
  components: {
    SpTracks,
  }
}
</script>
