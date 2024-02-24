<script setup>
import { ref } from 'vue'

import AppMenuItem from './AppMenuItem.vue'

const model = ref([
  {
    label: 'Dashboard',
    items: [{ label: 'Home', icon: 'pi pi-fw pi-home', to: '/dashboard' }]
  },
  {
    label: 'Managenent',
    icon: 'pi pi-fw pi-briefcase',
    items: [
      {
        label: 'Selling points',
        icon: 'pi pi-fw pi-building',
        to: '/management/sellingPoints'
      },
      {
        label: 'Menus',
        icon: 'pi pi-fw pi-align-left',
        items: [
          {
            label: 'Menu',
            icon: 'pi pi-fw pi-sign-in',
            to: '/auth/login'
          },
          {
            label: 'Menu items',
            icon: 'pi pi-fw pi-times-circle',
            to: '/auth/error'
          },
          {
            label: 'Menu items options',
            icon: 'pi pi-fw pi-lock',
            to: '/auth/access'
          },
          {
            label: 'Menu items options positions',
            icon: 'pi pi-fw pi-lock',
            to: '/auth/access'
          }
        ]
      },
      {
        label: 'QR codes',
        icon: 'pi pi-fw pi-pencil',
        to: '/pages/crud'
      }
    ]
  },
  {
    label: 'Orders',
    icon: 'pi pi-fw pi-building',
    items: [
      {
        label: 'Orders',
        icon: 'pi pi-fw pi-building',
        to: '/management/sellingPoints'
      }
    ]
  },
  {
    label: 'Kitchen board',
    icon: 'pi pi-fw pi-pencil',
    items: [
      {
        label: 'Orders',
        icon: 'pi pi-fw pi-building',
        to: '/management/sellingPoints'
      },
      {
        label: 'Order items',
        icon: 'pi pi-fw pi-building',
        to: '/management/sellingPoints'
      }
    ]
  },
  {
    label: 'Feedbacks',
    icon: 'pi pi-fw pi-pencil',
    items: [
      {
        label: 'Feedbacks',
        icon: 'pi pi-fw pi-building',
        to: '/management/sellingPoints'
      }
    ]
  },
  {
    label: 'Settings',
    icon: 'pi pi-fw pi-pencil',
    items: [
      {
        label: 'Payment settings',
        icon: 'pi pi-fw pi-building',
        to: '/management/sellingPoints'
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
