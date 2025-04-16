<template>
  <div class="cart">
    <h3 class="cart-title">购物车</h3>

    <!-- 未登录时显示提示信息 -->
    <div v-if="!buyerId" class="login-prompt">
      <p>请先登录</p>
      <button @click="redirectToLogin">去登录</button>
    </div>

    <!-- 登录后显示购物车商品列表 -->
    <div v-else>
      <div class="product-list">
        <div v-for="product in filteredProducts" :key="product.id" class="productcard">
          <product-cart :product="product" 
          @update:selected-items="updateSelectedItems"
          @refresh-cart="fetchCartItems" />
        </div>
      </div>

      <!-- 结算条 -->
      <product-settlement :selected-items="selectedItems" class="settlement-bar" @refresh-cart="fetchCartItems"/>
    </div>
  </div>
</template>

<script>
import ProductCart from '../../components/product-cartcard.vue';
import ProductSettlement from '../../components/product-settlement.vue';
import request from '@/utils/request';
import { useRouter } from 'vue-router';

export default {
  components: {
    ProductCart,
    ProductSettlement,
  },
  data() {
    return {
      products: [],
      filteredProducts: [],
      selectedItems: [], // 存储选中的商品
      buyerId: null, // 存储buyerId
    };
  },
  mounted() {
    this.fetchBuyerId();
  },
  methods: {
    async fetchBuyerId() {
      try {
        const response = await request.get('/api/user/profile');
        this.buyerId = response.data.id;
        this.fetchCartItems(); // 获取购物车数据
      } catch (error) {
        console.error("获取用户信息失败：", error);
        this.buyerId = null; // 如果未登录，buyerId 为null
      }
    },
    async fetchCartItems() {
      if (this.buyerId === null) return;

      try {
        const response = await request.get(`/api/cart/list/${this.buyerId}`);
        this.filteredProducts = response.data.data.map(item => ({
          id: item.product.product.productId,
          name: item.product.product.name,
          price: item.product.product.price,
          stock: item.product.product.stock,
          status: item.product.product.status,
          quantity: item.quantity,
          images: item.product.imagePaths.map(path => `http://localhost:8081${path}`)
        }));
      } catch (error) {
        console.error("获取购物车商品数据失败：", error);
      }
    },
    updateSelectedItems(item) {
    const existingItem = this.selectedItems.find(i => i.id === item.id);

    // 如果商品已存在，并且取消选择了商品，则从 selectedItems 中移除该商品
    if (existingItem) {
      if (!item.isSelected) {
        this.selectedItems = this.selectedItems.filter(i => i.id !== item.id);
      } else {
        existingItem.quantity = item.quantity;
        existingItem.isSelected = item.isSelected;
      }
    } else {
      // 商品首次被选中，加入 selectedItems
      if (item.isSelected) {
        this.selectedItems.push(item);
      }
    }
  },
    redirectToLogin() {
      const router = useRouter();
      router.push('/login');
    }
  }
};
</script>

<style scoped>
.login-prompt {
  text-align: center;
  padding: 20px;
}
.login-prompt p {
  font-size: 18px;
  margin-bottom: 10px;
}
.login-prompt button {
  background-color: #0D518A;
  color: white;
  border: none;
  border-radius: 4px;
  padding: 10px 20px;
  cursor: pointer;
}
.login-prompt button:hover {
  background-color: #0d4b7d;
}


  .cart {
    text-align: center;
    margin-top: 20px;
  }
  
  .cart-title {
    font-size: 24px;
    font-weight: bold;
    margin-bottom: 20px;
  }
  
  .product-list {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: 20px; /* 控制卡片间距 */
  }
  .settlement-bar {
  position: sticky;
  bottom: 0;
  width: 100%;
  z-index: 10;
  background-color: #f8f8f8;
}
  </style>