<template>
  <div class="hello">
      error area {{getErrorMessage}}
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
  name: 'ErrorArea',
  methods: {
      ...mapActions(["setError", "clearError"]),
  },
  computed: mapGetters(['getErrorMessage'])
}
</script>

