<template>
  <div class="logout-button">
    <button @click="logout">退出登录</button>
  </div>
</template>

<script>
import axios from "@/utils/request"; // 确保 axios 工具已正确配置

export default {
  methods: {
    async logout() {
      try {
        // 调用后端 API 进行用户注销
        const response = await axios.post('/api/user/logout');

        // 检查 API 响应是否成功
        if (response && response.status === 200) {
          alert(response.data?.message || '退出登录成功！'); // 提示后端返回的消息
          this.$router.push('/'); // 返回到首页或登录页
        } else {
          console.warn('非预期状态码:', response.status);
          alert('退出登录失败，请稍后重试！');
        }
      } catch (error) {
        // 捕获 axios 请求失败的情况
        if (error.response) {
          // 后端返回了错误响应
          console.error('后端错误:', error.response.data);
          alert(error.response.data?.message || '退出登录失败，请稍后重试！');
        } else if (error.request) {
          // 请求已发出，但未收到响应
          alert('无法连接到服务器，请检查网络连接后重试！');
        } else {
          // 其他类型的错误（如配置错误）
          console.error('请求错误:', error.message);
          alert('发生未知错误，请稍后重试！');
        }
      }
    },
  },
};
</script>


  
  <style scoped>
  .logout-button {
    position: fixed;
    top: 50px;
    right: 20px;
  }
  button {
    padding: 5px 16px;
    background-color: black;
    width: 100px;
    height: 40px;
    font-weight: bold;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  button:hover {
    background-color: #0d528aba;
  }
  </style>
  