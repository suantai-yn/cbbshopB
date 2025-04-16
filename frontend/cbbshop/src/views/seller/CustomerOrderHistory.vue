<template>
    <div class="customer-order-history">
      <h3>历史订单</h3>
      <table>
        <thead>
          <tr>
            <th>订单编号</th>
            <th>商品名称</th>
            <th>数量</th>
            <th>总价</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="order in customerOrders" :key="order.id">
            <td>{{ order.id }}</td>
            <td>{{ order.productName }}</td>
            <td>{{ order.quantity }}</td>
            <td>{{ order.totalPrice }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        customerOrders: [],
      };
    },
    created() {
      const customerId = this.$route.params.customerId;
      this.fetchCustomerOrders(customerId);
    },
    methods: {
      fetchCustomerOrders(customerId) {
        // 模拟请求客户的历史订单数据
        this.customerOrders = [
          { id: 101, productName: '商品A', quantity: 2, totalPrice: 200 },
          { id: 102, productName: '商品B', quantity: 1, totalPrice: 100 },
          // 更多订单数据
        ];
        console.log("加载客户ID " + customerId + " 的历史订单");
      },
    },
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
  