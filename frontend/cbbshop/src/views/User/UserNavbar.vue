<template>
    <div class="navbar">
        <h2>个人后台</h2>
      <nav>
        <router-link to="/user-dashboard/order">个人订单</router-link> |
        <router-link to="/user-dashboard/cart">购物车</router-link> |
        <router-link to="/user-dashboard/collection">收藏</router-link> |
        <router-link to="/user-dashboard/edit-profile">修改个人信息</router-link>
      </nav>
    </div>
  </template>
  
  <script>
  export default {};
  </script>
  
  <style scoped>
  .navbar {
    display: flex;
    flex-direction: column;
    text-align: center;
    background-color: #0D518A;
    width :100%;
  }
  h2 {
    color: white;
  }
  nav {
    margin-top: 10px;
    font-size: 16px;
    background-color: #DBEAF7;
  }
  nav a {
    margin-right: 5px;
  }
  </style>
  