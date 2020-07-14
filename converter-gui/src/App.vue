<template>
  <div id="app">
    <div id="nav">
      <router-link to="/step1">Step 1</router-link> |
      <router-link to="/step2">Step 2</router-link> |
      <router-link to="/step3">Step 3</router-link>
    </div>
    <router-view/>
    <b-modal 
      :active.sync="isDisplayErr"
      has-modal-card
      trap-focus
      :destroy-on-hide="false"
      aria-role="dialog"
      aria-modal>
      <DefaultApiErrorModal />
    </b-modal>
    <button @click=toggleErrModal></button>
  </div>
</template>

<script>
// @ is an alias to /src
import DefaultApiErrorModal from '@/components/Modals/DefaultApiErrorModal.vue'
import Vue from 'vue'
import { Modal } from 'buefy'
import { mapGetters, mapActions } from "vuex";

Vue.use(Modal)

export default {
  name: 'App',
  components: {
    DefaultApiErrorModal,
  },
  methods: {
      ...mapActions(["toggleErrModal"])
  },
  computed: mapGetters(['isDisplayErr'])
}
</script>

<style>
body{
  height: 100vh;
  widows: 100vw;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  max-width: 800px;
  margin: auto;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
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
.action{
  display: flex;
}
.button {
    margin: 20px 40px;
    min-width: 300px;
}
.small {
  width: 45%;
  min-width: unset;
  margin: 10px auto;
}

.list{
  width: 100%;
  margin: auto;
  text-align: left;
  box-shadow: none !important;
  padding: 10px 20px;
  max-height: 500px;
  height: 500px;
  overflow-y: auto;
  overflow-x: hidden;
}
.half{
  width: 45%;
  display: inline-block;
  float: left;
}
.right{
  float: right;
}
.hidden{
  display: none !important;
}
.disabled{
  pointer-events: none;
  opacity: 0.5;
}
.card{
    padding: 20px;
    border-radius: 20px;
}
</style>
