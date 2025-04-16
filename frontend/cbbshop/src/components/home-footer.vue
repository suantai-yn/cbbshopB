<template>
  <div class="page-wrapper">
    <!-- 页面内容 -->
    <footer>
      <a href="/">回到首页</a> | <a href="/contact">联系我们</a>
    </footer>
  </div>
</template>

<script>
export default {
  name: "home-footer"
};
</script>

<style scoped>
.page-wrapper {
   min-height: 100vh;
   display: flex;
   flex-direction: column;
   width: 100%;
 }

 footer {
  margin-top: auto;
  padding: 15px;
  background-color: #001;
  text-align: center;
  border-top: 1px solid #001;
  width: 100%;
}

a {
  color: #fff;
  font-weight: bold;
  text-decoration: none;
  margin: 0 10px;
}

a:hover {
  text-decoration: underline;
}
</style>
