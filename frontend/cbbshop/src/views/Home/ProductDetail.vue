<template>
  <div class="product-detail">
    <div class="product-header">
      <h2>商品详情</h2>
    </div>
    <div v-if="product" class="content-container">
      <!-- 左侧：商品大图和缩略图展示 -->
      <div class="left-section">
        <!-- 大图展示 -->
        <div class="product-main-image-container">
          <img :src="currentImage" alt="Selected Product Image" class="product-main-image" />
        </div>

        <!-- 缩略图展示 -->
        <div class="thumbnails">
          <img
            v-for="(image, index) in product.images"
            :key="index"
            :src="image"
            :alt="'Thumbnail ' + (index + 1)"
            @click="changeImage(index)"
            class="thumbnail"
            :class="{ active: currentIndex === index }"
          />
        </div>
      </div>

      <!-- 右侧：商品信息展示 -->
      <div class="right-section">        
        <h2>{{ product.name }}</h2>
        <p>价格: ￥{{ product.price }}</p>
        <p>{{ product.status === 'sold' ? '已售罄' : `库存: ${product.stock}` }}</p>
        <p>分类: {{ product.categoryName }}</p> <!-- 显示完整分类名称 -->
        
        <!-- 按钮 -->
        <div class="button-container">
          <button
            :disabled="product.status === 'SOLD_OUT'"
            @click="openPopup('buy')"
          >购买</button>
          <button @click="openPopup('cart')">加入购物车</button>
          <button @click="toggleFavorite">
            <img :src="isFavorite ? require('@/assets/star1.png') : require('@/assets/star2.png')" alt="收藏图标" class="star-icon"/>
            {{ isFavorite ? "取消收藏" : "收藏" }}
          </button>
        </div>
      </div>
    </div>

    <!-- 商品描述部分 -->
    <div class="description-section">
      <div v-html="descriptionContent"></div> <!-- 使用v-html指令显示HTML内容 -->
    </div>

    <!-- 商品描述下方 footer 组件 -->
    <home-footer />

    <!-- 弹窗组件 -->
    <product-popup
      v-if="showPopup"
      :visible="showPopup" 
      :type="popupType" 
      :actionType="popupType" 
      :onClose="closePopup" 
      :product="product"
      @close="closePopup"
    ></product-popup>
  </div>
</template>

<script>
import request from '@/utils/request';
import HomeFooter from '@/components/home-footer.vue';
import ProductPopup from '@/components/product-popup.vue';

export default {
  name: "ProductDetail",
  components: {
    HomeFooter, ProductPopup
  },
  data() {
    return {
      product: null,
      currentIndex: 0,
      descriptionContent: '',
      isFavorite: false,
      popupType: '',  // 当前弹窗类型 ('buy' 或 'cart')
      showPopup: false,  // 控制弹窗显示或隐藏
    };
  },
  computed: {
    currentImage() {
      return this.product ? this.product.images[this.currentIndex] : '';
    }
  },
  mounted() {
    console.log('Product ID:', this.$route.params.id);
    this.fetchProductDetails();
    console.log('Component mounted, calling fetchProductDetails');
    this.fetchFavoriteStatus();
  },
  methods: {
    async fetchProductDetails() {
      const productId = this.$route.params.id;
      try {
        const productResponse = await request.get(`/api/products/with-media/${productId}`);
        console.log('Response:', productResponse);
        this.product = {
          ...productResponse.data.data.product.product,
          images: productResponse.data.data.product.imagePaths.map(path => `http://localhost:8081${path}`),
          categoryName: productResponse.data.data.product.product.categoryName,
        };
        this.descriptionContent = productResponse.data.data.productDescription.content;
        this.isFavorite = productResponse.data.isFavorite;
      } catch (error) {
        console.error("获取商品详情失败:", error);
      }
    },

    changeImage(index) {
      this.currentIndex = index;
    },

    async fetchFavoriteStatus() {
      try {
        const response = await this.request.get(`/api/favorites/${this.$route.params.id}`);
        this.isFavorite = response.data.isFavorite;
      } catch (error) {
        console.error("获取收藏状态失败:", error);
      }
    },

    async toggleFavorite() {
      try {
        const profileResponse = await request.get('/api/user/profile');
        const user = profileResponse.data;
        
        if (!user) {
          alert("请先登录");
          this.$router.push('/login');
          return;
        }

        if (user.userType !== 'buyer') {
          alert("当前身份为卖家，无法操作收藏");
          return;
        }

        if (!this.isFavorite) {
          const response = await request.post('/api/favorites/add', {
            buyerId: user.id,
            productId: this.$route.params.id,
          });

          if (response.data.success) {
            this.isFavorite = true;
            alert("已收藏");
          } else {
            alert("操作失败，请稍后重试");
          }
        } else {
          const checkFavoriteResponse = await request.get(`/api/cart/exists/${this.$route.params.id}`);
          const favoriteId = checkFavoriteResponse.data.favoriteId;
          
          if (favoriteId) {
            const removeFavoriteResponse = await request.delete(`/api/favorites/remove/${favoriteId}`);
            
            if (removeFavoriteResponse.data.success) {
              this.isFavorite = false;
              alert("已取消收藏");
            } else {
              alert("取消收藏失败，请稍后重试");
            }
          }
        }
      } catch (error) {
        alert("收藏操作失败,请先登录", error);
      }
    },

    // 打开弹窗，设置弹窗类型
    openPopup(type) {
      this.popupType = type;  // 设置弹窗类型（buy 或 cart）
      this.showPopup = true;  // 显示弹窗
    },

    // 关闭弹窗
    closePopup() {
      this.showPopup = false;  // 隐藏弹窗
    }
  }
};
</script>


<style scoped>
.product-popup {
  display: flex;
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: white;
  border: 1px solid #ccc;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  z-index: 1000;
}

.product-detail {
  display: flex;
  flex-direction: column;
  padding: 1px;
}
.product-header {
  background-color: #001;
  padding: 15px;
  text-align: center;
  border-bottom: 1px solid #001;
}

.product-header h2 {
  color: #fff;
  font-weight: bold;
  margin: 0;
}
.content-container {
  display: flex;
  gap: 20px;
  margin: 70px;
}

.left-section {
  display: flex;
  flex-direction: column;
  width: 50%; /* 左侧占比50% */
}

.product-main-image-container {
  width: 100%;
  height: 350px; /* 固定大图高度 */
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  background-color: #f5f5f5;
  border: 1px solid #ccc;
}

.product-main-image {
  width: 100%;
  height: auto;
  max-height: 100%;
  object-fit: contain; /* 确保图片按比例缩放 */
}

.thumbnails {
  display: flex;
  margin-top: 10px;
}

.thumbnail {
  width: 50px;
  height: auto;
  margin-right: 10px;
  cursor: pointer;
  border: 1px solid #ccc;
  border-radius: 4px;
  transition: border-color 0.3s;
}

.thumbnail.active {
  border-color: #F8B736; /* 选中的缩略图边框颜色 */
}

.right-section {
  display: flex;
  flex-direction: column;
  align-items: flex-start; /* 左对齐商品信息 */
  width: 50%; /* 右侧占比50% */
  margin-left: 100px; /* 从该距离位置开始，左对齐 */
}

.product-info {
  display: flex;
  flex-direction: column;
  margin-bottom: 20px;
}

.button-container {
  display: flex; /* 设置为 Flex 容器 */
  justify-content: space-between; /* 按钮之间水平分布 */
  align-items: center; /* 垂直方向对齐 */
  margin-top: 20px;
}
button {
  display: flex; /* 将按钮设置为 Flex 容器 */
  align-items: center; /* 子项垂直居中 */
  justify-content: center; /* 子项水平居中 */
  padding: 10px 20px;
  margin-right: 30px;
  margin-top: 30px;
  width: 120px;
  height: 40px;
  background-color: #0D518A;
  color: white;
  font-weight: bold;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
  align-self: flex-start; /* 按钮左对齐 */
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

button:hover {
  background-color: #0D518A;
}
.star-icon {
  cursor: pointer;
  width: 20px;
  height: 20px;
  margin-right: 4px;
}

/* 商品描述部分 */
.description-section {
  margin-top: 20px;
  padding: 20px;
  background-color: #f9f9f9;
  border: 1px solid #ccc;
  border-radius: 5px;
}

/* 商品描述下方 footer */
.home-footer {
  margin-top: 40px;
}
</style>
