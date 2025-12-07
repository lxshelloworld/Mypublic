
![项目架构图](./images/architecture-diagram.png)

### **架构概述：**
这个项目使用 Google Cloud 部署，结合 Docker 容器化和 CI/CD 流程，实现自动化部署。

### **使用的技术：**
| 技术栈        | 用途                                      |
| ------------- | ----------------------------------------- |
| Google Cloud  | 托管虚拟机和 DNS 配置                    |
| Docker/Podman | 容器化前端、后端和数据库                |
| Nginx         | 反向代理，处理前后端请求和 CORS 配置     |
| GitHub Actions| 自动化构建和部署流程，触发容器镜像的更新|
| Cloud DNS     | 配置域名解析                             |

### 部署流程
1.开发与提交：在本地开发，使用 Git 推送代码到 GitHub。
2.GitHub Actions 会自动触发 CI/CD 流程。
3.前端和后端容器镜像会被构建并推送到 Docker Hub。
4.服务器通过拉取镜像来更新容器。
5.用户通过配置好的域名访问应用。

### 访问与管理
用户可以通过域名（`lxhelloworld.com`）在浏览器中访问应用。
运维人员可以通过 **SSH** 或 **Tera Term** 远程管理虚拟机，检查系统状态。
GitHub Actions：管理 CI/CD 自动化部署。
