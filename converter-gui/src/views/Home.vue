<template>
  <v-stepper v-model="e1">
    <v-stepper-header>
      <v-stepper-step editable=true :complete="e1 > 1" step="1">Retrieve</v-stepper-step>
      <v-divider></v-divider>
      <v-stepper-step editable=true :complete="e1 > 2" step="2">Magic</v-stepper-step>
      <v-divider></v-divider>
      <v-stepper-step editable=true :complete="e1 > 3" step="3">Migrate</v-stepper-step>
      <v-divider></v-divider>
      <v-stepper-step editable=true :complete="e1 > 4" step="4">Success</v-stepper-step>
    </v-stepper-header>
    <v-stepper-items>
      <v-stepper-content step="1">
        <Playlists />
        <v-btn color="primary" @click="e1 = 2">Continue</v-btn>
      </v-stepper-content>
        <v-stepper-content step="2">
          <YtTracks />
          <v-btn color="primary" @click="e1 = 3">Continue</v-btn>
          <v-btn color="primary" @click="e1 = 1">Go back</v-btn>
        </v-stepper-content>
      <v-stepper-content step="3">
          <SpTracks />
          <v-btn color="primary" @click="e1 = 4">Continue</v-btn>
          <v-btn color="primary" @click="e1 = 2">Go back</v-btn>
      </v-stepper-content>
      <v-stepper-content step="4">
        <div>Success</div>
      </v-stepper-content>
    </v-stepper-items>
  </v-stepper>
</template>

<script>
// @ is an alias to /src
import Vue from 'vue'
import SpTracks from '@/components/SpTracks.vue'
import YtTracks from '@/components/YtTracks.vue'
import Playlists from '@/components/Playlists.vue'


import { Button } from 'buefy'
import 'buefy/dist/buefy.css'

Vue.use(Button)

export default {
  name: 'Home',
    components: {
    YtTracks,SpTracks,Playlists
  },
  data () {
    return {
      e1: 1,
    }
  }
}
</script>
