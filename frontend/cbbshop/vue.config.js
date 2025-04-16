const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,
  devServer: {
    port: 8086, // 修改前端端口号
    proxy: {
      '/api': {
        target: 'http://localhost:8081', // 后端 API 的地址
        changeOrigin: true, // 确保代理时修改请求源
        pathRewrite: { '^/api': '' } // 将 /api 去除，这样请求 /api/products 会被代理到 /products
      }
    }
  }
});