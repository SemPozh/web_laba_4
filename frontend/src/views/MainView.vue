<script setup>
import Graph from '@/components/Graph.vue';
import MainForm from '@/components/MainForm.vue';
import ClearButton from '@/components/ClearButton.vue';
import ResultsTable from '@/components/ResultsTable.vue';
</script>

<template>
    <div id="main-table">
        <MainForm/>
        <Graph/>
    </div>
    <ClearButton/>
    <ResultsTable/>
</template>
