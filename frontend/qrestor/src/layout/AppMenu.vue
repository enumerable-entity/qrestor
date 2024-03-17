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
        icon: 'pi pi-fw pi-home',
        to: '/pages/spoints',
      },
      {
        label: 'Menus',
        icon: 'pi pi-fw pi-book',
        items: [
          {
            label: 'All Menus',
            icon: 'pi pi-fw pi-list',
            to: '/management/menus',
          },
          {
            label: 'Menu items',
            icon: 'pi pi-fw pi-bars',
            to: '/management/menus/menu-items',
          },
          {
            label: 'Menu items options',
            icon: 'pi pi-fw pi-align-center',
            to: '/management/menus/menu-items/menu-item-options',
          },
          {
            label: 'Options positions',
            icon: 'pi pi-fw pi-server',
            to: '/management/menus/menu-item-options/options-positions',
          },
          {
            label: 'Ingredients',
            icon: 'pi pi-fw pi-server',
            to: '/management/menus/ingredients',
          }
        ]
      },
      {
        label: 'Orders',
        icon: 'pi pi-fw pi-shopping-cart',
        items: [
          {
            label: 'Active orders',
            icon: 'pi pi-fw pi-clock',
            to: '/management/orders/active',
          },
          {
            label: 'History',
            icon: 'pi pi-fw pi-history',
            to: '/management/orders/history',
          }
        ]
      },
      {
        label: 'Kitchen Board',
        icon: 'pi pi-fw pi-pencil',
        items: [
          {
            label: 'Live Board',
            icon: 'pi pi-fw pi-server',
            to: '/kitchen/live-board',
          }
        ]
      },
      {
        label: 'QR codes',
        icon: 'pi pi-fw pi-qrcode',
        items: [
          {
            label: 'Mappings',
            icon: 'pi pi-fw pi-arrow-right-arrow-left',
            to: '/qr-codes-management'
          },
          {
            label: 'Configuration',
            icon: 'pi pi-fw pi-wrench',
            to: '/qr-codes-config'
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
