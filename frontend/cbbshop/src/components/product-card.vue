<template>
  <div class="product-card" v-if="product.status === 'available'" @click="goToProductDetail">
    <div v-if="product.images && product.images.length > 0" class="product-image-container">
      <img :src="product.images[0]" alt="Product Image" />
    </div>
    <div v-else class="product-image-container no-image">
      <!-- 占位符内容，可以是文字或其他元素 -->
      <div class="placeholder"></div>
    </div>
 
    <div class="product-info">
      <h3 class="product-name">{{ product.name }}</h3>
      <p class="product-price">￥{{ product.price }}</p>
      <p class="product-status" :class="{ soldOut: product.status === 'SOLD_OUT' }">
        {{ product.status === 'SOLD_OUT' ? 'Sold Out' : `库存: ${product.stock}` }}
      </p>
    </div>
  </div>
</template>

<script>
export default {
  name: "ProductCard",
  props: {
    product: Object
  },
  computed: {
    truncatedDescription() {
      return this.product.description.length > 80
        ? this.product.description.substring(0, 80) + '...'
        : this.product.description;
    }
  },
  methods: {
    goToProductDetail() {
      // 跳转到商品详情页，传递商品ID
      this.$router.push({ name: 'ProductDetail', params: { id: this.product.id } });
    }
  }
};
</script>

<style scoped>
.product-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 200px;
  border: 1px solid #ccc;
  margin: 10px;
  padding: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: box-shadow 0.3s ease;
}

.product-card:hover {
  box-shadow: 0 8px 12px rgba(0, 0, 0, 0.15);
}

.product-image-container {
  width: 100%;
  height: 150px;
  position: relative; /* 添加定位以支持内部绝对定位 */
  margin-bottom: 10px;
}
 
.product-image-container img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
 
.product-image-container .placeholder {
  width: 100%;
  height: 100%;
  background-color: #eeeeee00; /* 占位符背景颜色 */
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  color: #aaa; /* 占位符文字颜色 */
}
 
.product-info {
  text-align: center;
}
 
.product-name {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 8px;
}
 
.product-price {
  font-size: 14px;
  color: #333;
  margin-bottom: 8px;
}
 
.product-status {
  font-size: 14px;
  font-weight: bold;
  color: #0D5184;
}
 
.product-status.soldOut {
  color: red;
}
 

</style>