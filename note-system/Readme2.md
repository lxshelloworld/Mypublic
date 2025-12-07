---

📒 Note System – 前后端分离笔记系统

一个基于 Spring Boot + React 的前后端分离笔记系统，实现用户认证、笔记管理、容器化部署与云端自动化上线。
项目先后部署在 Google Cloud Platform（GCP） 与 Amazon Web Services（AWS Tokyo），用于学习云端架构设计与 DevOps 实践。

> 在线演示 👉 https://lxshelloworld.com/login

---

🚀 1. 项目亮点（Highlights）

✔️ 前后端完全分离：React SPA + Spring Boot REST API
✔️ 支持 JWT 鉴权
✔️ 笔记增删改查完整功能链路
✔️ Docker 容器化 + GitHub Actions CI/CD 全自动部署
✔️ 多云部署经验：GCP → AWS Tokyo
✔️ AWS 实现接近企业级的多可用区架构（Multi-AZ）
✔️ 前端、后端、数据库均实现容器化管理

---

☁️ 2. 云端部署（Cloud Deployment）

本项目经历两阶段部署：
1. GCP（初次云部署练习）
2. AWS Tokyo（企业级部署实践）

---

2.1 GCP 部署（2025/5 ~ 2025/8）
用于学习云基础部署、DNS、容器管理与 CI/CD。

🔧 使用技术

技术	用途
Google Cloud VM	运行容器化应用
Docker / Podman	前后端 & DB 容器化
Nginx	反向代理与 CORS
Cloud DNS	域名解析
GitHub Actions	自动构建 & 推送镜像

🔄 部署流程

1. 开发后提交至 GitHub
2. GitHub Actions 构建镜像并推送至 Docker Hub
3. GCP 服务器拉取最新镜像并更新容器
4. 用户通过域名访问生产环境

---

2.2 AWS 部署（2025/10 ~ 2026/1）
目标：构建接近实际企业生产环境的高可用架构（Multi-AZ）。

🧩 AWS 架构组件
服务	用途

ECS（EC2/Fargate）	运行前后端容器
RDS PostgreSQL	托管关系数据库
VPC + Subnets	网络隔离与安全
ELB	负载均衡
NAT Gateway	私有子网访问互联网
S3	静态资源存储
CloudFront	CDN 加速
Route53	DNS
CloudWatch	日志 + 监控
Secrets Manager	管理密码与密钥
ECR	镜像仓库

🔄 部署流程

1. 在 VPC 内创建公有/私有子网
2. 前端、后端部署至 ECS（EC2 或 Fargate）
3. RDS 部署于 Multi-AZ
4. NAT+IGW 支撑网络出入口
5. ELB 分发流量
6. S3 + CloudFront 提供静态内容加速

🎯 设计原则
成本优化
高可用
安全性
弹性伸缩
可观察性（监控日志）

---

🧩 3. 系统功能（Features）

功能	描述

用户注册	邮箱 + 密码注册
用户登录	JWT 登录认证
笔记列表	展示所有笔记
创建笔记	输入标题与内容
编辑笔记	修改笔记
删除笔记	删除笔记（含二次确认）

---

🛠️ 4. 技术栈（Tech Stack）
前端（Frontend）
React 18
Tailwind CSS 3
React Router
Node.js 18
后端（Backend）
Spring Boot 3.2
Java 21
Spring Security + JWT
Spring Data JPA（Hibernate）
数据库（Database）
PostgreSQL 16
运维 / DevOps（Deployment & DevOps）
Docker & Docker Compose
GitHub Actions（CI/CD）
Google Cloud Platform（初期部署）
Amazon Web Services Tokyo（最终部署）
ECS / Fargate
RDS
Route53
CloudFront
S3
ECR
Secrets Manager
CloudWatch

---

📁 5. 项目目录结构（Repository Structure）

note-system/
├── 01.docs/                # 系统设计文档（架构、接口、流程）
├── 02.backend/
│   └── note-backend/       # Spring Boot 后端
├── 03.frontend/
│   └── note-frontend/      # React 前端
├── 04.architecture/        # 云端部署架构与图
├── 05.demo/                # 演示截图 / 视频
└── README.md               # 项目说明

🔗 7. 访问地址
🌍 线上 Demo：https://lxshelloworld.com/login
📦 GitHub 仓库：https://github.com/lxshelloworld/Mypublic

⭐ 8. 后续计划（Roadmap）
[ ] 添加标签 Tag 功能
[ ] 支持笔记搜索
[ ] 支持图片上传（S3）

---
