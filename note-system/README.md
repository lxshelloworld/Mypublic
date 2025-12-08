[🇨🇳 英語版](README-en.md) | [🇯🇵 中国語版](README-zh.md)
---

# 📒 Note System – 前後端分離ノートシステム

Spring Boot + React を用いた前後端分離型のノートシステムです。ノート管理、コンテナ化によるデプロイ、クラウド上での自動デプロイを実現しています。
本プロジェクトは Google Cloud Platform（GCP）および Amazon Web Services（AWS Tokyo）にデプロイされ、クラウドアーキテクチャ設計や DevOps 実践の学習を目的としています。

オンラインデモ: https://lxshelloworld.com/login

---
## 🚀 1. プロジェクトのハイライト（Highlights）

- ✔️ 前後端完全分離：React SPA + Spring Boot REST API
- ✔️ ノートの作成・編集・削除・一覧表示までの完全機能
- ✔️ フロントエンド、バックエンド、データベースの全てをコンテナ管理
- ✔️ Docker コンテナ化 + GitHub Actions による CI/CD 自動デプロイ
- ✔️ マルチクラウド経験：GCP → AWS Tokyo
- ✔️ AWS で企業レベルに近いマルチアベイラビリティゾーン（Multi-AZ）構成

---

## ☁️ 2.クラウドデプロイ（Cloud Deployment）

本プロジェクトは 2 つのフェーズ でクラウドにデプロイされました：
1. GCP（初回クラウドデプロイ学習）
2. AWS Tokyo（企業向け高可用性デプロイ）

### 2.1 GCP デプロイ（2025/5 ~ 2025/8）

**概要：**
Google Cloud 上で Docker コンテナ化と CI/CD フローを組み合わせ、自動デプロイを実現。クラウド基礎デプロイ、DNS、コンテナ管理、CI/CD の学習用。

**アーキテクチャ図：**
<img width="1763" height="707" alt="GCP" src="https://github.com/user-attachments/assets/9423133d-47a2-48b8-ad27-0d7619991e4f" />
[GCPクラウド構築.pdf](./04.architecture/01.GCP/GCPクラウド構築.pdf)


**使用技術：**
| 技术栈        | 用途                                      |
| ------------- | ----------------------------------------- |
| Google Cloud  | 仮想マシンと DNS 設定のホスティング                     |
| Docker/Podman | フロントエンド、バックエンド、DB のコンテナ化            |
| Nginx         | 逆プロキシ、フロント/バックエンドリクエストと CORS 設定   |
| GitHub Actions| CI/CD の自動構築・デプロイ、コンテナイメージ更新トリガー  |
| Cloud DNS     | ドメインネームシステム設定                              |


**🔄 デプロイフロー：**
1. ローカルで開発 → Git で GitHub にプッシュ。
2. GitHub Actions が自動で CI/CD をトリガー。
3. フロントエンド・バックエンドの Docker イメージを構築し Docker Hub にプッシュ。
4. GCP サーバーが最新イメージをプルしてコンテナを更新。


**アクセスと管理：**
- ユーザーはブラウザで lxhelloworld.comからアクセス可能。
- 運用者は SSH または Tera Term で VM をリモート管理、状態確認。
- GitHub Actions：CI/CD 自動デプロイ管理。
- Docker Hub：コンテナイメージ管理。
  
---

### 2.2 AWS デプロイ（2025/10 - 2026/1）

**概要：**
AWS Tokyo リージョンにデプロイし、企業レベルに近い 高可用性アーキテクチャ（Multi-AZ） を構築。

**アーキテクチャ図：**

<img width="741" height="456" alt="image" src="https://github.com/user-attachments/assets/4a7c995b-1c26-4d86-8522-9b3ca8b9b03c" />



**システムコンポーネント：**
| 组件              | 描述                                      |
| ----------------- | ----------------------------------------- |
| VPC               | 仮想プライベートクラウド、AWS リソースの分離・管理         |
| ECS on EC2        | EC2 上で動作するコンテナアプリ              |
| ECS Fargate       | サーバーレスコンテナ管理、自動リソース管理      |
| RDS               | マネージドデータベースサービス                           |
| NAT Gateway       | プライベートサブネットのインターネット接続                 |
| ELB               | Elastic Load Balancer、トラフィック分散          |
| Internet Gateway  | インターネット接続用ゲートウェイ          |
| Route53           | DNS サービス、ドメイン・ルーティング管理                 |
| CloudFront        | CDN サービス、グローバル高速配信           |
| CloudWatch        | 監視・ログ管理サービス                     |
| S3                | 静的ファイルストレージ               |
| Secrets Manager   | 機密情報の安全な管理                |
| ECR               | Docker イメージリポジトリ       |


**特徴：**
1. マルチ AZ 配置による高可用性
2. ECS Fargate によるコンテナ自動スケーリング
3. RDS によるプライベートサブネット安全アクセス
4. ELB で負荷分散、CloudFront による CDN 配信


**設計方針：**
1. **コスト管理**：可能な限り無料枠で利用できるサービスを優先し、必要な場合のみ重要な有料サービスを採用する。
2. **目的**：AWS の無料利用枠を最大限活用しながら、多様なサービスを試し、実践的なクラウド設計を学習する。
3. **AWS Well-Architected 原則の遵守**


**ネットワーク構成図：**

[awsネットワーク.pdf](./04.architecture/02.AWS/awsネットワーク.pdf)


---

## 🧩3.システム概要

**システム機能**

| 功能名         | 描述                                      |
| -------------- | ---------------------------------------- |
| ユーザー登録      | メールとパスワードでアカウント作成           |
| ログイン          | ユーザー認証                                |
| ノート一覧表示 | 作成したノートを一覧表示                   |
| ノート作成     | タイトル・内容を入力して新規作成              |
| ノート編集      | 既存ノートの編集                             |
| ノート削除      | 不要なノートを削除（確認ダイアログあり）        |

**技術スタック**

| 层（组件）    | 技术               | 描述                              | 版本                      |
| ------------- | ------------------ | --------------------------------- | ------------------------- |
| フロントエンド | React, Tailwind CSS | UI 作成およびバックエンド通信 | Node.js 18.x React 18.2.0 Tailwind CSS 3.4.1 |
| バックエンド   | Spring Boot (Java)  | REST API、認証、ノート管理   | Spring Boot 3.2.5 Java 21 |
| データベース   | PostgreSQL          | ユーザー、ノート、タグの保存   | PostgreSQL 16.2           |
| コンテナ化     | Docker, Docker Compose | 環境構築・管理の自動化     |                           |
| CI/CD         | GitHub Actions      | 自動構築・テスト・デプロイ     |                           |

## 📁 4.ディレクトリ構造（Repository Structure）

```plaintext
note-system
├── 01.docs                # システム関連設計文書
├── 02.backend        
   └── note-backend/  # Spring Boot バックエンド
├── 03.frontend      
   └── note-frontend/ # React フロントエンド
├── 04.architecture        # クラウドデプロイアーキテクチャ図
├── 05.demo                # システムデモ
├── README.md              # プロジェクト説明
```

## 🔗 5.参照リンク
- 📦 GitHub リポジトリ：https://github.com/lxshelloworld/Mypublic/note-system
- 🐳 DockerHub ：https://hub.docker.com/repositories/lfortest
- 🌍 オンラインデモ：https://lxshelloworld.com/login
  
