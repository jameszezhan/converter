<template>
  <div class="home">
      <router-link to="/step1">Click here to start</router-link>
  </div>
</template>

<script>
// @ is an alias to /src
import Vue from 'vue'
import { Button } from 'buefy'
import 'buefy/dist/buefy.css'

Vue.use(Button)

export default {
  name: 'Home'
}
</script>
