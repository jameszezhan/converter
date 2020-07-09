<template>
  <div class="hello">
        <b-button @click="authenticateSpotify">Authorize</b-button>
        <b-button @click="startMigration">Migrate</b-button>
  </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: 'SpTracks',
  methods: {
    ...mapActions(["fetchRecommendations", "startMigration", "authenticateSpotify"])
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
