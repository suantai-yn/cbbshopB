<template>  
    <div class="contact-container">  
      <div class="contact-info">  
        <p>Number: {{ phoneNumber }}</p>  
        <p>E-mail: {{ email }}</p>  
      </div>  
    </div>  
  </template>  
    
  <script>  
  export default {  
    name: 'Contact',  
    data() {  
      return {  
        phoneNumber: '19357333333',  
        email: '123456@gmail.com'  
      };  
    }  
  };  
  </script>  
    
  <style scoped>  
  .contact-container {  
    padding: 20px;  
    border: 1px solid #ddd;  
    border-radius: 5px;  
    max-width: 400px;  
    margin: 0 auto;  
  }  
    
  .contact-info {  
    text-align: center;  
  }  
    
  .contact-info p {  
    margin: 10px 0;  
    font-size: 16px;  
    color: #333;  
  }  
  </style>