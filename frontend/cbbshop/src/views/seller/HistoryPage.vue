<template>
  <div class="order-history">
    <h3>历史订单</h3>
    <table>
      <thead>
        <tr>
          <th>订单编号</th>
          <th>商品名称</th>
          <th>买家名称</th>
          <th>收货地址</th>
          <th>总价</th>
          <th>订单状态</th>
          <th>订单日期</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="order in orders" :key="order.orderId">
          <td>{{ order.orderId }}</td>
          <td>{{ order.productName }}</td>
          <td>{{ order.buyerUsername }}</td>
          <td>{{ order.buyerAddress }}</td>
          <td>{{ order.totalPrice }}</td>
          <td>{{ getOrderStatus(order.status) }}</td>
          <td>{{ order.orderDate }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from "@/utils/request"; // 引入axios实例

export default {
  data() {
    return {
      orders: [],  // 存储订单数据
    };
  },
  created() {
    // 组件创建时发送请求
    this.getAllOrders();
  },
  methods: {
    // 获取所有订单
    getAllOrders() {
      axios.get('/api/orders/all')  // 请求后端获取所有订单
        .then(response => {
          // 假设返回的响应数据在data字段中，按需求处理数据
          if (response.data.success) {
            this.orders = response.data.data;  // 假设数据在 response.data.data 中
          } else {
            console.error("获取订单数据失败:", response.data.message);
          }
        })
        .catch(error => {
          console.error("获取订单数据失败:", error);
          alert("加载历史订单失败，请稍后重试！");
        });
    },

    // 格式化订单日期
    formatDate(dateString) {
      const date = new Date(dateString);
      const options = { year: 'numeric', month: 'long', day: 'numeric' };
      return date.toLocaleDateString('zh-CN', options);  // 格式化为中文日期
    },

    // 将订单状态转换为中文
    getOrderStatus(status) {
      const statusMap = {
        "ongoing": "进行中",  // 正在进行中的订单
        "completed": "已完成",  // 已完成的订单
        "cancelled": "已取消",  // 已取消的订单
      };
      return statusMap[status] || "未知状态";  // 如果没有对应的状态，返回 "未知状态"
    }
  }
};
</script>




<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
}
th, td {
  padding: 10px;
  text-align: center;
  border: 1px solid #ddd;
}
</style>
