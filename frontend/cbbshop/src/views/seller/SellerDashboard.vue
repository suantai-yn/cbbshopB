<template>
    <div class="seller-dashboard">
      <div class="head">
        <h2>商家后台</h2>
        <nav class="navbar">
          <router-link to="/seller-dashboard/products">商品列表</router-link> |
          <router-link to="/seller-dashboard/add-product">发布商品</router-link> |
          <router-link to="/seller-dashboard/ordermonitor">订单管理</router-link> |
          <router-link to="/seller-dashboard/change-password">修改密码</router-link> |
          <router-link to="/seller-dashboard/customer-info">客户信息</router-link>
        </nav>
      </div>
        <div class="content">
          <router-view /> <!-- 动态加载内容 -->
        </div>
        <LogoutButton /> <!-- 固定退出按钮组件 -->
        <home-footer />
    </div>
  </template>
  
  <script>
  import LogoutButton from '@/components/button-logout.vue';
  import HomeFooter from '../../components/home-footer.vue';
  export default {
    components: {
      LogoutButton,
      HomeFooter
    },
  };
  </script>
  
  <style scoped>
  .seller-dashboard {
    display: flex;
    flex-direction: column;
    align-items: center;
    height: 100vh;
    
  }
  .head {
    background-color: #0D518A;
    width: 100%;
    color:white;
  }
.navbar {
  margin-top: 10px;
  font-size: 16px;
  text-align: center;
  background-color: #DBEAF7;
    
}
  .content {
    flex: 1;
    width: 80%; /* 可根据需求调整 */
    padding: 20px;
    text-align: center;
  }
  
  </style>
  