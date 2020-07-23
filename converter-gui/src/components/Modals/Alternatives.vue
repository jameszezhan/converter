<template>
    <div class="card">
        <div>Select alternatives</div>
        <div v-for="recommendation in allRecommendations" v-bind:key="recommendation.options[recommendation.chosenIndex].uri">
            <b-select 
                v-bind:placeholder=recommendation.options[recommendation.chosenIndex].name
                v-model="recommendation.chosenIndex"
                expanded>

                <option 
                v-for="(option, index) in recommendation.options" 
                v-bind:key="option.uri"
                v-bind:value="index">
                <DisplayTitle :track = option />
                 
                </option>
            </b-select>
        </div>
        <b-button @click="$parent.close()">Cancel</b-button>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import Vue from 'vue'
import { Modal, Select } from 'buefy'
import DisplayTitle from '@/components/DisplayTitle.vue'

Vue.use(Modal)
Vue.use(Select)
export default {
  name: 'Alternatives',
  components: {
      DisplayTitle
  },
  methods: {
      ...mapActions({resetRecommendation: "spotify/resetRecommendation"}),
  },
  computed: mapGetters({
      allRecommendations: 'spotify/allRecommendations'
    })
}
</script>

<style scoped>
.card{
    max-height: 600px;
    overflow-y: scroll;
}
</style>