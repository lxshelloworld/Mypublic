# 第一步：使用 node 环境构建 vite 项目
FROM node:20-alpine AS builder

# 设置工作目录
WORKDIR /app

# 安装依赖并构建
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# 第二步：使用 nginx 部署静态文件
FROM nginx:alpine

# 删除默认 nginx 静态文件配置
RUN rm -rf /usr/share/nginx/html/*

# 复制 vite 构建后的文件到 nginx 路径
COPY --from=builder /app/dist /usr/share/nginx/html

# 自定义 nginx 配置（404问题）
# COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
