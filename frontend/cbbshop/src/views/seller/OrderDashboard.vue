<template>
  <div class="order-history-page">
    <!-- 左侧导航栏 -->
    <el-menu
      default-active="1"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      background-color="#DBEAF7"
      text-color="#fff"
      active-text-color="#ffd04b"
      style="width: 200px; height: 250; float: left"
    >
      <el-menu-item
        v-for="(item, index) in menuItems"
        :key="index"
        :index="item.status"
        @click="filterOrdersByStatus(item.status)"
      >
        <i class="el-icon-menu"></i>
        <span slot="title">{{ item.label }}</span>
      </el-menu-item>
    </el-menu>

    <!-- 右侧表格区域 -->

    <div
      style="
        text-align: center;
        float: left;
        width: calc(100% - 200px);
        padding: 20px;
      "
    >
      <el-table :data="filteredOrders" border style="width: 100%" height="400">
        <el-table-column prop="orderId" label="订单编号" width="80">
        </el-table-column>
        <el-table-column prop="buyerName" label="买家名称" width="100">
        </el-table-column>
        <el-table-column prop="deliveryAddress" label="收货地址" width="150">
        </el-table-column>
        <el-table-column prop="contactInfo" label="联系方式" width="150">
        </el-table-column>
        <el-table-column prop="totalPrice" label="总价" width="100">
        </el-table-column>
        <el-table-column prop="status" label="订单状态" width="100">
          <template slot-scope="scope">
            {{ getOrderStatus(scope.row.status) }}
          </template>
        </el-table-column>
        <el-table-column prop="orderDate" label="订单日期" width="130">
          <template slot-scope="scope">
            {{ formatDate(scope.row.orderDate) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              plain
              @click="handleViewDetails(scope.row)"
              >管理</el-button
            >
            <el-button
              size="mini"
              type="danger"
              @click="handleCancelOrder(scope.row)"
              :disabled="isAbled(scope.row.status)"
              >取消订单</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 弹出框 -->
    <el-dialog
      title="订单详情"
      :visible.sync="dialogVisible"
      width="60%"
      @close="resetDialog"
    >
      <!-- 步骤条 -->
      <el-steps :active="activeStep" finish-status="success" align-center>
        <el-step title="客户下单"></el-step>
        <el-step title="商家确认"></el-step>
        <el-step title="备货完成"></el-step>
        <el-step title="运输中"></el-step>
        <el-step title="交易完成"></el-step>
      </el-steps>

      <!-- 下一步按钮 -->
      <el-button
        style="margin: 12px 0"
        @click="nextStep"
        type="primary"
        :disabled="currentOrderStatus === 'cancelled'"
      >
        下一步
      </el-button>

      <div class="order-items-container">
        <!-- 订单详情表格 -->
        <el-table
          :data="currentOrderItems"
          stripe
          style="width: 100%; margin-top: 20px; margin-left: 28%"
        >
          <el-table-column prop="name" label="商品名称" width="150">
          </el-table-column>
          <el-table-column prop="quantity" label="数量" width="100">
          </el-table-column>
          <el-table-column prop="unitPrice" label="单价" width="100">
          </el-table-column>
        </el-table>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from "@/utils/request";

export default {
  data() {
    return {
      orders: [], // 所有订单数据
      filteredOrders: [], // 筛选后的订单数据
      currentOrderItems: [], // 当前选中订单的商品项
      activeStep: 0, // 当前步骤条的步骤索引
      dialogVisible: false, // 弹出框显示状态
      defaultStatus: "customer_ordered", // 默认筛选状态
      menuItems: [
        { label: "已下单", status: "customer_ordered" },
        { label: "备货中", status: "seller_confirmed" },
        { label: "待发货", status: "stock_prepared" },
        { label: "运输中", status: "shipped" },
        { label: "已完成", status: "completed" },
        { label: "已取消", status: "cancelled" },
      ],
      currentOrderId: null, // 添加当前订单ID
      currentOrderStatus: null, // 添加当前订单状态
    };
  },
  created() {
    this.getAllOrders();
  },
  methods: {
    isAbled(status) {
      return status === "completed" || status === "cancelled";
    },
    // 获取所有订单
    getAllOrders() {
      axios.get("/api/orders/all").then((response) => {
        if (response.data.success) {
          this.orders = response.data.data;
          this.filterOrdersByStatus(this.defaultStatus);
        } else {
          console.error("获取订单数据失败:", response.data.message);
        }
      });
    },

    // 筛选订单
    filterOrdersByStatus(status) {
      this.filteredOrders = this.orders.filter(
        (order) => order.status === status
      );
    },

    // 显示订单管理弹出框
    handleViewDetails(order) {
      this.currentOrderId = order.orderId;
      this.currentOrderStatus = order.status; //设置当前订单状态
      // 1. 调用后端 API 获取订单项详情
      axios
        .get(`/api/orders/${order.orderId}/items`)
        .then((response) => {
          if (response.status === 200) {
            this.currentOrderItems = response.data; // 赋值订单项数据
            this.activeStep = this.mapOrderStatusToStep(order.status); // 设置步骤条状态

            this.dialogVisible = true; // 显示弹出框
          } else {
            this.$message.error("未找到订单项详情");
          }
        })
        .catch((error) => {
          console.error("获取订单项详情失败:", error);
          this.$message.error("获取订单项详情失败");
        });
    },

    // 根据订单状态映射到步骤条
    mapOrderStatusToStep(status) {
      const statusMap = {
        customer_ordered: 0,
        seller_confirmed: 1,
        stock_prepared: 2,
        shipped: 3,
        completed: 4,
      };
      return statusMap[status] || 0; // 默认返回第 0 步
    },

    // 下一步
    nextStep() {
      if (this.activeStep < 3) {
        this.activeStep++;
        this.updateOrderStatus(); // 调用更新订单状态的API
      } else if (this.activeStep === 3) {
        this.$message.error("需等买家确认收货！");
      } else {
        this.$message.success("订单已完成所有步骤！");
      }
    },

    // 更新订单状态
    updateOrderStatus() {
      const statusList = [
        "customer_ordered",
        "seller_confirmed",
        "stock_prepared",
        "shipped",
        "completed",
      ];
      const newStatus = statusList[this.activeStep]; // 根据当前步骤决定状态
      const currentStatus = statusList[this.activeStep - 1]; // 上一步的状态
      // 打印请求内容，以便调试
      console.log("准备发送的请求:", {
        orderId: this.currentOrderId,
        newStatus: newStatus,
      });
      axios
        .put(`/api/orders/${this.currentOrderId}/status?newStatus=${newStatus}`) //查询参数
        .then((response) => {
          if (response.status === 200) {
            this.$message.success("订单状态已更新");
            //this.filterOrdersByStatus(currentStatus); // 重新筛选订单
          } else {
            this.$message.error("更新订单状态失败");
          }
        })
        .catch((error) => {
          console.error("更新订单状态失败:", error);
          this.$message.error("更新订单状态失败");
        });
    },

    // 重置弹出框
    resetDialog() {
      this.activeStep = 0;
      this.currentOrderItems = [];
    },

    // 格式化日期
    formatDate(dateString) {
      const date = new Date(dateString);
      return date.toLocaleDateString("zh-CN");
    },

    // 转换订单状态为中文
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

    // 取消订单
    handleCancelOrder(order) {
      // 发送取消订单请求
      axios
        .post(`/api/orders/cancel/${order.orderId}?role=seller`)
        .then((response) => {
          if (response.status === 200) {
            this.$message.success("订单已取消");
            // 更新订单列表或重新获取订单
            this.getAllOrders(); // 可以调用getAllOrders重新获取数据
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
  },
};
</script>

<style scoped>
.order-history-page {
  display: flex;
  height: 100vh;
}
/* 订单详情表格容器样式 */
.order-items-container {
  display: flex; /* 使用 Flexbox 布局 */
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中（如果需要） */
  margin-top: 20px; /* 与按钮或其他内容保持距离 */
}
</style>
