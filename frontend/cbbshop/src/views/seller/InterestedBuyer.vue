<template>
    <div class="interested-buyers">
      <h3>意向购买人</h3>
      <table>
        <thead>
          <tr>
            <th>买家姓名</th>
            <th>商品名</th>
            <th>联系方式</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="buyer in buyers" :key="buyer.id">
            <td>{{ buyer.name }}</td>
            <td>{{buyer.product}}</td>
            <td>{{ buyer.contact }}</td>
            <td>
              <button @click="selectBuyer(buyer.id)">选择</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        buyers: [
          { id: 1, name: '买家A', product:'Apple',contact: '123-456-7890' },
          { id: 2, name: '买家B', product:'Jeans',contact: '098-765-4321' },
          // 更多买家数据
        ],
      };
    },
    methods: {
      selectBuyer(buyerId) {
        // 实现选择买家的逻辑，例如标记买家为优先客户
        console.log("选择了买家 ID:", buyerId);
      },
    },
  };
  </script>
  
  <style scoped>
  table {
    width: 100%;
    border-collapse: collapse;
  }
  th, td {
    padding: 10px;
    text-align: center;
    border: 1px solid #ddd;
  }
  button {
    margin: 0 5px;
  }
  </style>
  