<template>
  <el-table
    :data="orders"
    style="width: 80%; align-items: center; margin-left: 10%"
  >
    <!-- 展开行 -->
    <el-table-column type="expand">
      <template #default="{ row }">
        <el-form label-position="left" inline class="demo-table-expand">
          <el-form-item label="商品名称">
            <span>{{
              row.items
                ? row.items.map((item) => item.name).join(", ")
                : "加载中..."
            }}</span>
          </el-form-item>
          <el-form-item label="商品数量">
            <span>{{
              row.items
                ? row.items.map((item) => item.quantity).join(", ")
                : "加载中..."
            }}</span>
          </el-form-item>
          <el-form-item label="单价">
            <span>{{
              row.items
                ? row.items.map((item) => item.unitPrice).join(", ")
                : "加载中..."
            }}</span>
          </el-form-item>
        </el-form>
      </template>
    </el-table-column>

    <!-- 订单编号 -->
    <el-table-column label="订单号" prop="orderId"></el-table-column>

    <!-- 总价 -->
    <el-table-column label="总价" prop="totalPrice"></el-table-column>

    <!-- 订单日期 -->
    <el-table-column label="订单日期" prop="orderDate">
      <template #default="{ row }">
        {{ formatDate(row.orderDate) }}
      </template>
    </el-table-column>

    <!-- 订单状态 -->
    <el-table-column label="订单状态">
      <template #default="{ row }">
        {{ getOrderStatus(row.status) }}
      </template>
    </el-table-column>

    <!-- 操作栏 -->
    <el-table-column label="操作">
      <template #default="{ row }">
        <el-button
          v-if="row.status === 'customer_ordered'"
          type="danger"
          size="mini"
          plain
          @click="cancelOrder(row.orderId)"
        >
          取消订单
        </el-button>
        <el-button
          v-if="row.status !== 'customer_ordered'"
          type="danger"
          size="mini"
          plain
          :disabled="true"
        >
          取消订单
        </el-button>
        <el-button
          type="success"
          size="mini"
          plain
          @click="confirmReceipt(row.orderId)"
          :disabled="row.status === 'completed'||row.status === 'cancelled'"
        >
          确认收货
        </el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import axios from "@/utils/request"; // 确保引入了axios实例

export default {
  data() {
    return {
      orders: [], // 初始化为空数组，稍后将通过API填充
      currentOrderItems: [],
    };
  },
  methods: {
    fetchOrders() {
      axios
        .get(`/api/orders/buyer/${this.buyerId}`)
        .then((response) => {
          if (response.data && response.data.data) {
            this.orders = response.data.data;

            // 使用 Promise.all 来确保所有的 fetchOrderItems 请求都完成
            const fetchItemPromises = this.orders.map((order) =>
              this.fetchOrderItems(order)
            );

            // 等待所有请求完成后，再进行其他处理
            Promise.all(fetchItemPromises)
              .then(() => {
                this.$forceUpdate(); // 强制更新视图（可选）
              })
              .catch((error) => {
                console.error("Error fetching order items:", error);
              });
          }
        })
        .catch((error) => {
          console.error("Error fetching orders:", error);
        });
    },

    fetchOrderItems(order) {
      axios
        .get(`/api/orders/${order.orderId}/items`)
        .then((response) => {
          if (response.status === 200) {
            //order.items = response.data; // 将订单项数据保存到订单的 items 字段
            this.$set(order, "items", response.data); // 使用 Vue 的 $set 确保响应式更新
          } else {
            this.$message.error("未找到订单项详情");
          }
        })
        .catch((error) => {
          console.error("获取订单项详情失败:", error);
          this.$message.error("获取订单项详情失败");
        });
    },
    fetchBuyerId() {
      axios
        .get("/api/user/profile")
        .then((response) => {
          if (response.data) {
            this.buyerId = response.data.id;
            this.fetchOrders(); // 获取到buyerId后，调用fetchOrders方法获取订单数据
          }
        })
        .catch((error) => {
          console.error("Error fetching buyerId:", error);
        });
    },
    cancelOrder(orderId) {
      axios
        .post(`/api/orders/cancel/${orderId}?role=buyer`)
        .then((response) => {
          if (response.status === 200) {
            this.$message.success("订单已取消");
            // 更新订单列表或重新获取订单
            // this.fetchOrders();  // 重新获取数据
          } else {
            this.$message.error("取消订单失败");
          }
        })
        .catch((error) => {
          console.error(
            "取消订单失败:",
            error.response ? error.response.data : error.message
          );
          this.$message.error("取消订单失败");
        });
    },
    confirmReceipt(orderId) {
      axios
        .put(`/api/orders/${orderId}/confirm-receive`, null, {
          params: { buyerId: this.buyerId },
        })
        .then((response) => {
          if (response.data === "Order not found") {
            this.$message.error("订单未找到");
          } else if (response.data === "Order not shipped yet") {
            this.$message.error("订单尚未发货");
          } else if (response.data === "success") {
            this.$message.success("确认收货成功");
            this.fetchOrders(); // 刷新订单列表
          }
        })
        .catch((error) => {
          console.error("确认收货失败:", error);
          this.$message.error("确认收货失败");
        });
    },
    getOrderStatus(status) {
      const statusMap = {
        customer_ordered: "已下单",
        seller_confirmed: "备货中",
        stock_prepared: "待发货",
        shipped: "运输中",
        completed: "已完成",
        cancelled: "已取消",
      };
      return statusMap[status] || "未知状态";
    },
    formatDate(dateString) {
      const date = new Date(dateString);
      const options = { year: "numeric", month: "long", day: "numeric" };
      return date.toLocaleDateString("zh-CN", options); // 格式化为中文日期
    },
  },
  created() {
    this.fetchBuyerId(); // 组件创建时，获取buyerId和订单数据
  },
};
</script>

<style scoped>
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>
