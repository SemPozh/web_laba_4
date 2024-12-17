<template>
  <div id="data-table">
    <div class="gui-element" id="data-block">
      <div>
        <table id="results">
          <thead>
          <tr>
            <th>X</th>
            <th>Y</th>
            <th>R</th>
            <th>Попадание</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="shot in shots" :key="shot.id">
            <td>{{ shot.x }}</td>
            <td>{{ shot.y }}</td>
            <td>{{ shot.r }}</td>
            <td>{{ shot.result ? 'Попадание' : 'Промах' }}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    props: {
      shots: Array
    }
  }
</script>