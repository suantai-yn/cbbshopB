<template>
  <div class="login-page">
    <div class="login-box">
      <h3>登录</h3>
      
      <!-- 用户名输入框 -->
      <div class="input-group">
        <label>用户名</label>
        <input type="text" v-model="username" placeholder="15字以内" />
        <span v-if="usernameError" class="error">{{ usernameError }}</span>
      </div>
      
      <!-- 密码输入框 -->
      <div class="input-group">
        <label>密码</label>
        <input type="password" v-model="password" placeholder="6-15个字符" />
        <span v-if="passwordError" class="error">{{ passwordError }}</span>
      </div>
      
      <!-- 按钮 -->
      <div class="buttons">
        <a @click="showResetDialog = true" class="forgot-password">忘记密码？</a>
        <button @click="register">注册</button>
        <button @click="login">登录</button>
      </div>
    </div>

    <!-- 重置密码弹窗组件 -->
    <ResetPasswordDialog v-if="showResetDialog" :isVisible="showResetDialog" @close="showResetDialog = false" />
  </div>
</template>

<script>
import ResetPasswordDialog from "@/components/password-resetdialog.vue";
import axios from "@/utils/request";

export default {
  components: { ResetPasswordDialog },
  data() {
    return {
      username: "",
      password: "",
      showResetDialog: false,
      usernameError: "",
      passwordError: ""
    };
  },
  methods: {
    register() {
      this.$router.push("/registerpage");
    },
    async login() {
      this.usernameError = this.passwordError = "";
      if (!this.username || !this.password) {
        this.passwordError = "用户名或密码不能为空";
        return;
      }

      try {
        const response = await axios.post("/api/user/login", {
          username: this.username,
          password: this.password
        });

        const userType = response.data.userType;
        if (userType === "buyer") {
          this.$router.push("/user-dashboard");
        } else {
          this.$router.push("/seller-dashboard");
        }
      } catch (error) {
        // 使用后端返回的错误消息
        this.passwordError = error.response?.data?.message || "登录失败，请重试";
      }
    }
  }
};
</script>

<style scoped>
.login-page {
  background-image: url('@/assets/login/login1.jpg');
  background-size: cover;
  display: flex;
  justify-content: right;
  align-items: center;
  height: 100vh;
}

.login-box {
  background-color: #ffffffcb;
  padding: 20px;
  width: 450px;
  margin-right: 10%;
  text-align: center;
  border-radius: 20px;
}
h3 {
  margin-bottom: 35px;
}
.input-group {
  display: flex;
  align-items: center;
  margin-top: 20px;
  margin-bottom: 20px;
}
label {
  width: 100px;
  text-align: right;
  margin-right: 20px;
}
input {
  flex: 1;
  padding: 8px;
  border-radius: 5px;
  max-width: 250px;
  border: 1px solid #ccc;
}
.buttons {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  margin: 20px 0 0;
}
button {
  margin: 15px;
  padding: 8px 18px;
  background-color: #0D518A;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
button:hover {
  background-color: #0A3B6C;
}
.error {
  color: red;
  font-size: 12px;
  margin-top: 4px;
}
.forgot-password {
  margin-right: 10px;
  color: #007bff;
  cursor: pointer;
  text-decoration: underline;
  font-size: 12px;
  padding: 0 10px;
}
</style>