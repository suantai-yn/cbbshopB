<template>
  <div class="register-page">
    <div class="register-box">
      <h3>用户注册</h3>

      <div class="input-group">
        <label>用户名</label>
        <input 
          type="text" 
          v-model="username" 
          placeholder="15字以内，仅允许文字、字母、下划线" 
        />
      </div>

      <div class="input-group">
        <label>密码</label>
        <input 
          type="password" 
          v-model="password" 
          placeholder="6-15个字符，可包含字母、数字、特殊符号" 
        />
      </div>

      <div class="input-group">
        <label>联系电话</label>
        <input 
          type="text" 
          v-model="contact" 
          placeholder="11位数字" 
        />
      </div>

      <div class="input-group">
        <label>倾向交易地址</label>
        <input 
          type="text" 
          v-model="address" 
          placeholder="100字以内，可包含文字、数字、符号" 
        />
      </div>

      <div class="input-group">
        <label>身份</label>
        <div class="radio-group">
          <label><input type="radio" v-model="userType" value="买家" /> 买家</label>
          <label><input type="radio" v-model="userType" value="卖家" /> 卖家</label>
        </div>
      </div>

      <div class="input-group">
        <label>验证问题</label>
        <input 
          type="text" 
          v-model="resetQuestion" 
          placeholder="15字以内，仅文字和字母" 
        />
      </div>

      <div class="input-group">
        <label>回答</label>
        <input 
          type="text" 
          v-model="resetAnswer" 
          placeholder="10字以内，仅文字和字母" 
        />
      </div>

      <div class="buttons">
        <!-- 错误提示 -->
        <span v-if="globalError" class="error-message">{{ globalError }}</span>
        <button @click="submit">提交</button>
        <button @click="goBack">返回</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/request";

export default {
  name: "RegisterPage",
  data() {
    return {
      username: "",
      password: "",
      contact: "",
      address: "",
      userType: "",
      resetQuestion: "",
      resetAnswer: "",
      globalError: "", // 显示单项错误信息
    };
  },
  methods: {
    validateUsername() {
      if (!this.username || !/^[\w\u4e00-\u9fa5]{1,15}$/.test(this.username)) {
        return "用户名不符合要求（15字以内，仅允许文字、字母、下划线）";
      }
      return "";
    },
    validatePassword() {
      if (!this.password || !/^[A-Za-z0-9!@#$%^&*]{6,15}$/.test(this.password)) {
        return "密码不符合要求（6-15个字符，可包含字母、数字、特殊符号）";
      }
      return "";
    },
    validateContact() {
      if (!this.contact || !/^\d{11}$/.test(this.contact)) {
        return "联系电话格式错误（应为11位数字）";
      }
      return "";
    },
    validateAddress() {
      if (!this.address || this.address.length > 100) {
        return "倾向交易地址不符合要求（100字以内）";
      }
      return "";
    },
    validateUserType() {
      if (!this.userType) {
        return "请选择身份";
      }
      return "";
    },
    validateResetQuestion() {
      if (!this.resetQuestion || !/^[\w\u4e00-\u9fa5]{1,15}$/.test(this.resetQuestion)) {
        return "验证问题不符合要求（15字以内，仅文字和字母）";
      }
      return "";
    },
    validateResetAnswer() {
      if (!this.resetAnswer || !/^[\w\u4e00-\u9fa5]{1,10}$/.test(this.resetAnswer)) {
        return "回答不符合要求（10字以内，仅文字和字母）";
      }
      return "";
    },
    validateForm() {
      this.globalError =
        this.validateUsername() ||
        this.validatePassword() ||
        this.validateContact() ||
        this.validateAddress() ||
        this.validateUserType() ||
        this.validateResetQuestion() ||
        this.validateResetAnswer();
      return !this.globalError; // 如果没有错误信息，则验证通过
    },
    async submit() {
      if (!this.validateForm()) {
        return; // 验证未通过，不提交
      }
      const userTypeValue = this.userType === "买家" ? "buyer" : "seller";

      try {
        await axios.post("/api/user/register", {
          username: this.username,
          password: this.password,
          contactNumber: this.contact,
          preferredLocation: this.address,
          resetQuestion: this.resetQuestion,
          resetAnswer: this.resetAnswer,
          userType: userTypeValue,
        });
        alert("注册成功！");
        this.$router.push("/login");
      } catch (error) {
        console.error("请求失败", error);
        alert("注册失败，请稍后重试");
      }
    },
    goBack() {
      this.$router.push("/login");
    },
  },
};
</script>



  
<style scoped>
.register-page {
  background-image: url('@/assets/login/login1.jpg');
  background-size: cover;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}
.register-box {
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

label {
width: 100px; /* 固定宽度以对齐 */
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
.input-group {
display: flex;
align-items: center;
justify-content: flex-start;
margin: 20px 0;
}
.radio-group {
display: inline-flex;
gap: 10px; /* 按钮与文字的间距 */
}

.radio-group label {
display: inline-flex;
align-items: center;
gap: 5px; /* 按钮和文字的间距 */
}



.buttons {
  position: relative; /* 添加这行 */
  display: flex;
  justify-content: flex-end;
  margin: 20px 0 0;
}

.error-message {
  position: absolute;
  left: 0; /* 根据需求调整 */
  top: 50%;
  transform: translateY(-50%);
  color: red;
  font-size: 12px;
  white-space: nowrap;
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
  background-color: #0D518A;
}
</style>
  