<template>
  <div id="authForm">
    <input type="text" placeholder="Имя пользователя" name="login" class="auth_input" v-model="username">
    <input type="text" placeholder="Имя пользователя" name="login" class="auth_input" v-model="password">
    <div class="auth_btn_wrapper">
        <input type="button" value="Войти" class="auth_btn" @click="handleLogin">
        <input type="button" value="Зарегистрироваться" class="auth_btn" @click="handleRegister">
    </div>
    <p class="error_text">{{ errorMessage }}</p>
  </div>
</template>

<script>
import axios from 'axios';


export default {
  data() {
    return {
      username: '',
      password: '',
      errorMessage: '',
    };
  },
  methods: {
    async validateForm() {
      if (this.username.length < 4){
        this.errorMessage = "Логин должен содержать больше 3-х символов"
        return false;
      }
      if (this.password.length < 4){
        this.errorMessage = "Пароль должен содержать больше 3-х символов"
        return false;
      }
      return true;
    },
    async handleLogin() {
      const isValid = await this.validateForm();
      if (!isValid) return;
      
      try {
        await axios.post('/backend/api/auth/login', {
          username: this.username,
          password: this.password
        }, {withCredentials: true});
        this.$router.push("/main");
      } catch (error) {
        this.errorMessage = 'Ошибка входа. Проверьте логин и пароль.';
        console.log(error);
      }
    },
    async handleRegister() {
      const isValid = await this.validateForm();
      if (!isValid) return;

      try {
        await axios.post('/backend/api/auth/register', {
          username: this.username,
          password: this.password
        }, {withCredentials: true});
        this.handleLogin();
        // Здесь вы можете перенаправить пользователя или выполнить другие действия
      } catch (error) {
        this.errorMessage = error.response.data.message;
      }
    }
  }
}
</script>