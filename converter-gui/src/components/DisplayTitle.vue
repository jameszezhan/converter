<template>
    <span>
        <strong>{{track.name}}</strong>
        <span v-if="track.artists[0] !== undefined">{{" By " + track.artists[0]}}</span>
        <span v-if="track.artists[1] !== undefined">{{", " + track.artists[1]}}</span>
        <span v-if="track.artists[2] !== undefined">{{", " + track.artists[2]}}</span>

    </span>
</template>
<script>
export default {
  name: 'DisplayTitle',
  props: {
    track: { type: Object }
  }
}
</script>

<style scoped>

</style>