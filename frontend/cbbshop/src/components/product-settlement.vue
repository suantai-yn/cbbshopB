<template>
  <div class="product-settlement">
    <!-- 第一部分：选中的商品信息 -->
    <div class="selected-items">
      <span v-if="selectedItems.length === 0">请选择商品</span> <!-- 占位符文本 -->
      <span v-for="(item, index) in selectedItems" :key="index">
        {{ item.name }} x {{ item.quantity }}
        <span v-if="index < selectedItems.length - 1">, </span>
      </span>
    </div>

    <!-- 第二部分：结算总价 -->
    <div class="total-price">
      <span>总价: ￥{{ totalPrice }}</span>
    </div>

    <!-- 第三部分：按钮操作区 -->
    <div class="action-buttons">
      <button @click="settle">结算</button>
      <button @click="goToFavorites">转到收藏</button>
      <button @click="deleteItems">删除</button>
    </div>
  </div>
</template>

<script>
import request from '@/utils/request';
export default {
  props: {
    selectedItems: Array,
  },
  computed: {
    totalPrice() {
      return this.selectedItems
        .reduce((total, item) => total + item.price * item.quantity, 0)
        .toFixed(2); // 保留两位小数
    },
  },
  methods: {
    async settle() {
      if (this.selectedItems.length === 0) {
        alert("请选择至少一个商品");
        return;
      }
      const profileResponse = await request.get('/api/user/profile');
      const user = profileResponse.data;
      const order = {
        buyerId: user.id,
        totalPrice: this.totalPrice,
        status: "customer_ordered",
      };

      const orderItems = this.selectedItems.map(item => ({
        productId: item.id,
        quantity: item.quantity,
        unitPrice: item.price,
      }));

      try {
        const response = await request.post('/api/orders/create', {
          order,
          orderItems,
        });

        if (response.data.success) {
          alert("订单创建成功");
          // 订单提交成功后删除已购买的商品
          await this.deleteItems();
          // 可以根据需要在此处跳转到订单页面
        } else {
          alert("订单创建失败：" + response.data.message);
        }
      } catch (error) {
        console.error("创建订单失败：", error);
        alert("创建订单失败");
      }
    },

    async goToFavorites() {
      if (this.selectedItems.length === 0) {
        alert("请选择至少一个商品");
        return;
      }
      
      const profileResponse = await request.get('/api/user/profile');
      const user = profileResponse.data;
      
      let allSucceeded = true; // 记录是否所有商品都成功转为收藏

      for (let item of this.selectedItems) {
        try {
          const response = await request.get(`/api/cart/exists/${item.id}`);
          if (!response.data.isFavorite) {
            const addResponse = await request.post('/api/favorites/add', {
              buyerId: user.id,
              productId: item.id,
            });
            if (addResponse.data.success) {
              console.log(`${item.name} 已成功添加到收藏`);
            } else {
              console.error(`${item.name} 添加收藏失败`);
              allSucceeded = false; // 如果某个商品添加失败，将标记为失败
            }
          } else {
            console.log(`${item.name} 已经在收藏中，跳过`);
          }
        } catch (error) {
          console.error(`转到收藏失败：`, error);
          allSucceeded = false; // 如果发生错误，将标记为失败
        }
      }

      // 根据操作结果弹出提示
      if (allSucceeded) {
        alert("所有选中商品已成功转为收藏");
        this.$emit('refresh-cart');
      } else {
        alert("转到收藏失败，请重试");
        this.$emit('refresh-cart');
      }
    },

    async deleteItems() {
      if (this.selectedItems.length === 0) {
        alert("请选择至少一个商品");
        return;
      }

      for (let item of this.selectedItems) {
        try {
          const response = await request.get(`/api/cart/exists/${item.id}`);
          const cartId = response.data.cartId;

          const deleteResponse = await request.delete(`/api/cart/remove/${cartId}`);
          if (deleteResponse.data.success) {
            console.log(`${item.name} 已从购物车删除`);
          } else {
            console.error(`${item.name} 删除失败`);
          }
        } catch (error) {
          console.error(`删除商品失败：`, error);
          alert("删除失败");
        }
      }

      // 刷新购物车数据
      this.$emit('refresh-cart');
    },
  },
};
</script>

  
  <style scoped>
  .product-settlement {
    position: fixed;
    bottom: 0; /* 固定在页面底部 */
    left: 0;
    width: 100%;
    height: 115px;
    border:2px solid #0D518A;
    background-color: #DBEAF7;
    padding: 10px 20px;
    box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
    align-items: center;
    z-index: 1000;
  }
  
  .selected-items {
    text-align: center;
    margin-bottom: 10px;
    min-height: 30px; /* 设置最小高度，防止空白区域 */
    display: flex;
    align-items: center; /* 使占位文本垂直居中 */
    justify-content: center;
  }

  
  .total-price {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 10px;
  }
  
  .action-buttons {
    display: flex;
    justify-content: space-between;
    width: 100%;
    max-width: 400px;
  }
  
  .action-buttons button {
    padding: 10px 20px;
    background-color: #0D518A;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s;
  }
  
  .action-buttons button:hover {
    background-color: #0D518A;
  }
  </style>
  