<template>
  <div>
    <home-header @search="searchProducts" />
    <home-filter @filterCategory="filterProducts" />
    <main>
      <div v-for="product in filteredProducts" :key="product.id">
        <product-card :product="product" />
      </div>
    </main>
    <home-footer />
  </div>
</template>

<script>
import HomeHeader from '../../components/home-header.vue';
import HomeFilter from '../../components/home-filter.vue';
import ProductCard from '../../components/product-card.vue';
import HomeFooter from '../../components/home-footer.vue';
import request from '@/utils/request';

export default {
  name: "HomePage",
  components: {
    HomeHeader,
    HomeFilter,
    ProductCard,
    HomeFooter
  },
  data() {
    return {
      products: [],
      filteredProducts: []
    };
  },
  mounted() {
    this.fetchProducts();
  },
  methods: {
    async fetchProducts() {
      try {
        const response = await request.get('/api/products/with-images');
        this.products = response.data.data.map(item => ({
          id: item.product.productId,
          name: item.product.name,
          price: item.product.price,
          stock: item.product.stock,
          status: item.product.status,
          categoryId: item.product.categoryId,
          images: item.imagePaths.map(path => `http://localhost:8081${path}`)
        }));
        this.filteredProducts = this.products;
      } catch (error) {
        console.error("获取商品数据失败：", error);
      }
    },

    // 搜索商品
    searchProducts(query) {
      if (!query) {
        this.filteredProducts = this.products;
        return;
      }
      this.filteredProducts = this.products.filter(product =>
        product.name.toLowerCase().includes(query.toLowerCase())
      );
    },

    // 根据子分类筛选商品
      filterProducts({  childId }) {
        // 仅根据子分类筛选商品
        if (childId) {
          this.filteredProducts = this.products.filter(product => product.categoryId === childId);
        }
        // 如果没有选择子分类，显示所有商品
        else {
          this.filteredProducts = this.products;
        }
      }
      }
};
</script>

  
<style scoped>
  main {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    padding: 20px;
  }
</style>
  
  