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
                        <tr v-for="item in items" :key="item.id">
                            <td>{{ item.x }}</td>
                            <td>{{ item.y }}</td>
                            <td>{{ item.r }}</td>
                            <td>{{ item.result }}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
    export default {
  data() {
    return {
        items: []
    };
  },
  mounted() {
    this.loadShots();
  },
  methods: {
    async loadShots() {
      try {
        const shots = await axios.get('/backend/api/shots', {withCredentials: true});
        console.log(shots);
      } catch (error) {
        console.log(error);
      }
    },
  }
}
</script>