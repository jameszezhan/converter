<template>
  <div class="card">
    <div>Name: {{trackItem.name}}</div>
    <div>Artists: {{trackItem.artists}}</div>  
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
    name: 'InfoCard',
    props: ['trackItem'],
    computed: mapGetters({
        allYtTracks: 'allYtTracks'
    }),
    methods: {
      ...mapActions({
        resetRecommendation: "spotify/resetRecommendation"
      })
    }
}
</script>

