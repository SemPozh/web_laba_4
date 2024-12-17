<template>
  <div id="main-table">
    <div class="gui-element" id="input-block">
      <div id="form">

        <label id="x-label">Выберите X-координату</label>
        <div>
          <div>
            <MainFormButton v-for="value in xValues" :key="value"
                            :value="value"
                            fieldName="x"
                            :selectedField="this.selectedX"
                            :handleClick="handleXClick"/>
          </div>
        </div>

        <label id="y-label" for="y-inp">Выберите Y-координату</label>
        <div>
          <input type="text" placeholder="Введите Y в (-3...3)" class="y-choose_input" id="y-inp" name="y"
                 @input="handleYInputChange"/>
        </div>

        <label id="r-label">Выберите R</label>
        <div>
          <div>
            <MainFormButton v-for="value in rValues" :key="value"
                            :value="value"
                            fieldName="r"
                            :selectedField="this.selectedR"
                            :handleClick="handleRClick"/>
          </div>
        </div>
        <input type="button" value="Отправить" class="submit_button" id="submitButton" @click="handleFormSubmit"/>
        <p class="error_text" id="Incorrect">{{ errorMessage }}</p>
      </div>
    </div>
    
    <div class="gui-element" id="visualization-block">
      <div id="frame">
        <canvas id="figureCanvas" width="100" height="100" @click="handleCanvasClick"/>
      </div>
    </div>
  </div>

  <div id="clearButton">
    <input type="button" value="Очистить таблицу" @click="handleClearButtonClick">
  </div>
  
  <DataTable :shots="this.shots"/>
</template>

<script>
import axios from 'axios';
import MainFormButton from './MainFormButton.vue';
import DataTable from './DataTable.vue';

export default {
  components: {MainFormButton, DataTable},
  data() {
    return {
      selectedR: null,
      selectedX: null,
      xValues: [-4, -3, -2, -1, 0, 1, 2, 3, 4],
      rValues: [-4, -3, -2, -1, 0, 1, 2, 3, 4],
      canvas: null,
      ctx: null,
      intervalSize: null,
      y: null,
      yInput: null,
      errorMessage: "",
      shots: []
    }
  },
  mounted() {
    this.getUserShots();

    this.yInput = document.getElementById('y-inp');

    this.canvas = document.getElementById('figureCanvas');
    this.canvas.width = this.canvas.parentElement.offsetWidth;
    this.canvas.height = this.canvas.parentElement.offsetHeight;
    this.ctx = this.canvas.getContext('2d');
    this.intervalSize = this.canvas.height / 10;
    this.drawCoordinateLines();
  },
  methods: {
    handleRClick(value) {
      this.selectedR = value;
      if (value > 0) {
        this.drawArea(value);
        for (let shot of this.shots) {
          if (shot.r == value) {
            this.drawPoint(shot.x, shot.y, shot.result);
          }
        }
      } else {
        this.clearCanvas();
        this.drawCoordinateLines();
      }
    },
    handleXClick(value) {
      this.selectedX = value;
    },
    async handleCanvasClick(event) {
      const rect = this.canvas.getBoundingClientRect();
      let x = event.clientX - rect.left;
      let y = event.clientY - rect.top;
      let xMapped = (x - this.canvas.width / 2) / this.intervalSize;
      let yMapped = (this.canvas.height / 2 - y) / this.intervalSize;
      if (this.selectedR == null) {
        this.errorMessage = "Не задан параметр R!"
        return;
      }
      if (this.selectedR <= 0) {
        this.errorMessage = "R должен быть положительным!"
        return;
      }
      try {
        const response = await axios.post('/backend/api/shots/add', {
          "x": xMapped,
          "y": yMapped,
          "r": this.selectedR,
          "byAreaClick": true
        });
        this.shots.push({
          "x": response.data.x,
          "y": response.data.y,
          "r": response.data.r,
          "result": response.data.result
        });
        this.drawPoint(response.data.x, response.data.y, response.data.result);
      } catch (error) {
        this.errorMessage = error.response.data.message;
      }
    },
    async handleFormSubmit() {
      if (this.selectedX == null) {
        this.errorMessage = "Не выбрана координата X!"
        return;
      }
      if (this.selectedR == null) {
        this.errorMessage = "Не задан параметр R!"
        return;
      }
      if (this.y == null) {
        this.errorMessage = "Координата Y не задана или некорректна"
        return;
      }
      if (this.selectedR <= 0) {
        this.errorMessage = "R должен быть положительным!"
        return;
      }
      try {
        const response = await axios.post('/backend/api/shots/add', {
          "x": this.selectedX,
          "y": this.y,
          "r": this.selectedR,
          "byAreaClick": false
        });
        this.shots.push({
          "x": this.selectedX,
          "y": this.y,
          "r": this.selectedR,
          "result": response.data.result
        });
        this.drawPoint(response.data.x, response.data.y, response.data.result);
      } catch (error) {
        this.errorMessage = error.response.data.message;
      }
    },
    async getUserShots() {
      try {
        const response = await axios.get("/backend/api/shots");
        for (let shot of response.data) {
          this.shots.push({
            "x": shot.x,
            "y": shot.y,
            "r": shot.r,
            "result": shot.result,
          });
        }
      } catch (error) {
        console.error(error);
      }
    },
    async handleClearButtonClick() {
      try {
        const response = await axios.delete("/backend/api/shots/clear");
        this.shots.length = 0;
        this.clearCanvas();
        this.drawArea(this.selectedR);
      } catch (error) {
        console.error(error);
      }
    },
    handleYInputChange() {
      const regex = /^-?[0-2](\.\d+)?$/;
      // Matches a number between 0 and 2, which can be negative,
      // and may have an optional decimal part with up to 16 digits after the decimal point.
      // Examples: "0", "1", "2", "-1.5", "0.123", "2.1234567890123456" (valid)
      // Examples: "3", "-3.12345678901234567" (invalid)

      if (this.yInput.value === "") {
        this.yInput.classList.remove("incorrect");
        this.y = null;
        return
      }
      if (regex.test(this.yInput.value)) {
        this.yInput.classList.remove("incorrect");
        this.y = parseFloat(this.yInput.value);
      } else {
        this.yInput.classList.add("incorrect");
        this.y = null;
      }
    },
    drawArea(r) {
      this.clearCanvas();
// rectangle
      this.ctx.fillStyle = "#2130ff";
      this.ctx.fillRect(this.canvas.width / 2, this.canvas.height / 2 - this.intervalSize * (r / 2), this.intervalSize * r, this.intervalSize * (r / 2));

// circle-sector
      this.ctx.beginPath()
      this.ctx.moveTo(this.canvas.width / 2, this.canvas.height / 2);
      this.ctx.lineTo(this.canvas.width / 2, this.canvas.height / 2 - this.intervalSize * (r / 2));
      this.ctx.moveTo(this.canvas.width / 2, this.canvas.height / 2);
      this.ctx.lineTo(this.canvas.width / 2 - this.intervalSize * (r / 2), this.canvas.height / 2);
      this.ctx.arc(this.canvas.width / 2, this.canvas.height / 2, this.intervalSize * (r / 2), Math.PI, -Math.PI / 2, false);
      this.ctx.closePath();
      this.ctx.fill();


// triangle
      this.ctx.beginPath()
      this.ctx.moveTo(this.canvas.width / 2 - this.intervalSize * r, this.canvas.height / 2);
      this.ctx.lineTo(this.canvas.width / 2, this.canvas.height / 2);
      this.ctx.lineTo(this.canvas.width / 2, this.canvas.height / 2 + this.intervalSize * (r / 2));
      this.ctx.closePath();
      this.ctx.fill();
//
      this.drawCoordinateLines();
    },

    clearCanvas() {
      this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
    },

    drawCoordinateLines() {
// draw coordinates plane
      this.ctx.beginPath()
      this.ctx.fillStyle = "#000";

      this.ctx.moveTo(this.canvas.width / 2, 0);
      this.ctx.lineTo(this.canvas.width / 2, this.canvas.height);


      this.ctx.moveTo(0, this.canvas.height / 2);
      this.ctx.lineTo(this.canvas.width, this.canvas.height / 2);


      this.ctx.moveTo(this.canvas.width / 2, 0);
      this.ctx.lineTo(this.canvas.width / 2 + 10, 15);


      this.ctx.moveTo(this.canvas.width / 2, 0);
      this.ctx.lineTo(this.canvas.width / 2 - 10, 15);


      this.ctx.moveTo(this.canvas.width, this.canvas.height / 2);
      this.ctx.lineTo(this.canvas.width - 15, this.canvas.height / 2 + 10);


      this.ctx.moveTo(this.canvas.width, this.canvas.height / 2);
      this.ctx.lineTo(this.canvas.width - 15, this.canvas.height / 2 - 10);

      for (let i = -4; i <= 4; i++) {
        if (i === 0) {
          continue
        }
        this.ctx.moveTo(this.canvas.width / 2 - 7, this.canvas.height / 2 + this.intervalSize * i);
        this.ctx.lineTo(this.canvas.width / 2 + 7, this.canvas.height / 2 + this.intervalSize * i);

        this.ctx.moveTo(this.canvas.width / 2 + this.intervalSize * i, this.canvas.height / 2 - 7);
        this.ctx.lineTo(this.canvas.width / 2 + this.intervalSize * i, this.canvas.height / 2 + 7);
      }

      // text
      this.ctx.font = "18px Arial";
      for (let i = -4; i <= 4; i++) {
        if (i === 0) {
          continue;
        }
        if (i < 0) {
          this.ctx.fillText(i.toString(), this.canvas.width / 2 + this.intervalSize * i - 11, this.canvas.height / 2 - 10);
          this.ctx.fillText(i.toString(), this.canvas.width / 2 - 25, this.canvas.height / 2 - this.intervalSize * i + 7);
        } else {
          this.ctx.fillText(i.toString(), this.canvas.width / 2 + this.intervalSize * i - 6, this.canvas.height / 2 - 10);
          this.ctx.fillText(i.toString(), this.canvas.width / 2 - 20, this.canvas.height / 2 - this.intervalSize * i + 7);
        }
      }
      this.ctx.closePath();
      this.ctx.stroke();
    },

    drawPoint(x, y, result) {
      if (result) {
        this.ctx.fillStyle = "#2fc908";
      } else {
        this.ctx.fillStyle = "#ff0000";
      }
      this.ctx.beginPath();
      this.ctx.arc(this.canvas.width / 2 + x * this.intervalSize, this.canvas.height / 2 - y * this.intervalSize, 3, 0, Math.PI * 2);
      this.ctx.closePath();
      this.ctx.fill();
    }
  }
}
</script>