<template>
  <div id="authForm">
    <input type="text" placeholder="Имя пользователя" name="login" class="auth_input" v-model="username">
    <input type="text" placeholder="Имя пользователя" name="login" class="auth_input" v-model="password">
    <input type="button" value="Войти" class="auth_btn" @click="handleLogin">
    <input type="button" value="Зарегистрироваться" class="auth_btn" @click="handleRegister">
    <p class="error_text">{{ errorMessage }}</p>
    <p class="success_text">{{ errorMessage }}</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      username: '',
      password: '',
      errorMessage: ''
    };
  },
  methods: {
    async handleLogin() {
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