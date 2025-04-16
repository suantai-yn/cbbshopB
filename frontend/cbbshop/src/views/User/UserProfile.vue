<template>
  <div>
    <h3>个人信息</h3>
    <form @submit.prevent="submitForm">
      <label>密码:
        <input type="password" v-model="password" required />
      </label>
      <label>电话:
        <input type="text" v-model="phone" required />
      </label>
      <label>默认交易地址:
        <input type="text" v-model="address" required />
      </label>
      <label>问题:
        <input type="text" v-model="resetQuestion" required />
      </label>
      <label>答案:
        <input type="text" v-model="resetAnswer" required />
      </label>
      <button type="submit">提交</button>
    </form>
  </div>
</template>

<script>
import axios from "@/utils/request"; // 确保你有正确的 axios 请求工具

export default {
  data() {
    return {
      resetAnswer:'',
      resetQuestion:'',
      password: "",
      phone: "",
      address: "",
    };
  },
  methods: {
    // 提交表单
    submitForm() {
      // 验证输入
      if (!this.isValidInput()) {
        alert("请确保输入符合要求！");
        return;
      }

      const data = {
        password: this.password,
        contactNumber: this.phone,
        preferredLocation: this.address,
        resetAnswer:this.resetAnswer,
        resetQuestion: this.resetQuestion
      };

      // 发送 POST 请求
      axios
        .post("/api/user/updateInfo", data, {
          headers: {
            "Content-Type": "application/json",
          },
        })
        .then((response) => {
          const { success, message } = response.data || {};
          if (success) {
            alert(message || "操作成功！");
          } else {
            alert(message || "未知错误，请稍后重试！");
          }
        })
        .catch((error) => {
          console.error("请求失败:", error.response || error.message);
          alert("加载个人信息失败，请稍后重试！");
        });
    },
    // 输入验证
    isValidInput() {
      const phoneRegex = /^\d{11}$/; // 电话号码验证（11位数字）
      //const passwordRegex = /^[A-Za-z\d!@#$%^&*]+$/; // 密码正则验证

      
      if (
        this.password.length < 6 ||
        this.password.length > 15 
      ) {
        return false;
      }
      if (!phoneRegex.test(this.phone)) {
        return false;
      }
      if (this.address.length > 200) {
        return false;
      }
      return true;
    },
  },
};
</script>

<style scoped>
form {
  display: flex;
  flex-direction: column;
  margin-right: auto;
}

label {
  margin-bottom: 10px;
}

button {
  margin-top: 10px;
  width: 60px;
  margin-left: auto;
  margin-right: auto;
  background-color: #f3d794;
}
</style>
