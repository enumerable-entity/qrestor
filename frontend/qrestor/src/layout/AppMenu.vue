<script setup>
import { ref } from 'vue'

import AppMenuItem from './AppMenuItem.vue'

const model = ref([
  
  {
    label: 'Managenent',
    icon: 'pi pi-fw pi-briefcase',
    items: [
      {
        label: 'Selling points',
        icon: 'pi pi-fw pi-pencil',
        to: '/pages/spoints'
      },
      {
        label: 'Menus',
        icon: 'pi pi-fw pi-bars',
        items: [
          {
            label: 'Menus',
            icon: 'pi pi-fw pi-book',
            to: '/management/menus',
          },
          {
            label: 'Menu items',
            icon: 'pi pi-fw pi-pencil',
            to: '/pages/menu2'
          }
        ]
      }
    ]
  }
  
])
</script>

<template>
  <ul class="layout-menu">
    <template v-for="(item, i) in model" :key="item">
      <app-menu-item v-if="!item.separator" :item="item" :index="i"></app-menu-item>
      <li v-if="item.separator" class="menu-separator"></li>
    </template>
  </ul>
</template>

<style lang="scss" scoped></style>
