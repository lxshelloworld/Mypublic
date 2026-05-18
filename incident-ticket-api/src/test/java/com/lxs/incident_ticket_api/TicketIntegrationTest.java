package com.lxs.incident_ticket_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lxs.incident_ticket_api.entity.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TicketIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateTicket() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setTitle("数据库连接故障");
        ticket.setDescription("生产环境 MySQL 无法连接");
        ticket.setPriority(Ticket.Priority.CRITICAL);
        ticket.setSystemName("payment-system");
        ticket.setAssignee("张三");

        MvcResult result = mockMvc.perform(post("/api/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticket)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", equalTo("数据库连接故障")))
                .andExpect(jsonPath("$.status", equalTo("OPEN")))
                .andExpect(jsonPath("$.priority", equalTo("CRITICAL")))
                .andReturn();

        System.out.println("✓ 测试 1 通过：创建工单成功");
    }

    @Test
    void testGetAllTickets() throws Exception {
        // 先创建两个工单
        createTestTicket("故障1", "HIGH");
        createTestTicket("故障2", "LOW");

        mockMvc.perform(get("/api/tickets")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))));

        System.out.println("✓ 测试 2 通过：获取工单列表成功");
    }

    @Test
    void testGetTicketById() throws Exception {
        // 创建工单
        MvcResult createResult = mockMvc.perform(post("/api/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createTestTicketObject())))
                .andReturn();

        String response = createResult.getResponse().getContentAsString();
        Long ticketId = objectMapper.readTree(response).get("id").asLong();

        // 获取工单详情
        mockMvc.perform(get("/api/tickets/" + ticketId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(ticketId.intValue())))
                .andExpect(jsonPath("$.title", notNullValue()));

        System.out.println("✓ 测试 3 通过：获取工单详情成功");
    }

    @Test
    void testUpdateStatus() throws Exception {
        // 创建工单
        MvcResult createResult = mockMvc.perform(post("/api/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createTestTicketObject())))
                .andReturn();

        String response = createResult.getResponse().getContentAsString();
        Long ticketId = objectMapper.readTree(response).get("id").asLong();

        // 更新状态
        mockMvc.perform(patch("/api/tickets/" + ticketId + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"RESOLVED\""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", equalTo("RESOLVED")));

        System.out.println("✓ 测试 4 通过：更新工单状态成功");
    }

    @Test
    void testUpdateAssignee() throws Exception {
        // 创建工单
        MvcResult createResult = mockMvc.perform(post("/api/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createTestTicketObject())))
                .andReturn();

        String response = createResult.getResponse().getContentAsString();
        Long ticketId = objectMapper.readTree(response).get("id").asLong();

        // 更新负责人
        mockMvc.perform(patch("/api/tickets/" + ticketId + "/assignee")
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"李四\""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assignee", equalTo("李四")));

        System.out.println("✓ 测试 5 通过：更新负责人成功");
    }

    @Test
    void testUpdateNote() throws Exception {
        // 创建工单
        MvcResult createResult = mockMvc.perform(post("/api/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createTestTicketObject())))
                .andReturn();

        String response = createResult.getResponse().getContentAsString();
        Long ticketId = objectMapper.readTree(response).get("id").asLong();

        // 更新备注
        mockMvc.perform(patch("/api/tickets/" + ticketId + "/note")
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"已开始调查，预计2小时内解决\""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.note", equalTo("已开始调查，预计2小时内解决")));

        System.out.println("✓ 测试 6 通过：更新备注成功");
    }

    @Test
    void testGetTicketsByStatus() throws Exception {
        createTestTicket("开放工单", "HIGH");
        
        mockMvc.perform(get("/api/tickets?status=OPEN")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].status", everyItem(equalTo("OPEN"))));

        System.out.println("✓ 测试 7 通过：按状态筛选工单成功");
    }

    @Test
    void testGetTicketsByPriority() throws Exception {
        createTestTicket("高优先级", "CRITICAL");
        
        mockMvc.perform(get("/api/tickets?priority=CRITICAL")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].priority", everyItem(equalTo("CRITICAL"))));

        System.out.println("✓ 测试 8 通过：按优先级筛选工单成功");
    }

    @Test
    void testDeleteTicket() throws Exception {
        // 创建工单
        MvcResult createResult = mockMvc.perform(post("/api/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createTestTicketObject())))
                .andReturn();

        String response = createResult.getResponse().getContentAsString();
        Long ticketId = objectMapper.readTree(response).get("id").asLong();

        // 删除工单
        mockMvc.perform(delete("/api/tickets/" + ticketId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // 验证删除成功（应该返回 404）
        mockMvc.perform(get("/api/tickets/" + ticketId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        System.out.println("✓ 测试 9 通过：删除工单成功");
    }

    @Test
    void testGetNonExistentTicket() throws Exception {
        mockMvc.perform(get("/api/tickets/99999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        System.out.println("✓ 测试 10 通过：查询不存在的工单返回 404");
    }

    @Test
    void testCreateTicketWithoutTitle() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setDescription("没有标题的工单");

        mockMvc.perform(post("/api/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticket)))
                .andExpect(status().isBadRequest());

        System.out.println("✓ 测试 11 通过：创建工单时缺少标题正确返回 400");
    }

    @Test
    void testInvalidStatus() throws Exception {
        // 创建工单
        MvcResult createResult = mockMvc.perform(post("/api/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createTestTicketObject())))
                .andReturn();

        String response = createResult.getResponse().getContentAsString();
        Long ticketId = objectMapper.readTree(response).get("id").asLong();

        // 使用非法状态值
        mockMvc.perform(patch("/api/tickets/" + ticketId + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"INVALID_STATUS\""))
                .andExpect(status().isBadRequest());

        System.out.println("✓ 测试 12 通过：使用非法状态值正确返回 400");
    }

    // 辅助方法
    private void createTestTicket(String title, String priority) throws Exception {
        Ticket ticket = new Ticket();
        ticket.setTitle(title);
        ticket.setDescription("测试工单");
        ticket.setPriority(Ticket.Priority.valueOf(priority));
        ticket.setSystemName("test-system");

        mockMvc.perform(post("/api/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticket)));
    }

    private Ticket createTestTicketObject() {
        Ticket ticket = new Ticket();
        ticket.setTitle("测试工单");
        ticket.setDescription("这是一个测试工单");
        ticket.setPriority(Ticket.Priority.HIGH);
        ticket.setSystemName("test-system");
        ticket.setAssignee("测试人员");
        return ticket;
    }
}

