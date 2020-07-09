<template>
  <div class="step2">
    <YtTracks />
  </div>
</template>

<script>
// @ is an alias to /src
import YtTracks from '@/components/YtTracks.vue'
import Vue from 'vue'
import { Button } from 'buefy'
import 'buefy/dist/buefy.css'

Vue.use(Button)

export default {
  name: 'Step2',
  components: {
    YtTracks,
  }
}
</script>
