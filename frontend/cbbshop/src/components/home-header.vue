<template>
    <header>
      <h1>cbb-shop</h1>
      <div class="search-bar">
        <input type="text" placeholder="search" v-model="searchQuery" />
        <img src="@/assets/search.png" alt="Search" @click="performSearch" class="search-icon" />
      </div>
      <div>
        <button @click="toProfile">个人</button>
        <button @click="toLogin">登录</button>
      </div>
      
    </header>
  </template>
  
  <script>
  import request from "@/utils/request"; // 引入已配置的 axios 实例

  export default {
    name: "home-header",
    data() {
      return {
        searchQuery: ""
      };
    },
    methods: {
      performSearch() {
        this.$emit("search", this.searchQuery); // 将 searchQuery 发给父组件进行搜索
      },
      async toProfile() {
        try {
          // 发送请求到后端检查登录状态
          const response = await request.get('/api/user/profile');

          // 获取用户类型并跳转到对应后台
          const userType = response.data.userType;  // 假设返回的 JSON 包含 userType
          if (userType === 'buyer') {
            this.$router.push({ path: '/user-dashboard', name: 'UserDashboard' });
          } else if (userType === 'seller') {
            this.$router.push({ path: '/seller-dashboard', name: 'SellerDashboard' });
          } else {
            // 其他类型用户处理（可选）
            this.$router.push('/'); // 例如回到首页
          }
        } catch (error) {
          // 若未登录或出现错误，跳转到登录页面
          this.$router.push('/login');
        }
      },
      toLogin() {
        // 跳转到登录页面
        this.$router.push('/login');
      },
    }
  };
  </script>
  
  <style scoped>
    header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 10px 20px;
      background-color: #001;
      border-bottom: 1px solid #000000;
    }

    h1 {
      font-size: 1.5em;
      color: #eee;
    }

    input[type="text"] {
      width: 200px;
      padding: 5px;
      border: 1px solid #ccc;
      border-radius: 4px;
    }
    .search-icon {
      cursor: pointer;
      width: 15px;
      margin-left: 8px;
    }
    button {
      margin-left: 10px;
      padding: 5px 10px;
      background-color: #0D518A;
      color: #fff;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }

    button:hover {
      background-color: #0D518A;
    }
</style>

  