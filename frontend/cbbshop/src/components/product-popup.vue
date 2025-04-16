<template>
    <div class="popup-overlay" v-if="visible">
      <div class="popup">
        <h3>{{ title }}</h3>
        <p>商品名称：{{ product.name }}</p>
        <p>价格：￥{{ product.price }}</p>
        <p>库存：{{ product.stock }}</p>
        
        <!-- 商品数量选择 -->
        <div class="quantity-control">
          <button @click="decreaseQuantity" :disabled="quantity <= 1">-</button>
          <span>{{ quantity }}</span>
          <button @click="increaseQuantity" :disabled="quantity >= product.stock">+</button>
        </div>
        
        <!-- 实时总价 -->
        <p>总价格：￥{{ totalPrice }}</p>
        
        <!-- 提交按钮 -->
         <div class="button-area">
            <button @click="handleSubmit">{{ buttonText }}</button>
            <button @click="closePopup">取消</button>
         </div>
        
      </div>
    </div>
  </template>
  
  <script>
  import request from '@/utils/request';
  export default {
    name: "ProductPopup",
    props: {
      visible: {
        type: Boolean,
        required: true
      },
      product: {
        type: Object,
        required: true
      },
      actionType: {
        type: String,
        required: true // "buy" 或 "addToCart"
      },
      onClose: {
        type: Function,
        required: true
      }
    },
    data() {
      return {
        quantity: 1
      };
    },
    computed: {
      totalPrice() {
        return (this.product.price * this.quantity).toFixed(2);
      },
      title() {
        return this.actionType === "buy" ? "确认购买" : "加入购物车";
      },
      buttonText() {
        return this.actionType === "buy" ? "提交订单" : "加入购物车";
      }
    },
    methods: {
      closePopup() {
        this.onClose();
      },
      increaseQuantity() {
        if (this.quantity < this.product.stock) {
          this.quantity++;
        }
      },
      decreaseQuantity() {
        if (this.quantity > 1) {
          this.quantity--;
        }
      },
      async handleSubmit() {
        try {
          // 1. 用户登录与身份检测
          const profileResponse = await request.get('/api/user/profile');
          const user = profileResponse.data;

          if (profileResponse.status === 403) {
            alert("请先登录");
            this.$router.push('/login');
            return;
          }

          // 如果用户身份为卖家，禁止操作
          if (user.userType !== 'buyer') {
            alert("当前身份为卖家，无法操作");
            return;
          }

          // 2. 根据操作类型选择 API
          const apiUrl =
            this.actionType === "buy"
              ? "/api/orders/create"
              : "/api/cart/add";

          let payload = {};

          // 3. 根据 actionType 设置请求的数据结构
          if (this.actionType === "buy") {
            // 购买时，计算总价并构建订单
            const totalPrice = (this.product.price * this.quantity).toFixed(2); // 总价
            payload = {
              order: {
                buyerId: user.id,
                totalPrice: totalPrice,
                status: "customer_ordered",
              },
              orderItems: [
                {
                  productId: this.$route.params.id,
                  quantity: this.quantity,
                  unitPrice: this.product.price,
                },
              ],
            };
          } else if (this.actionType === "cart") {
            // 加入购物车时，构建请求体
            payload = {
              buyerId: user.id,
              productId: this.$route.params.id,
              quantity: this.quantity,
            };
          }
          console.log(payload);  // 打印 payload 确认数据

          // 4. 提交请求
          const response = await request.post(apiUrl, payload);

          if (response.data.success) {
            alert(this.actionType === "buy" ? "购买成功！" : "已加入购物车！");
          } else {
            alert("操作失败，请稍后重试。");
          }
        } catch (error) {
          console.error("操作失败:", error);
          alert("操作失败，请先登录。");
        } finally {
          this.closePopup();
        }
      }
    }
  };
  </script>
  
  <style scoped>
  
  .popup-overlay {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .popup {
    background: white;
    width: 400px;
    padding: 20px;
    border-radius: 10px;
    text-align: center;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  }
  .quantity-control {
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 10px 0;
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
  