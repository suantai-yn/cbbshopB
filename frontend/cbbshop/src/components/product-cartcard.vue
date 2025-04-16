<template>
  <div class="product-cart" v-if="product.status === 'available'">
    <!-- 多选框 -->
    <div @click.stop class="multiple-selection">
      <input type="checkbox" class="check-box" v-model="isSelected" @change="updateSelectedItems" />
    </div>

    <!-- 图片 -->
    <div v-if="product.images && product.images.length > 0" class="product-image-container">
      <img :src="product.images[0]" alt="Product Image" />
    </div>
    <div v-else class="product-image-container no-image">
      <div class="placeholder"></div>
    </div>

    <!-- 商品信息 -->
    <div class="product-info">
      <h3 class="product-name">{{ product.name }}</h3>
      <p class="product-price">单价￥{{ product.price }}</p>
      <p class="product-status" :class="{ soldOut: product.status === 'SOLD_OUT' }">
        {{ product.status === 'SOLD_OUT' ? 'Sold Out' : `库存: ${product.stock}` }}
      </p>
    </div>

    <!-- 改变商品数量 -->
    <div @click.stop class="quantity-control">
      <button @click="decreaseQuantity" :disabled="quantity <= 1">-</button>
      <span>{{ quantity }}</span>
      <button @click="increaseQuantity" :disabled="quantity >= product.stock">+</button>
    </div>

    <!-- 按钮 -->
    <div class="button-area">
      <button @click.stop="deleteProduct">删除</button>
    </div>
  </div>
</template>

<script>
import request from '@/utils/request';
export default {
  
  name: "ProductCart",
  props: {
    product: Object,
  },
  data() {
    return {
      quantity: this.product.quantity || 1,
      isSelected: false, // 表示当前商品是否被选中
    };
  },
  methods: {
    async increaseQuantity() {
      if (this.quantity < this.product.stock) {
        this.quantity++;
        await this.updateQuantityInDatabase();
        this.updateSelectedItems(); // 更新结算条内容
      }
    },
    async decreaseQuantity() {
      if (this.quantity > 1) {
        this.quantity--;
        await this.updateQuantityInDatabase();
        this.updateSelectedItems(); // 更新结算条内容
      }
    },
    async updateQuantityInDatabase() {
      try {
        // 获取用户信息
        const profileResponse = await request.get('/api/user/profile');
        const user = profileResponse.data;

        // 发送请求更新购物车商品数量
        const response = await request.post('/api/cart/add', {
          buyerId: user.id,
          productId: this.product.id,
          quantity: this.quantity,
        });

        if (response.data.success) {
          console.log("商品数量更新成功");
        } else {
          console.error("商品数量更新失败");
        }
      } catch (error) {
        console.error("更新商品数量失败：", error);
      }
    },
    updateSelectedItems() {
      // 向父组件传递更新的选中商品信息
      if (this.isSelected) {
        this.$emit("update:selected-items", {
          id: this.product.id,
          name: this.product.name,
          price: this.product.price,
          quantity: this.quantity,
          isSelected: this.isSelected
        });
      } else {
        this.$emit("update:selected-items", {
          id: this.product.id,
          name: this.product.name,
          price: this.product.price,
          quantity: this.quantity,
          isSelected: this.isSelected
        });
      }
    },
    async deleteProduct() {
      try {
        // 获取当前商品的 cartId
        const response = await request.get(`/api/cart/exists/${this.product.id}`);
        const cartId = response.data.cartId;
        console.log(cartId);
        // 发送请求删除该商品
        const deleteResponse = await request.delete(`/api/cart/remove/${cartId}`);

        if (deleteResponse.data.success) {
          alert("商品已成功从购物车删除");
          // 刷新购物车商品列表
          this.$emit("refresh-cart");
        } else {
          alert("删除失败，请稍后重试");
        }
      } catch (error) {
        console.error("删除商品失败：", error);
        alert("删除失败，请稍后重试");
      }
    }
  },
  watch: {
    // 当商品被选中时，向父组件更新选中的商品信息
    isSelected(newValue) {
      this.updateSelectedItems(); // 选中或取消选中时更新结算条
    }
  }
};
</script>
  
  <style scoped>
  .product-cart {
    display: flex;
    align-items: center;
    width: 1200px;
    border: 1px solid #ccc;
    margin: 10px;
    padding: 10px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    transition: box-shadow 0.3s ease;
  }
   
  .check-box {
    width: 18px;
    height: 18px;
  }
  /* 多选框部分 */
  .multiple-selection {
    flex: 0 0 50px; /* 固定宽度为50px */
    height: 100px;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  /* 图片部分 */
  .product-image-container {
    flex: 0 0 120px; /* 固定宽度为120px */
    height: 120px;
    margin: 0 20px; /* 两边间距 */
    position: relative; /* 绝对定位支持 */
  }

  .product-image-container img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }

  .product-image-container .placeholder {
    width: 100%;
    height: 100%;
    background-color: #eeeeee;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 16px;
    color: #aaa;
  }

  /* 商品信息部分 */
  .product-info {
    flex: 1; /* 自动填充剩余空间 */
    display: flex;
    flex-direction: column; /* 垂直排列 */
    justify-content: center;
    gap: 5px; /* 子项间距 */
    text-align: left;
  }

  .product-name {
    font-size: 16px;
    font-weight: bold;
    margin: 0;
  }

  .product-price {
    font-size: 14px;
    color: #333;
    margin: 0;
  }

  .product-status {
    font-size: 14px;
    font-weight: bold;
    color: #0D5184;
  }

  .product-status.soldOut {
    color: red;
  }

  /* 数量控制部分 */
  .quantity-control {
    flex: 0 0 150px; /* 固定宽度 */
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 10px;

  }

  .quantity-control button {
    width: 30px;
    height: 30px;
    font-size: 16px;
    background-color: #00102F;
    color: #ffffff;
    cursor: pointer;
  }

  .quantity-control span {
    width: 30px;
    text-align: center;
    font-size: 16px;
    font-weight: bold;
  }

  .quantity-control button:disabled {
    cursor: not-allowed;
    opacity: 0.6;
  }

  /* 按钮部分 */
  .button-area {
    flex: 0 0 100px; /* 固定宽度 */
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .button-area button {
    padding: 5px 10px;
    margin: 20px;
    background-color: #00102F;
    width: 100px;
    height: 40px;
    font-weight: bold;
    color: #fff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }

  .button-area button:hover {
    background-color: #003580;
  }
  </style>