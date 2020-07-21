<template>
  <div class="card">
      Information
      <!-- <p>{{id}}</p> -->
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
    name: 'InfoCard',
    props: ['id'],
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

