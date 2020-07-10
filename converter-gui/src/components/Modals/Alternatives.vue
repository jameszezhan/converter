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
                {{option.name}} 
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
Vue.use(Modal)
Vue.use(Select)
export default {
  name: 'Alternatives',
  methods: {
      ...mapActions(["resetRecommendation"]),
  },
  computed: mapGetters(['allRecommendations'])
}
</script>

