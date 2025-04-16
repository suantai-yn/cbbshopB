<template>
  <div class="customer-info">
    <h3>客户信息</h3>
    <table>
      <thead>
        <tr>
          <th>客户姓名</th>
          <th>联系方式</th>
          <th>默认地址</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="customer in customers" :key="customer.id">
          <td>{{ customer.username }}</td>
          <td>{{ customer.contactNumber }}</td>
          <td>{{ customer.preferredLocation }}</td>
          <td>
            <button @click="viewPurchaseHistory(customer.id)">购买历史</button>
            <button @click="resetpwd(customer.id)">重置密码</button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- 客户购买历史弹出框 -->
    <div v-if="showPurchaseHistoryModal" class="modal">
      <div class="modal-content">
        <h4>{{ selectedCustomer.username }}的购买历史</h4>
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
  </el-table>
        <button @click="closePurchaseHistoryModal">关闭</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/request"; // 导入axios实例

export default {
  data() {
    return {
      customers: [], // 存储客户数据
      showPurchaseHistoryModal: false, // 控制购买历史弹出框的显示与隐藏
      selectedCustomer: {}, // 当前选中的客户
      orders: [], // 存储该客户的订单数据
    };
  },
  created() {
    this.fetchCustomers(); // 组件创建时获取客户数据
  },
  methods: {
    // 获取客户数据
    fetchCustomers() {
      axios
        .get("/api/user/buyers")
        .then((response) => {
          if (response.data && response.data.length > 0) {
            this.customers = response.data; // 将返回的客户数据绑定到customers
          } else {
            console.warn("没有客户数据可加载");
          }
        })
        .catch((error) => {
          console.error("获取客户信息失败:", error);
          alert("加载客户信息失败，请稍后重试！");
        });
    },
    // 查看客户购买历史
    viewPurchaseHistory(customerId) {
      this.selectedCustomer = this.customers.find(
        (customer) => customer.id === customerId
      ); // 找到当前选中的客户
      this.getCustomerOrders(customerId); // 获取该客户的订单
      this.showPurchaseHistoryModal = true; // 显示弹出框
    },
    // 获取客户的订单历史
    getCustomerOrders(customerId) {
      axios
        .get(`/api/orders/buyer/${customerId}`)
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
    //客户订单项获取
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
    // 重置密码
    resetpwd(customerId) {
      axios
        .post("/api/user/resetPassword", null, {
          params: { customerId },
        })
        .then((response) => {
          if (response.status === 200) {
            alert("密码已重置为 123456！");
          } else {
            alert("密码重置失败：" + response.data);
          }
        })
        .catch((error) => {
          console.error("重置密码失败:", error);
          alert("重置密码请求失败，请稍后重试！");
        });
    },
    // 格式化订单日期
    formatDate(dateString) {
      const date = new Date(dateString);
      const options = { year: "numeric", month: "long", day: "numeric" };
      return date.toLocaleDateString("zh-CN", options); // 格式化为中文日期
    },
    // 将订单状态转换为中文
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
    // 关闭购买历史弹出框
    closePurchaseHistoryModal() {
      this.showPurchaseHistoryModal = false; // 隐藏弹出框
      this.orders = []; // 清空订单数据
    },
  },
};
</script>

<style scoped>
/* 表格样式 */
table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
}
th,
td {
  border: 1px solid #ddd;
  padding: 8px;
}
th {
  background-color: #f2f2f2;
}

/* 按钮样式 */
button {
  margin: 0 5px;
  padding: 5px 10px;
}

/* 模态框样式 */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}
.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 5px;
  width: 80%;
  max-width: 600px;
}
</style>
