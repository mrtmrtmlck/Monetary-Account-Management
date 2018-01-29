package com.ingenico.assessment.accountmanagement;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import net.minidev.json.JSONObject;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountManagementApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void shouldCreateAccount() throws Exception {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("name", "test1");
		jsonObj.put("balance", 1000);

		mockMvc.perform(post("/api/newAccount").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(jsonObj.toJSONString())).andExpect(status().isOk());
	}

	@Test
	public void shouldNotBeNegativeBalance() throws Exception {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("name", "test2");
		jsonObj.put("balance", -500);

		mockMvc.perform(post("/api/newAccount").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(jsonObj.toJSONString()))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void shouldTransferMoney() throws Exception {
		// prepare sender account data
		JSONObject obj1 = new JSONObject();
		obj1.put("name", "sender");
		obj1.put("balance", 500);

		mockMvc.perform(post("/api/newAccount").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(obj1.toJSONString()));
		
		// prepare receiver account data
		JSONObject obj2 = new JSONObject();
		obj2.put("name", "receiver");
		obj2.put("balance", 200);

		mockMvc.perform(post("/api/newAccount").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(obj2.toJSONString()));
		
		// test transfer method
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("senderId", 1);
		jsonObj.put("receiverId", 2);
		jsonObj.put("amount", 100);

		mockMvc.perform(post("/api/transfer").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(jsonObj.toJSONString())).andExpect(status().isOk());
	}
}