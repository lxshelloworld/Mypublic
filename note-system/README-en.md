[jp Japanese](README.md) | [cn Chinese](README-zh.md)
---

# 📒 Note System – Frontend-Backend Separated Note System

This project is a frontend-backend separated note-taking system built with Spring Boot + React.
It supports note management, containerized deployment, and automated cloud deployment.
The system has been deployed on Google Cloud Platform (GCP) and Amazon Web Services (AWS Tokyo) as part of learning cloud architecture design and DevOps practices.

Online Demo: https://lxshelloworld.com/login

---
## 🚀 1. Project Highlights

- ✔️ Full frontend-backend separation: React SPA + Spring Boot REST API
- ✔️ Complete note features: Create, Edit, Delete, View
- ✔️ Frontend, backend, and database fully containerized
- ✔️ Docker-based deployment + GitHub Actions CI/CD
- ✔️ Multi-cloud experience: GCP → AWS Tokyo
- ✔️ Near enterprise-level Multi-AZ architecture on AWS

---

## ☁️ 2.Cloud Deployment

The project was deployed in two phases:
1. GCP – Initial cloud deployment learning
2. AWS Tokyo – Enterprise-level high availability deployment

### 2.1 GCP Deployment (2025/5 ~ 2025/8)

**Overview:**
Deployed on Google Cloud using Docker containerization plus a CI/CD pipeline.
Designed for learning cloud basics, DNS, container management, and automated deployment.

**Architecture Diagram:**
<img width="1763" height="707" alt="GCP" src="https://github.com/user-attachments/assets/9423133d-47a2-48b8-ad27-0d7619991e4f" />
[GCPクラウド構築.pdf](./04.architecture/01.GCP/GCPクラウド構築.pdf)


**Technologies Used:**
| Technology Stack        | Purpose                                      |
| ------------- | ----------------------------------------- |
| Google Cloud  | Hosting VMs and DNS configuration                    |
| Docker/Podman | Containerizing frontend, backend, and DB            |
| Nginx         | Reverse proxy, request routing, CORS handling   |
| GitHub Actions| CI/CD automation, builds & deploys containers  |
| Cloud DNS     | Domain name management                              |


**🔄 Deployment Flow:**
1. Develop locally → Push to GitHub
2. GitHub Actions triggers CI/CD automatically
3. Docker images for frontend & backend are built and pushed to Docker Hub
4. GCP server pulls latest images and updates containers


**Access & Management:**
- Users access the app via lxhelloworld.com
- Administrators manage the VM via SSH or Tera Term
- GitHub Actions handles CI/CD automation
- Docker Hub stores container images
  
---

### 2.2 AWS Deployment (2025/10 – 2026/1)

**Overview:**
Deployed in the AWS Tokyo Region with a production-like high-availability Multi-AZ architecture.

**Architecture Diagram:**

<img width="741" height="456" alt="image" src="https://github.com/user-attachments/assets/4a7c995b-1c26-4d86-8522-9b3ca8b9b03c" />



**System Components:**
| Component              | Description                                      |
| ----------------- | ----------------------------------------- |
| VPC               | Network isolation and resource management         |
| ECS on EC2        | Containerized app running on EC2            |
| ECS Fargate       | Serverless container management      |
| RDS               | Managed relational database                         |
| NAT Gateway       | Internet access for private subnets                |
| ELB               | Load balancing         |
| Internet Gateway  | Public internet connectivity         |
| Route53           | DNS service, domain routing              |
| CloudFront        | CDN for global content distribution         |
| CloudWatch        | Monitoring & logging                   |
| S3                | Static file storage             |
| Secrets Manager   | Secure credential storage               |
| ECR               | Container image registry      |


**Key Features:**
1. High availability with Multi-AZ deployment
2. ECS Fargate for auto scaling
3. Secure private subnet access to RDS
4. ELB load balancing + CloudFront CDN distribution


**Design Principles:**
1. **Cost Control:**：Prioritize free-tier services; use paid services only when necessary.
2. **Goal:**：Use as many AWS services as possible within the free tier.
3. **Follow AWS Well-Architected Framework**


**Network Diagram:**

[awsネットワーク.pdf](./04.architecture/02.AWS/awsネットワーク.pdf)


---

## 🧩3.System Overview

**System Features**

| Feature         | Description                                      |
| -------------- | ---------------------------------------- |
| User Registration      | Create account via email & password         |
| Login          | User authentication                             |
| View Notes | Display all user-created notes                  |
| Create Note     | Create a new note (title + content)             |
| Edit Note      | Modify existing notes                          |
| Delete Note      | Delete a note (with confirmation dialog)        |

**Tech Stack**

| Layer    | Technology               | Description                              | Version                      |
| ------------- | ------------------ | --------------------------------- | ------------------------- |
| Frontend | React, Tailwind CSS | UI + communication with backend| Node.js 18.x React 18.2.0 Tailwind CSS 3.4.1 |
| Backend   | Spring Boot (Java)  | REST API, authentication, note processing  | Spring Boot 3.2.5 Java 21 |
| Database   | PostgreSQL          | Store users, notes, tags  | PostgreSQL 16.2           |
| Containerization     | Docker, Docker Compose | Automated environment setup    |                           |
| CI/CD         | GitHub Actions      | Build, test, deploy automation     |                           |

---

## 📁 4.Repository Structure

```plaintext
note-system
├── 01.docs                # System design documents
├── 02.backend        
   └── note-backend/  # Spring Boot backend
├── 03.frontend      
   └── note-frontend/ # React frontend
├── 04.architecture        # Cloud architecture diagrams
├── 05.demo                # System demo files
├── README.md              # Project description
```
---

## 🔗 5.Links
- 📦 GitHub Repository: https://github.com/lxshelloworld/Mypublic/note-system
- 🐳 DockerHub: https://hub.docker.com/repositories/lfortest
- 🌍 Online Demo: https://lxshelloworld.com/login
  
