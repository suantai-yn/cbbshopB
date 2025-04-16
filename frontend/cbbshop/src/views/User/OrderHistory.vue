<template>
  <div>

    <table>
      <tr>   
        <th>订单号</th>
        <th>商品名称</th>
        <th>总价</th>
        <th>收货地址</th>
        <th>订单状态</th>
        <th>下单日期</th>
      </tr>
      <tr v-for="order in orders" :key="order.orderId">
        <td>{{ order.orderId }}</td>
        <td>{{ order.productName }}</td>
        <td>{{ order.totalPrice }}</td>
        <td>{{ order.buyerAddress }}</td>
        <td>{{ getOrderStatus(order.status) }}</td>
        <td>{{ formatDate(order.orderDate) }}</td>
      </tr>
    </table>
  </div>
</template>

<script>
import axios from "@/utils/request"; // 确保引入了axios实例

export default {
  data() {
    return {
      orders: [], // 初始化为空数组，稍后将通过API填充
    };
  },
  methods: {
    fetchOrders() {
      axios.get(`/api/orders/buyer/${this.buyerId}`)
        .then(response => {
          if (response.data && response.data.data) {
            this.orders = response.data.data;
          }
        })
        .catch(error => {
          console.error('Error fetching orders:', error);
        });
    },
    fetchBuyerId() {
      axios.get('/api/user/profile')
        .then(response => {
          if (response.data) {
            this.buyerId = response.data.id; // 假设返回的用户信息中包含id字段
            this.fetchOrders(); // 获取到buyerId后，调用fetchOrders方法获取订单数据
          }
        })
        .catch(error => {
          console.error('Error fetching buyerId:', error);
        });
    },
    // 将订单状态转换为中文
  getOrderStatus(status) {
      const statusMap = {
        ongoing: "进行中",
        completed: "已完成",
        cancelled: "已取消",
      };
      return statusMap[status] || "未知状态";
    },
    // 格式化日期
    formatDate(dateString) {
      const date = new Date(dateString);
      const options = { year: 'numeric', month: 'long', day: 'numeric' };
      return date.toLocaleDateString('zh-CN', options);  // 格式化为中文日期
    },
  },
  created() {
    this.fetchBuyerId(); // 组件创建时，获取buyerId和订单数据
  },
  
};
</script>

<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
}
th, td {
  padding: 8px;
  border: 1px solid #ddd;
  text-align: center;
}
</style>