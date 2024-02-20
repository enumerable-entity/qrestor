<script setup>
import { useRoute } from 'vue-router'
import List from '@/views/pages/uikit/List.vue'
import AppFooter from '@/layout/AppFooter.vue';

const route = useRoute()
const id = route.params.code
</script>

<template>
  <div class="layout-wrapper" :class="containerClass">
    <div class="layout-main-container">
      <div class="layout-main">
        <div className="card">
          <h5>Empty Page</h5>
          <p>Here is code {{ id }}</p>
        </div>
        <List> </List>
      </div>
       <app-footer></app-footer>
    </div>
    <div class="layout-mask"></div>
  </div>
</template>

<style scoped></style>