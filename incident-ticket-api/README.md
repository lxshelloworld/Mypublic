# 工单管理 API 项目使用说明

## 项目概览

这是一个 **障害・保守対応チケット管理 API**（工单管理系统），适用于系统保守/运维团队内部使用。

### 技术栈
- **Java 21**
- **Spring Boot 3.5.14**
- **Spring Data JPA**
- **H2 内存数据库**（开发测试用）
- **PostgreSQL** 支持（生产环境可配置）
- **Lombok**
- **Maven**

---

## 快速开始

### 1. 启动项目

#### 方式 A：IntelliJ IDEA（推荐）
1. 右键点击 `IncidentTicketApiApplication.java`
2. 选择 **Run 'IncidentTicketApiApplication'**
3. 等待控制台显示 `Tomcat started on port(s): 8080`

#### 方式 B：使用命令行
```bash
mvn spring-boot:run
```

#### 方式 C：运行已构建的 JAR
```bash
mvn clean package
java -jar target/incident-ticket-api-0.0.1-SNAPSHOT.jar
```

### 2. 查看数据库（H2 Web 控制台）
项目启动后，访问：
```
http://localhost:8080/h2-console
```
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **User**: `sa`
- **Password**: （空）

---

## API 接口列表

### 1. 创建工单
```
POST /api/tickets
```

请求体示例：
```json
{
  "title": "数据库连接故障",
  "description": "生产环境 MySQL 无法连接",
  "priority": "CRITICAL",
  "systemName": "payment-system",
  "assignee": "张三"
}
```

响应：
```json
{
  "id": 1,
  "title": "数据库连接故障",
  "description": "生产环境 MySQL 无法连接",
  "status": "OPEN",
  "priority": "CRITICAL",
  "systemName": "payment-system",
  "assignee": "张三",
  "note": null,
  "occurredAt": null,
  "createdAt": "2026-05-17T10:30:00",
  "updatedAt": "2026-05-17T10:30:00"
}
```

---

### 2. 获取工单列表（支持筛选）
```
GET /api/tickets
```

**查询参数**（可选）：
- `status` - 工单状态：OPEN, INVESTIGATING, RESOLVED, CLOSED
- `priority` - 优先级：LOW, MEDIUM, HIGH, CRITICAL
- `systemName` - 系统名称

**示例**：
```
GET /api/tickets
GET /api/tickets?status=OPEN
GET /api/tickets?priority=CRITICAL
GET /api/tickets?systemName=payment-system
GET /api/tickets?status=OPEN&priority=HIGH&systemName=api-gateway
```

---

### 3. 获取工单详情
```
GET /api/tickets/{id}
```

**示例**：
```
GET /api/tickets/1
```

---

### 4. 更新工单状态
```
PATCH /api/tickets/{id}/status
```

请求体（纯字符串，包含双引号）：
```
"RESOLVED"
```

**支持的状态值**：
- `OPEN` - 开放
- `INVESTIGATING` - 调查中
- `RESOLVED` - 已解决
- `CLOSED` - 已关闭

---

### 5. 更新负责人
```
PATCH /api/tickets/{id}/assignee
```

请求体：
```
"李四"
```

---

### 6. 更新处理备注
```
PATCH /api/tickets/{id}/note
```

请求体：
```
"已联系用户，问题已确认，预计 2 小时内解决"
```

---

### 7. 删除工单
```
DELETE /api/tickets/{id}
```

**示例**：
```
DELETE /api/tickets/1
```

成功返回：**204 No Content**

---

## 如何测试

### 方式 1：使用 Postman（推荐）

1. 下载并安装 [Postman](https://www.postman.com/downloads/)
2. 启动项目后，创建 POST 请求：
   - URL: `http://localhost:8080/api/tickets`
   - Method: POST
   - Headers: `Content-Type: application/json`
   - Body (raw JSON):
     ```json
     {
       "title": "系统故障",
       "description": "服务无法启动",
       "priority": "HIGH",
       "systemName": "batch-system"
     }
     ```
3. 点击 **Send** 执行请求

### 方式 2：使用 IntelliJ IDEA REST Client

1. 打开项目根目录的 `api-test.http` 文件
2. 每个请求左边有一个 **▶ Run** 按钮
3. 点击按钮即可执行请求，右侧查看响应

这个文件包含 25 个预设的测试用例，覆盖所有 API 功能和各种错误场景。

### 方式 3：使用 curl（PowerShell）

```powershell
# 创建工单
$body = @{
    title = "数据库故障"
    priority = "CRITICAL"
    systemName = "payment"
} | ConvertTo-Json

Invoke-WebRequest -Uri "http://localhost:8080/api/tickets" `
  -Method POST `
  -Headers @{ "Content-Type" = "application/json" } `
  -Body $body

# 获取所有工单
Invoke-WebRequest -Uri "http://localhost:8080/api/tickets" -Method GET

# 获取工单详情
Invoke-WebRequest -Uri "http://localhost:8080/api/tickets/1" -Method GET

# 更新状态
Invoke-WebRequest -Uri "http://localhost:8080/api/tickets/1/status" `
  -Method PATCH `
  -Headers @{ "Content-Type" = "application/json" } `
  -Body '"RESOLVED"'

# 删除工单
Invoke-WebRequest -Uri "http://localhost:8080/api/tickets/1" -Method DELETE
```

### 方式 4：运行自动化测试

```bash
mvn test
```

这会运行 `TicketIntegrationTest` 测试类，包含 12 个完整的集成测试用例。

---

## 工单字段说明

| 字段 | 类型 | 说明 | 必填 |
|------|------|------|------|
| id | Long | 工单编号 | 自动生成 |
| title | String | 工单标题 | ✓ |
| description | String | 工单详细描述 | |
| status | Enum | 工单状态（OPEN/INVESTIGATING/RESOLVED/CLOSED） | 创建时默认为 OPEN |
| priority | Enum | 优先级（LOW/MEDIUM/HIGH/CRITICAL） | |
| assignee | String | 负责人 | |
| systemName | String | 系统名称 | |
| occurredAt | DateTime | 障害发生时间 | |
| note | String | 处理备注 | |
| createdAt | DateTime | 创建时间 | 自动记录 |
| updatedAt | DateTime | 更新时间 | 自动记录 |

---

## 业务规则

1. **创建工单时**：
   - title（标题）必填，不能为空
   - status 自动设置为 OPEN
   - createdAt 和 updatedAt 自动记录

2. **更新时**：
   - status 和 priority 必须是有效的枚举值
   - 所有修改都会自动更新 updatedAt 时间戳

3. **删除操作**：
   - 删除不存在的工单返回 404 Not Found
   - 成功删除返回 204 No Content

4. **查询**：
   - 查询不存在的工单返回 404 Not Found
   - 支持按状态、优先级、系统名多条件筛选

---

## 项目结构

```
src/
  main/
    java/
      com/
        lxs/
          incident_ticket_api/
            IncidentTicketApiApplication.java  # 启动类
            entity/
              Ticket.java                      # 工单实体
            repository/
              TicketRepository.java            # 数据访问层
            service/
              TicketService.java               # 业务逻辑层
            controller/
              TicketController.java            # REST API 层
            exception/
              TicketNotFoundException.java     # 工单未找到异常
              GlobalExceptionHandler.java      # 全局异常处理器
    resources/
      application.yml                          # 应用配置

  test/
    java/
      com/
        lxs/
          incident_ticket_api/
            TicketIntegrationTest.java         # 集成测试

api-test.http                                  # REST Client 测试脚本
```

---

## 常见问题

### Q: 项目启动失败？
A: 
1. 确保 Java 版本为 21 或更高
2. 运行 `mvn clean compile` 重新编译
3. 检查端口 8080 是否被占用

### Q: 如何切换到 PostgreSQL？
A: 修改 `application.yml`，取消 PostgreSQL 配置的注释，注释掉 H2 配置，并确保 PostgreSQL 服务运行。

### Q: 更新状态时提示解析错误？
A: 
- 确保请求体是纯字符串，不是 JSON 对象
- 正确的格式：`"RESOLVED"`（包含双引号）
- 错误的格式：`{"status": "RESOLVED"}`

### Q: 如何查看数据库内容？
A: 访问 H2 Web 控制台：`http://localhost:8080/h2-console`

---

## 扩展计划

- [ ] 自动化告警接入接口（接收监控告警，自动生成工单）
- [ ] 工单分配给多人（Group by）
- [ ] 工单查询历史记录
- [ ] 工单评论功能
- [ ] 定时任务（如超期工单预警）
- [ ] 前端管理界面
- [ ] Docker 容器化部署
- [ ] API 文档（Swagger/OpenAPI）

---

## 技术文档

- [Spring Boot 官方文档](https://spring.io/projects/spring-boot)
- [Spring Data JPA 文档](https://spring.io/projects/spring-data-jpa)
- [H2 数据库文档](http://h2database.com/)
- [Lombok 文档](https://projectlombok.org/)

---

**项目维护日期**: 2026-05-17

