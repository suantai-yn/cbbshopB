<template>
    <div class="user-dashboard">
      <UserNavbar/>
      <div class="content">
        <router-view /> <!-- 在这里动态加载子组件 -->
        <LogoutButton /> <!-- 将退出按钮固定放置在 main 区域内 -->
      </div>      
      <home-footer />
    </div>
  </template>
  
  <script>
  import LogoutButton from '@/components/button-logout.vue';
  import UserNavbar from '@/views/User/UserNavbar.vue';
  import HomeFooter from '@/components/home-footer.vue';
  export default {
    components: {
     UserNavbar,
      LogoutButton,
      HomeFooter
    },
  };
  </script>
  
  <style scoped>
  .user-dashboard {
    display: flex;
    flex-direction: column;
    align-items: center;
    height: 100vh;
  }

  .content {
    flex: 1;
    width: 100%; /* 可以根据需要调整 */
    padding: 10px;
    margin-top: 5px;
    text-align: center;
  }
  
  </style>
  