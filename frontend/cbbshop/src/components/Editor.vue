<template>
  <div style="border: 1px solid #ccc;">
      <Toolbar
          style="border-bottom: 1px solid #ccc"
          :editor="editor"
          :defaultConfig="toolbarConfig"
          :mode="mode"
      />
      <Editor
          style="height: 500px; overflow-y: hidden;"
          v-model="html"
          :defaultConfig="editorConfig"
          :mode="mode"
          @onCreated="onCreated"
      />
  </div>
</template>

<script>
import Vue from 'vue'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'

export default Vue.extend({
    components: { Editor, Toolbar },
    data() {
        return {
        editor: null,
        html: '<p>hello</p>',
        toolbarConfig: {},
        editorConfig: { placeholder: '请输入内容...' },
        mode: 'default', // or 'simple'
      }
    },
    methods: {
        onCreated(editor) {
            this.editor = Object.seal(editor) // 一定要用 Object.seal() ，否则会报错
        },
        getContent() {
            const editor = this.editor // 获取 editor 实例
            if (editor == null) return
            return editor.getHtml(); // 获取编辑器内容
         },
    },
    mounted() {
        // 模拟 ajax 请求，异步渲染编辑器
        setTimeout(() => {
            this.html = ''
        }, 1500)
    },
    beforeDestroy() {
        const editor = this.editor
        if (editor == null) return
        editor.destroy() // 组件销毁时，及时销毁编辑器
    }
})
</script>

<style src="@wangeditor/editor/dist/css/style.css"></style>
