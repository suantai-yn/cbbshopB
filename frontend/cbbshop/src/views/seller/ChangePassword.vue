<template>
  <div class="change-password">
    <form @submit.prevent="submitPasswordChange">
      <label for="current-password">原密码</label>
      <input type="password" id="current-password" v-model="currentPassword" required />

      <label for="new-password">新密码</label>
      <input type="password" id="new-password" v-model="newPassword" required />

      <button type="submit">提交</button>
    </form>
  </div>
</template>

<script>
import axios from "@/utils/request"; // 确保引入了axios实例

export default {
  data() {
    return {
      currentPassword: '',  // 用户输入的原密码
      newPassword: '',      // 用户输入的新密码
    };
  },
  methods: {
    // 提交密码更改请求
    // 提交密码修改请求
submitPasswordChange() {
  axios
    .post('/api/user/changePassword', null, {
      params: {
        oldPassword: this.currentPassword,  // 传递原密码
        newPassword: this.newPassword,       // 传递新密码
      }
    })
    .then(response => {
      if (response.status === 200) {
        alert("密码修改成功！");
        this.currentPassword = '';  // 清空输入框
        this.newPassword = '';      // 清空输入框
      }
    })
    .catch(error => {
      if (error.response) {
        // 后端返回了错误信息
        alert("修改密码失败: " + error.response.data);
      } else {
        // 请求未到达后端
        console.error("修改密码请求失败:", error);
        alert("请求失败，请稍后重试！");
      }
    });
}
,
  },
};
</script>

<style scoped>
.change-password {
  display: flex;
  justify-content: center;
  align-items: flex-start; /* 向上对齐 */
  height: 80vh; /* 将高度减少，以便向上移动 */
  padding-top: 50px; /* 增加顶部填充以微调位置 */
}

form {
  display: flex;
  flex-direction: column;
  max-width: 350px; /* 放大表单宽度 */
  padding: 25px; /* 增加表单内边距 */
  border-radius: 10px;
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15); /* 更明显的阴影 */
  background-color: #fff;
  font-size: 1.1em; /* 放大字体 */
}

label {
  margin-bottom: 8px;
  font-weight: bold;
  color: #333;
}

input {
  margin-bottom: 18px;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  outline: none;
  font-size: 1em; /* 调整输入框字体大小 */
}

input:focus {
  border-color: #007BFF;
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.2);
}

button {
  padding: 12px;
  border: none;
  border-radius: 4px;
  background-color: #0056b3;
  color: white;
  font-weight: bold;
  font-size: 1em; /* 调整按钮字体大小 */
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #0056b3;
}
</style>
