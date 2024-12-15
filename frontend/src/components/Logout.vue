<template>
    <button class="logout_btn" @click="handleLogoutClick">Выйти</button>
</template>

<script>
    export default{
        methods: {
            handleLogoutClick(){
                document.cookie = 'jwt_auth=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/;';
                this.$router.push("/start");
            }
        }
    }
</script>