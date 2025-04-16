<template>
  <div class="add-product">
    <h3>发布商品</h3>
    <form @submit.prevent="submitProduct">
      <div class="form-group">
        <label>商品名称</label>
        <input v-model="product.name" type="text" required />
      </div>

      <div class="form-group">
        <label>商品售价（元）</label>
        <input v-model="product.price" type="number" required />
      </div>

      <div class="form-group">
        <label>库存</label>
        <input v-model="product.stock" type="number" required />
      </div>

      <div class="form-group">
        <label>类别</label>
        <select
          v-model="product.mainCategory"
          @change="updateSubCategories"
          required
        >
          <option
            v-for="category in mainCategories"
            :key="category.categoryId"
            :value="category.categoryId"
          >
            {{ category.name }}
          </option>
        </select>

        <select v-model="product.subCategory" required>
          <option
            v-for="subcategory in subCategories"
            :key="subcategory.categoryId"
            :value="subcategory.categoryId"
          >
            {{ subcategory.name }}
          </option>
        </select>
      </div>

      <div class="form-group">
        <label>图片</label>
        
        <input type="file" multiple @change="handleImageUpload" />
        <div style="color:lightgray;">*请一次性选择所有图片*</div>
      </div>

      <div class="form-group">
        <label>描述</label>
        <Editor ref="editor" />
      </div>

      <button type="submit">发布</button>
      <button type="button" @click="cancel">取消</button>
    </form>
  </div>
</template>

<script>
import Editor from "@/components/Editor.vue";
import axios from "@/utils/request";

export default {
  components: {
    Editor,
  },
  data() {
    return {
      product: {
        name: "",
        price: "",
        stock: "",
        status: "",
        //mainCategory: '',
        categoryId: "",
      },
      mainCategories: [], // 主分类数据
      subCategories: [], // 子分类数据
      selectedImages: [], // 存储选择的图片
    };
  },
  mounted() {
    this.getMainCategories();
  },
  methods: {
    async submitProduct() {
      // 验证库存是否小于等于 0
      if (this.product.stock <= 0) {
        this.$message.error("库存不能小于等于0！");
        return; // 阻止表单提交
      }
      const formData = new FormData();

      // 将商品数据作为 JSON 放入请求体
      const productJson = JSON.stringify({
        name: this.product.name,
        price: this.product.price,
        stock: this.product.stock,
        categoryId: this.product.subCategory,
        status: "available",
      });

      formData.append("productJson", productJson);
      // 商品描述
      const descriptionContent = this.$refs.editor.getContent(); // 获取编辑器中的内容
      console.log("des", descriptionContent);
      formData.append("descriptionContent", descriptionContent); // 添加描述

      // 添加图片
      if (this.selectedImages && this.selectedImages.length > 0) {
        this.selectedImages.forEach((file) => {
          formData.append("images", file);
        });
      } else {
        // 如果没有图片，确保传递空数组
        formData.append("images", []);
      }

      try {
        const response = await axios.post("/api/products/add", formData, {
          headers: {
            "Content-Type": "multipart/form-data", // 设置为 multipart/form-data 以确保文件上传
          },
        });

        if (response.data.success) {
          alert("商品发布成功！");
        } else {
          alert("商品发布失败：" + response.data.message);
        }
      } catch (error) {
        console.error("请求失败:", error);
        alert("商品发布失败，请稍后重试！");
      }
    },
    handleImageUpload(event) {
      // 获取选择的图片文件
      this.selectedImages = Array.from(event.target.files);

      // 打印 selectedImages 的类型和内容
      console.log("selectedImages 类型:", this.selectedImages.constructor.name); // 应该是 FileList
      console.log("selectedImages 内容:", this.selectedImages); // 打印文件对象的详细信息
    },
    getMainCategories() {
      axios
        .get(`/api/categories/all`)
        .then((response) => {
          this.mainCategories = response.data;
          console.log("Main categories:", this.mainCategories); // 打印检查格式
          // 确保 mainCategory 有默认值
          if (this.mainCategories.length > 0 && !this.product.mainCategory) {
            this.product.mainCategory = this.mainCategories[0].categoryId; // 选择第一个分类作为默认
          }
        })
        .catch((error) => {
          console.error("获取主类别失败:", error);
        });
    },
    updateSubCategories() {
      // 根据选择的主类别，获取对应的子类别
      console.log("Selected main category:", this.product.mainCategory);
      axios
        .get(`/api/categories/${this.product.mainCategory}`)
        .then((response) => {
          // 这里更新子分类数据
          this.subCategories = response.data;
        })
        .catch((error) => {
          console.error("获取子类别失败:", error);
        });
    },
    cancel() {
      // 重置表单数据
      this.product = {
        name: "",
        price: "",
        stock: "",
        mainCategory: "",
        subCategory: "",
      };
      this.selectedImages = [];
    },
  },
};
</script>

<style scoped>
.add-product {
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 700px;
  margin: 40px auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h3 {
  margin-bottom: 20px;
  color: #333;
  font-weight: 600;
  font-size: 24px;
}

.form-group {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
  width: 100%;
}

label {
  width: 80px;
  font-weight: 500;
  color: #666;
  margin-right: 10px;
}

input[type="text"],
input[type="number"],
input[type="file"],
textarea {
  width: calc(100% - 100px);
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 16px;
  color: #333;
  box-sizing: border-box;
}
select {
  width: calc(50% - 50px);
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 16px;
  color: #333;
  box-sizing: border-box;
}
select {
  width: 48%;
  margin-right: 4%;
}

input[type="text"]:focus,
input[type="number"]:focus,
textarea:focus,
select:focus {
  outline: none;
  border-color: #007bff;
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.2);
}

textarea {
  resize: vertical;
  min-height: 80px;
}

button {
  padding: 10px 15px;
  background-color: #0b329c;
  color: #fff;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
  margin-top: 10px;
  width: 35%;
}

button[type="button"] {
  background-color: #6c757d;
  margin-left: 4%; /* 分开发布和取消按钮 */
}

button:hover {
  background-color: #0056b3;
}

button[type="button"]:hover {
  background-color: #5a6268;
}
</style>
