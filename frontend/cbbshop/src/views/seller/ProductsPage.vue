<template>
  <div class="product-list">
    <h3>商品列表</h3>
    <table>
      <thead>
        <tr>
          <th>商品编号</th>
          <th>商品名称</th>
          <th>价格</th>
          <th>库存</th>
          <th>状态</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(product, index) in products" :key="product.productId">
          <td>{{ product.productId }}</td>
          <td>{{ product.name }}</td>
          <td>{{ product.price }}</td>
          <td>{{ product.stock }}</td>
          <td>{{ product.status }}</td>
          <td>
            <!-- <button @click="viewBuyers(product.productId, index)">查看购买人</button> -->
            <button @click="markAsSoldOut(product.productId, index)">
              下架
            </button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- 查看购买人弹出表单 -->
    <div v-if="showBuyersModal" class="modal">
      <div class="modal-content">
        <h4>意向购买人</h4>
        <table>
          <thead>
            <tr>
              <th>购买人名称</th>
              <th>联系电话</th>
              <th>收货地址</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(buyer, idx) in selectedProductBuyers" :key="idx">
              <td>{{ buyer.buyerUsername }}</td>
              <td>{{ buyer.buyerPhoneNumber }}</td>
              <td>{{ buyer.buyerAddress }}</td>
              <td><button @click="completeOrder(buyer.orderId)">完成</button></td>
            </tr>
          </tbody>
        </table>
        <button @click="closeBuyersModal">关闭</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/request"; // 确保你有正确的 axios 请求工具

export default {
  data() {
    return {
      products: [], // 商品列表
      showBuyersModal: false, // 控制显示购买人弹窗
      selectedProductBuyers: [], // 当前选择的商品的购买人
    };
  },
  mounted() {
    this.getAllProducts(); // 页面加载时获取商品数据
  },
  methods: {
    // 请求所有商品数据
    getAllProducts() {
      axios
        .get("/api/products/with-images") // 调用后端接口获取商品数据
        .then((response) => {
          const data = response.data;
          if (data.success) {
            this.products = data.data.map(item => ({
              productId: item.product.productId,
              name: item.product.name,
              price: item.product.price,
              stock: item.product.stock,
              status: item.product.status === "sold" ? "下架" : "在售",
              images: item.imagePaths.map(path => `http://localhost:8081${path}`),
            }));
          } else {
            console.error("获取商品数据失败:", data.message);
          }
        })
        .catch((error) => {
          console.error("请求失败:", error);
        });
    },

    // 查看购买人
    viewBuyers(productId) {
      // 请求获取该商品的意
      axios
        .get(`/api/orders/intents/${productId}`)
        .then((response) => {
          if (response.data.success) {
            this.selectedProductBuyers = response.data.data; // 保存购买人数据
            this.showBuyersModal = true; // 显示购买人弹窗
          } else {
            alert("没有找到购买人信息！");
          }
        })
        .catch((error) => {
          console.error("获取购买人信息失败:", error);
          alert("没有意向购买人信息");
        });
    },
     // 完成订单
     completeOrder(orderId) {
  axios
    .post(`/api/orders/complete/${orderId}`)
    .then((response) => {
      console.log("后端返回的数据:", response.data);
      if (response.data.success) {
        alert("订单已完成！");
      } else {
        alert(`订单完成失败：${response.data.message}`);
      }
    })
    .catch((error) => {
      console.error("请求失败:", error.response?.data || error);
      alert("请求失败，请稍后重试！");
    });
},

    // 关闭购买人弹窗
    closeBuyersModal() {
      this.showBuyersModal = false;
      this.selectedProductBuyers = [];
    },

    // 下架商品
    markAsSoldOut(productId, index) {
      console.log(`请求下架商品，productId: ${productId}`);
      axios
        .post(`/api/products/soldout/${productId}`)  // 调用后端接口下架商品
        .then((response) => {
          if (response.data.success) {
            alert("商品已成功下架");
            // 下架后更新本地商品列表
            this.products.splice(index, 1);  // 从列表中移除已下架商品
          } else {
            console.error("下架失败:", response.data.message);
          }
        })
        .catch((error) => {
          console.error("请求失败:", error);
        });
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
  background-color: #DBEAF7;
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
  width: 70%;
}
</style>
