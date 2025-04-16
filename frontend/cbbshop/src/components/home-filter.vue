<template>
  <div class="filter">
    <!-- 父分类选择框 -->
    <select v-model="selectedParentCategory" @change="fetchSubCategories">
      <option value="" disabled>选择一级分类</option>  <!-- 默认选项不可选中 -->
      <option v-for="category in parentCategories" :key="category.categoryId" :value="category.categoryId">
        {{ category.name }}
      </option>
    </select>

    <!-- 子分类选择框 -->
    <select v-model="selectedChildCategory">
      <option value="" disabled>选择二级分类</option>  <!-- 默认选项不可选中 -->
      <option v-for="category in childCategories" :key="category.categoryId" :value="category.categoryId">
        {{ category.name }}
      </option>
    </select>

    <button @click="applyFilters">筛选</button>
    <button @click="resetFilters">重置</button>
  </div>
</template>

<script>
import request from '@/utils/request';

export default {
  name: 'HomeFilter',
  data() {
    return {
      parentCategories: [],  // 存储父分类数据
      childCategories: [],   // 存储子分类数据
      selectedParentCategory: '',  // 选中的父分类
      selectedChildCategory: ''    // 选中的子分类
    };
  },
  mounted() {
    this.fetchParentCategories();  // 页面加载时获取父分类
  },
  methods: {
    // 获取所有一级分类
    async fetchParentCategories() {
      try {
        const response = await request.get('/api/categories/all');
        this.parentCategories = response.data;
      } catch (error) {
        console.error('获取父分类失败：', error);
      }
    },

    // 获取对应父分类下的所有二级分类
    async fetchSubCategories() {
      const parentId = this.selectedParentCategory;
      if (!parentId) {
        this.childCategories = [];
        this.selectedChildCategory = '';  // 清空子分类选择
        return;
      }

      try {
        const response = await request.get(`/api/categories/${parentId}`);
        this.childCategories = response.data;

        // 自动选择第一个子分类
        if (this.childCategories.length > 0) {
          this.selectedChildCategory = this.childCategories[0].categoryId;
        }
      } catch (error) {
        console.error('获取二级分类失败：', error);
      }
    },

    // 点击筛选按钮时触发
    applyFilters() {
      this.$emit('filterCategory', {
        parentId: this.selectedParentCategory,
        childId: this.selectedChildCategory
      });
    },

    // 重置筛选条件
    resetFilters() {
      this.selectedParentCategory = '';
      this.selectedChildCategory = '';
      this.childCategories = [];
      this.$emit('filterCategory', { parentId: null, childId: null });
    }
  }
};
</script>




<style scoped>
.filter {
  display: flex;
  justify-content: right;
  padding: 10px;
  background-color: #ffffff00;
  border-bottom: 1px solid #ddd;
}

select, button {
  margin: 0 10px;
  padding: 3px 20px;
}

button {
  background-color: #0D518A;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #0D5184;
}
</style>