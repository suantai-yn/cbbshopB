// src/utils/request.js
import axios from "axios";

// 创建 axios 实例
const instance = axios.create({
  baseURL: "http://localhost:8081", // 替换为你的后端 API 地址
  timeout: 5000,
  headers: {
    "Content-Type": "application/json",
    "Accept": "application/json"
  },
  withCredentials: true // 允许携带 Cookie（如有需要）
});

// // 添加请求拦截器
// instance.interceptors.request.use(
//   (config) => {
//     const token = localStorage.getItem("authToken"); // 从 localStorage 获取 token
//     if (token) {
//       // 设置 Bearer 格式的 Authorization 头部
//       config.headers["Authorization"] = `Bearer ${token}`;
//     }
//     return config;
//   },
//   (error) => {
//     return Promise.reject(error);
//   }
// );

// // 添加响应拦截器
// instance.interceptors.response.use(
//   (response) => response,
//   (error) => {
//     if (error.response) {
//       if (error.response.status === 401) {
//         // Token 无效或过期处理逻辑
//         localStorage.removeItem("authToken"); // 清除 token
//         alert("登录状态已过期，请重新登录。");

//         // 使用 Vue Router 跳转到登录页
//         router.push("/login").catch(err => console.warn(err));
//       } else if (error.response.status === 403) {
//         alert("无权限访问此资源。");
//       }
//     }
//     return Promise.reject(error);
//   }
// );

export default instance;
