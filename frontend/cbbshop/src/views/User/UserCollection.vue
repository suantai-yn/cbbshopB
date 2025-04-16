<template>
  <div class="collection">
    <h3 class="collection-title">收藏栏</h3>
    
    <!-- 未登录时显示提示信息 -->
    <div v-if="!buyerId" class="login-prompt">
      <p>请先登录</p>
      <button @click="redirectToLogin">去登录</button>
    </div>
    
    <!-- 登录后显示商品收藏 -->
    <div v-else>
      <div class="product-list">
        <div v-for="product in filteredProducts" :key="product.id" class="productcard">
          <product-card :product="product" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import ProductCard from '../../components/product-card.vue';
import request from '@/utils/request';

export default {
  components: {
    ProductCard,
  },
  data() {
    return {
      products: [],
      filteredProducts: [],
      buyerId: null,  // 存储buyerId
    };
  },
  mounted() {
    this.fetchBuyerId();
  },
  methods: {
    async fetchBuyerId() {
      try {
        // 获取buyerId
        const response = await request.get('/api/user/profile');
        this.buyerId = response.data.id;
        this.fetchFavorites();  // 获取收藏数据
      } catch (error) {
        console.error("获取用户信息失败：", error);
        this.buyerId = null;  // 如果未登录，buyerId 为 null
      }
    },
    async fetchFavorites() {
      if (this.buyerId === null) return;
      
      try {
        // 使用收藏列表API获取收藏数据
        const response = await request.get(`/api/favorites/list/${this.buyerId}`);
        this.products = response.data.data.map(item => ({
          id: item.product.productId,
          name: item.product.name,
          price: item.product.price,
          stock: item.product.stock,
          status: item.product.status,
          categoryId: item.product.categoryId,
          images: item.imagePaths.map(path => `http://localhost:8081${path}`)
        }));
        this.filteredProducts = this.products; // 设置过滤后的商品数据
      } catch (error) {
        console.error("获取收藏商品数据失败：", error);
      }
    },
    redirectToLogin() {
      // 使用 vue-router 跳转到登录页面
      this.$router.push('/login');
    },
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

.collection {
  text-align: center;
  margin-top: 20px;
}

.collection-title {
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
</style>