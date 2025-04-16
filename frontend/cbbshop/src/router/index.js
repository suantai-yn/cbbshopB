import Vue from 'vue'
import VueRouter from 'vue-router'


import UserDashboard from '@/views/User/UserDashboard.vue';
import SellerDashboard from '@/views/seller/SellerDashboard.vue';

import Home from '../views/Home/Home.vue';
import LoginPage from '../views/Home/LoginPage.vue';
import ProductDetail from '../views/Home/ProductDetail.vue';
import RegisterPage from '../views/Home/RegisterPage.vue';



Vue.use(VueRouter)

const routes = [
  
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/login',
    name: 'LoginPage',
    component: LoginPage
  },
  {
    path: '/product/:id',  // 动态路由，商品详情页
    name: 'ProductDetail',
    component: ProductDetail
  },
  {
    path: '/registerpage', 
    name: 'RegisterPage', 
    component: RegisterPage
  },
  {
    path: '/user-dashboard',
    name:'UserDashboard',
    component: UserDashboard,
    children: [

      { path: 'order', component:() =>import('@/components/UserOrder.vue')},
      { path: 'history', component:() =>import('@/views/User/OrderHistory.vue')},
      { path: 'edit-profile', component: () =>import('@/views/User/UserProfile.vue')},
      { path: 'collection', component: () =>import('@/views/User/UserCollection.vue')},
      { path: 'cart', component:() =>import('@/views/User/UserCart.vue')},
    ],
  },
  {
    path: '/seller-dashboard',
    name:'SellerDashboard',
    component: SellerDashboard,
    children: [
      { path: 'products', component: () => import('@/views/seller/ProductsPage.vue') },
      { path: 'add-product', component: () => import('@/views/seller/AddProduct.vue') },
      { path: 'in-buyers', component: () => import('@/views/seller/InterestedBuyer.vue') },
      { path: 'ordermonitor', component: () => import('@/views/seller/OrderDashboard.vue') },
      { path: 'change-password', component: () => import('@/views/seller/ChangePassword.vue') },
      { path: 'customer-info', component: () => import('@/views/seller/CustomerInfo.vue') },
    ],
  }
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
