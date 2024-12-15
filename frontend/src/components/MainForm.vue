<template>
  <div class="gui-element" id="input-block">
    <div id="form">
      <label id="x-label">Выберите X-координату</label>
      <div>
        <div>
          <div class="form_radio_btn" v-for="value in xValues" :key="value">
            <input type="button" :value="value" class="r-choose_input checkbox_x" name="x" :id="'x-' + value"
                   :class="{ choosen: choosenX === value }" @click="handleXClick(value)">
            <label :for="'x-' + value">{{ value }}</label>
          </div>
        </div>
      </div>
      <label id="y-label" for="y-inp">Выберите Y-координату</label>
      <div>
        <input type="text" placeholder="Введите Y в (-3...3)" class="y-choose_input" id="y-inp" name="y" @input="handleYInputChange"/>
      </div>
      <label id="r-label">Выберите R</label>
      <div>
        <div>
          <div class="form_radio_btn" v-for="value in rValues" :key="value">
            <input type="button" :value="value" class="r-choose_input checkbox_x" name="r" :id="'r-' + value"
                   @click="handleRClick(value)" :class="{ choosen: choosenR === value }">
            <label :for="'r-' + value">{{ value }}</label>
          </div>
        </div>
      </div>
      <input type="button" value="Отправить" class="submit_button" id="submitButton" @click="handleFormSubmit"/>
      <p class="error_text" id="Incorrect">{{errorMessage}}</p>
    </div>
  </div>
  <div class="gui-element" id="visualization-block">
    <div id="frame">
      <canvas id="figureCanvas" width="100" height="100" @click="handleCanvasClick"/>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      choosenR: null,
      choosenX: null,
      xValues: [-4, -3, -2, -1, 0, 1, 2, 3, 4],
      rValues: [-4, -3, -2, -1, 0, 1, 2, 3, 4],
      canvas: null,
      ctx: null,
      intervalSize: null,
      y: null,
      yInput: null,
      errorMessage: ""
    }
  },
  mounted() {
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
      this.choosenR = value;
      if (value > 0){
        this.drawArea(value);
      } else {
        this.clearCanvas();
        this.drawCoordinateLines();
      }
    },
    handleXClick(value) {
      this.choosenX = value;
    },
    handleCanvasClick(){

    },
    async handleFormSubmit(){
        if (this.choosenX==null){
            this.errorMessage = "Не выбрана координата X!"
            return;
        }
        if (this.choosenR==null){
            this.errorMessage = "Не задан параметр R!"
            return;
        }
        if (this.y==null){
            this.errorMessage = "Не задана координата Y!"
            return;
        }
        if (this.choosenR <= 0){
            this.errorMessage = "R должен быть положительным!"
        }
        const response = await axios.post('/backend/api/shots/add', {
            "x": this.choosenX,
            "y": this.y,
            "r": this.choosenR,
            "byAreaClick": false
        });
        console.log(response);
    },
    handleYInputChange(){
      const regex = /^-?[0-2](\.\d+)?$/;
      // Matches a number between 0 and 2, which can be negative,
      // and may have an optional decimal part with up to 16 digits after the decimal point.
      // Examples: "0", "1", "2", "-1.5", "0.123", "2.1234567890123456" (valid)
      // Examples: "3", "-3.12345678901234567" (invalid)

      if (this.yInput.value===""){
        this.yInput.classList.remove("incorrect");
        this.y = null;
        return
      }
      if (regex.test(this.yInput.value)){
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
      this.ctx.lineTo(this.canvas.width / 2, this.canvas.height / 2 + this.intervalSize * (r/2));
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
      console.log(x)
      console.log(y)
      console.log(result);
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