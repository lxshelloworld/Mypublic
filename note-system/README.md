# 📒 Note System – 前后端分离笔记系统

一个基于 Spring Boot + React 的前后端分离笔记系统，实现用户认证、笔记管理、容器化部署与云端自动化上线。 项目先后部署在 Google Cloud Platform（GCP） 与 Amazon Web Services（AWS Tokyo），用于学习云端架构设计与 DevOps 实践。

在线演示  https://lxshelloworld.com/login

---
## 🚀 1. 项目亮点（Highlights）

- ✔️ 前后端完全分离：React SPA + Spring Boot REST API 
- ✔️ 笔记增删改查完整功能链路 
- ✔️ 前端、后端、数据库均实现容器化管理
- ✔️ Docker 容器化 + GitHub Actions CI/CD 全自动部署 
- ✔️ 多云部署经验：GCP → AWS Tokyo 
- ✔️ AWS 实现接近企业级的多可用区架构（Multi-AZ） 

---

## ☁️ 2. 云端部署（Cloud Deployment）

本项目经历两阶段部署：
1. GCP（初次云部署练习）
2. AWS Tokyo（企业级部署实践）

### 2.1 GCP 部署 （2025/5 ~ 2025/8）

**架构概述：**
使用 Google Cloud 部署，结合 Docker 容器化和 CI/CD 流程，实现自动化部署。 用于学习云基础部署、DNS、容器管理与 CI/CD。

**架构图：**
<img width="1763" height="707" alt="GCP" src="https://github.com/user-attachments/assets/9423133d-47a2-48b8-ad27-0d7619991e4f" />
[GCPクラウド構築.pdf](./04.architecture/01.GCP/GCPクラウド構築.pdf)

**使用的技术：**
| 技术栈        | 用途                                      |
| ------------- | ----------------------------------------- |
| Google Cloud  | 托管虚拟机和 DNS 配置                    |
| Docker/Podman | 容器化前端、后端和数据库                |
| Nginx         | 反向代理，处理前后端请求和 CORS 配置     |
| GitHub Actions| 自动化构建和部署流程，触发容器镜像的更新|
| Cloud DNS     | 配置域名解析                             |


**🔄 部署流程：**
1. 在本地开发，使用 Git 推送代码到 GitHub。
2. **GitHub Actions** 会自动触发 **CI/CD** 流程。
3. 前端和后端容器镜像会被构建并推送到 Docker Hub。
4. GCP 服务器拉取最新镜像并更新容器。

**访问与管理：**
- 用户可以通过域名（`lxhelloworld.com`）在浏览器中访问应用。
- 运维可以通过 **SSH** 或 **Tera Term** 远程管理虚拟机，检查系统状态。
- GitHub Actions：管理 CI/CD 自动化部署。
- Docker Hub ：管理镜像。
  
---

### 2.2 AWS 部署 （2025/10-2026/1） 

**架构概述：**
部署在 **AWS Tokyo Region** ，构建接近实际企业生产环境的高可用架构（Multi-AZ）。

**架构图：**

<img width="741" height="456" alt="image" src="https://github.com/user-attachments/assets/4a7c995b-1c26-4d86-8522-9b3ca8b9b03c" />


**系统组件：**
| 组件              | 描述                                      |
| ----------------- | ----------------------------------------- |
| VPC               | 虚拟私有云，隔离和管理 AWS 资源          |
| ECS on EC2        | 运行在 EC2 实例上的容器应用              |
| ECS Fargate       | 无服务器容器管理，自动管理容器资源      |
| RDS               | 托管数据库服务                           |
| NAT Gateway       | 提供私有子网的互联网连接                 |
| ELB               | Elastic Load Balancer，分发流量          |
| Internet Gateway  | 互联网网关，用于连接公共互联网          |
| Route53           | DNS 服务，管理域名和路由                 |
| CloudFront        | CDN 服务，用于全球加速内容分发           |
| CloudWatch        | 监控和日志管理服务                       |
| S3                | 存储服务，用于存储静态文件               |
| Secrets Manager   | 安全地存储和管理敏感信息                 |
| ECR               | 容器镜像仓库，用于存储 Docker 镜像       |

**部署流程**
1. 在 **VPC** 内创建多个子网，分别部署 ECS 和 RDS。
2. 使用 **ELB** 分发流量，确保高可用性。
3. 配置 **NAT Gateway**，使私有子网能够访问互联网。
4. 部署 **ECS** 容器化应用，通过 **ECS Fargate** 实现自动扩展。
5. 配置 **RDS** 实例，提供数据库服务。

**设计方针：**
1. **费用控制**：优先选择不产生费用的服务，必要时使用重要的付费服务。
2. **目标**：尽量在免费额度内使用多种服务。
3. **遵循 AWS 设计原则**：
   - **安全性**  
   - **可用性**  
   - **高性能**  
   - **成本优化**  
   - **卓越运营**  
   - **可持续性**

**网络结构图**
[awsネットワーク.pdf](./04.architecture/02.AWS/awsネットワーク.pdf)


---

## 🧩3.系统部分介绍

**系统功能**

| 功能名         | 描述                                      |
| -------------- | ---------------------------------------- |
| 用户注册      | 使用电子邮件和密码创建用户账号           |
| 登录          | 用户登录                                |
| 笔记列表展示  | 显示用户创建的所有笔记                   |
| 笔记创建      | 输入标题和内容创建新的笔记              |
| 笔记编辑      | 编辑已有笔记                             |
| 笔记删除      | 删除不需要的笔记（带确认对话框）         |

**系统技术栈**

| 层（组件）    | 技术               | 描述                              | 版本                      |
| ------------- | ------------------ | --------------------------------- | ------------------------- |
| 前端         | React, Tailwind CSS | 用于创建 UI 和与后端通信的 React 应用 | Node.js 18.x React 18.2.0 Tailwind CSS 3.4.1 |
| 后端         | Spring Boot (Java)  | 提供 REST API、认证和笔记处理       | Spring Boot 3.2.5 Java 21 |
| 数据库       | PostgreSQL          | 存储用户、笔记和标签               | PostgreSQL 16.2           |
| 容器化       | Docker, Docker Compose | 自动化环境构建和统一管理          |                           |
| CI/CD         | GitHub Actions      | 自动化构建、测试和部署             |                           |

**系统架构图**

```plaintext
+--------------------+            +-------------------+           +----------------+
|                    |            |                   |           |                |
|  用户浏览器        | <-------> | React 前端         |  <----->  |  Spring Boot   |
| (React SPA 应用)   |  HTTP API  |(UI 和路由管理)     | REST API | 后端服务        |
|                    |            |                   |           |                |
+--------------------+            +-------------------+           +----------------+
                                                                       |
                                                                       v
                                                            +--------------------+
                                                            |                    |
                                                            |    PostgreSQL      |
                                                            |    数据库          |
                                                            +--------------------+
```
## 📁 4.目录结构（Repository Structure）

```plaintext
note-system
├── 01.docs                # 系统相关设计文档
├── 02.backend        
   └── note-backend/ # Spring Boot 后端 
├── 03.frontend      
   └── note-frontend/ # React 前端 
├── 04.architecture        # 云部署架构图
├── 05.demo                # 系统演示
├── README.md              # 项目说明
```

## 🔗 5.访问地址
- 📦 GitHub 仓库：https://github.com/lxshelloworld/Mypublic/note-system
- 🐳 DockerHub 仓库：https://hub.docker.com/repositories/lfortest
- 🌍 线上 Demo：https://lxshelloworld.com/login 
