<template>
  <div id="app">
    <div id="nav">
      <router-link to="/step1">Step 1</router-link> |
      <router-link to="/step2">Step 2</router-link> |
      <router-link to="/step3">Step 3</router-link>
    </div>
    <router-view/>
    <ErrorArea />
  </div>
</template>

<script>
// @ is an alias to /src
import ErrorArea from '@/components/ErrorArea.vue'

export default {
  name: 'App',
  components: {
    ErrorArea
  }
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
  max-width: 1080px;
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
  width: 49%;
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
.spread-between{
  justify-content: space-between;
}
</style>
