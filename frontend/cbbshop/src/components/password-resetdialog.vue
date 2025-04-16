<template>
  <div v-if="isVisible" class="reset-dialog">
    <div class="reset-content">
      <h3>重置密码</h3>
      <div class="input-group">
        <label>用户名</label>
        <input type="text" v-model="username" @blur="fetchResetQuestion" placeholder="请输入用户名" />
        <img src="@/assets/search1.png" @click="fetchResetQuestion" class="search-icon" />
      </div>

      <div v-if="resetQuestion" class="input-group">
        <p>{{ resetQuestion }}</p>
      </div>

      <div v-if="resetQuestion" class="input-group">
        <label>问题回答</label>
        <input type="text" v-model="resetAnswer" placeholder="10字以内仅文字字母" />
      </div>

      <div v-if="resetQuestion" class="input-group">
        <label>新密码</label>
        <input type="password" v-model="newPassword" placeholder="6-15字符" />
      </div>

      <div class="button-group">
        <span :class="{ 'error-message': true, 'hidden': !errorMessage }">{{ errorMessage || '' }}</span>
        <button @click="resetPassword">重置密码</button>
        <button @click="closeDialog">取消</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/request";

export default {
  props: {
    isVisible: Boolean,
  },
  data() {
    return {
      username: "",
      resetQuestion: "",
      resetAnswer: "",
      newPassword: "",
      errorMessage: "",
    };
  },
  computed: {
    resetAnswerValid() {
      return /^[\u4e00-\u9fa5a-zA-Z]{1,10}$/.test(this.resetAnswer);
    },
    newPasswordValid() {
      return /^[a-zA-Z0-9!@#$%^&*()_+=-]{6,15}$/.test(this.newPassword);
    }
  },
  methods: {
    async fetchResetQuestion() {
      if (!this.username) {
        this.errorMessage = "请输入用户名";
        return;
      }
      try {
        const response = await axios.get(`/api/user/getResetQuestion?username=${this.username}`);
        this.resetQuestion = response.data;
        this.errorMessage = "";
      } catch (error) {
        this.errorMessage = error.response?.data || "用户名不存在";
      }
    },
    async resetPassword() {
      if (!this.resetAnswerValid) {
        this.errorMessage = "验证问题回答错误";
        return;
      }
      if (!this.newPasswordValid) {
        this.errorMessage = "新密码格式错误";
        return;
      }
      try {
        const response = await axios.post("/api/user/resetPassword_2", {
          username: this.username,
          answer: this.resetAnswer,
          newPassword: this.newPassword,
        });
        alert(response.data);
        this.errorMessage = "";
        this.closeDialog();
      } catch (error) {
        this.errorMessage = error.response?.data || "重置失败";
      }
    },
    closeDialog() {
      this.$emit("close");
      this.username = this.resetQuestion = this.resetAnswer = this.newPassword = this.errorMessage = "";
    },
  },
};
</script>

<style scoped>
.reset-dialog {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}
.reset-content {
  background-color: #ffffff;
  padding: 20px;
  border-radius: 8px;
  width: 350px;
  max-width: 90%;
  text-align: center;
}
.input-group {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 10px 0;
}
label {
  width: 100px;
  text-align: right;
  margin-right: 10px;
}
input {
  flex: 1;
  padding: 8px;
  border-radius: 5px;
  border: 1px solid #ccc;
}
.search-icon {
  width: 20px;
  height: 20px;
  cursor: pointer;
  margin-left: 5px;
}
.button-group {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin-top: 20px;
  width: 100%;
  gap: 10px;
}
.error-message {
  color: red;
  font-size: 12px;
  width: calc(100% - 120px);
  text-align: left;
  padding: 10px 0;
}
.hidden {
  visibility: hidden;
}
button {
  padding: 8px 16px;
  background-color: #0d518a;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  white-space: nowrap; /* 禁止文字换行 */
}

button:hover {
  background-color: #0a3b6c;
}
</style>
