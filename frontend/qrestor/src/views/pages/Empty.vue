<script setup>
import { useRoute } from 'vue-router';

const route = useRoute();
const id = route.params.code; 
</script>


<template>
    <div className="card">
        <h5>Empty Page</h5>
        <p>Here is code {{ id }}</p>
    </div>
</template>


