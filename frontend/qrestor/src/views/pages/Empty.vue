<script setup>
import { useRoute } from 'vue-router';
import List from '@/views/pages/uikit/List.vue';


import AppConfig from '@/layout/AppConfig.vue';
const route = useRoute();
const id = route.params.code; 


</script>


<template>
    <div className="card">
        <h5>Empty Page</h5>
        <p>Here is code {{ id }}</p>
       
    </div>
     <List>
        
     </List>
     <AppConfig simple />
</template>


