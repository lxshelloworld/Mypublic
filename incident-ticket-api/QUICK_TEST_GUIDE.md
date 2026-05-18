# 快速测试指南

## 5分钟快速验证项目

### 步骤 1：启动项目（1分钟）
```bash
右键点击 IncidentTicketApiApplication.java
选择 Run 'IncidentTicketApiApplication'
```

等待看到：
```
o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080
```

### 步骤 2：打开测试脚本（30秒）
在 IDE 中打开 `api-test.http` 文件

### 步骤 3：执行测试（3分钟）

按以下顺序点击每个请求旁边的 **Run** 按钮：

#### 测试 CRUD 基础功能

| 序号 | 请求名称 | 预期结果 |
|------|---------|---------|
| 1 | 创建工单 | 返回 201/200，包含 id=1 |
| 2 | 创建第二个工单 | 返回 id=2 |
| 3 | 创建第三个工单 | 返回 id=3 |
| 4 | 获取所有工单 | 返回数组，包含 3 个工单 |
| 11 | 获取工单详情 (id=1) | 返回单个工单对象 |
| 14 | 更新工单状态 | 状态改为 INVESTIGATING |
| 17 | 更新负责人 | assignee 改为 "王五" |
| 18 | 更新备注 | note 填充内容 |
| 20 | 删除工单 (id=3) | 返回 204 No Content |

#### 测试筛选功能

| 序号 | 请求名称 | 预期结果 |
|------|---------|---------|
| 5 | 按状态筛选 OPEN | 返回所有状态为 OPEN 的工单 |
| 6 | 按优先级筛选 HIGH | 返回所有优先级为 HIGH 的工单 |
| 7 | 按优先级筛选 CRITICAL | 只返回 CRITICAL 的工单 |
| 8 | 按系统名筛选 | 只返回指定系统的工单 |

#### 测试错误处理

| 序号 | 请求名称 | 预期结果 |
|------|---------|---------|
| 13 | 查询不存在工单 | **404 Not Found** |
| 21 | 验证删除 | **404 Not Found**（已删除） |
| 23 | 缺少标题创建 | **400 Bad Request** |
| 24 | 非法状态值 | **400 Bad Request** |
| 25 | 删除不存在工单 | **404 Bad Request** |

### 步骤 4：查看数据库（可选）

访问 H2 Web 控制台：
```
http://localhost:8080/h2-console
```

点击 Connect，然后执行查询：
```sql
SELECT * FROM tickets;
SELECT * FROM tickets WHERE status = 'OPEN';
SELECT COUNT(*) FROM tickets;
```

---

## 常见状态码说明

| HTTP 状态码 | 说明 | 原因 |
|------------|------|------|
| **200 OK** | 成功 | GET/POST/PATCH 成功 |
| **204 No Content** | 成功删除 | DELETE 成功 |
| **400 Bad Request** | 请求错误 | 缺少必填字段、枚举值错误 |
| **404 Not Found** | 未找到 | 工单不存在 |
| **500 Server Error** | 服务器错误 | 代码异常 |

---

## 测试检查清单

运行所有测试后，检查以下项目是否都通过 ✓

### 功能测试
- [ ] 能创建工单
- [ ] 能查询工单列表
- [ ] 能查询单个工单
- [ ] 能更新工单状态
- [ ] 能更新负责人
- [ ] 能更新备注
- [ ] 能删除工单
- [ ] 删除后查询返回 404

### 筛选测试
- [ ] 能按状态筛选
- [ ] 能按优先级筛选
- [ ] 能按系统名筛选
- [ ] 能多条件组合筛选

### 验证测试
- [ ] 缺少标题时返回 400
- [ ] 非法枚举值返回 400
- [ ] 删除不存在工单返回 404
- [ ] 查询不存在工单返回 404

### 数据完整性
- [ ] createdAt 自动填充
- [ ] updatedAt 自动填充
- [ ] status 默认为 OPEN
- [ ] 修改后 updatedAt 会更新

---

## 如果测试失败

### 问题：项目无法启动

**可能原因**：
- Java 版本不是 21
- 端口 8080 被占用
- 依赖未下载

**解决方案**：
1. 检查 Java 版本：`java -version`（应该是 21.x）
2. 修改端口：在 `application.yml` 添加 `server.port: 8081`
3. 重新下载依赖：`mvn clean install`

### 问题：API 返回 404

**原因**：
- 项目没有启动
- URL 拼写错误
- 工单 ID 不存在

**解决方案**：
1. 确认项目已启动（控制台显示 "Tomcat started"）
2. 检查 URL 是否正确（如 `/api/tickets/1`）
3. 先创建工单，再查询

### 问题：状态更新返回 400

**原因**：
- 请求体格式错误

**正确格式**：
```
PATCH /api/tickets/1/status
Content-Type: application/json

"RESOLVED"
```

**错误格式**：
```json
{
  "status": "RESOLVED"
}
```

### 问题：H2 控制台无法访问

**原因**：
- H2 控制台未启用（检查 application.yml）

**解决方案**：
```yaml
spring:
  h2:
    console:
      enabled: true
      path: /h2-console
```

---

## 自动化测试

除了手动测试，也可以运行自动化测试：

```bash
mvn test
```

这会执行 `TicketIntegrationTest` 中的 12 个测试用例：
1. testCreateTicket - 创建工单
2. testGetAllTickets - 获取列表
3. testGetTicketById - 获取详情
4. testUpdateStatus - 更新状态
5. testUpdateAssignee - 更新负责人
6. testUpdateNote - 更新备注
7. testGetTicketsByStatus - 按状态筛选
8. testGetTicketsByPriority - 按优先级筛选
9. testDeleteTicket - 删除工单
10. testGetNonExistentTicket - 404 错误
11. testCreateTicketWithoutTitle - 400 错误（缺字段）
12. testInvalidStatus - 400 错误（无效值）

测试通过时，控制台会显示：
```
✓ 测试 1 通过：创建工单成功
✓ 测试 2 通过：获取工单列表成功
... 等等
```

---

## 性能基准

在标准开发环境（H2 内存数据库）上的预期性能：

| 操作 | 平均响应时间 |
|------|-----------|
| 创建工单 | < 50ms |
| 查询列表（无筛选） | < 10ms |
| 查询详情 | < 10ms |
| 更新工单 | < 30ms |
| 删除工单 | < 20ms |
| 查询 1000 条记录 | < 100ms |

---

## 下一步

✅ 所有基础功能都测试通过后，可以：

1. **切换到 PostgreSQL**
   - 修改 application.yml
   - 运行 `CREATE DATABASE incident_ticket_db;`
   - 重启项目

2. **添加更多功能**
   - 监控告警接入
   - API 文档（Swagger）
   - 单元测试

3. **部署到生产**
   - Docker 容器化
   - docker-compose 编排
   - CI/CD 流程

---

**提示**：如果所有测试都通过，项目可以投入使用！🎉


