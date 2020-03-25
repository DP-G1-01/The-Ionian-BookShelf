//package org.springframework.samples.the_ionian_bookshelf.web;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.FilterType;
//import org.springframework.samples.the_ionian_bookshelf.configuration.SecurityConfiguration;
//import org.springframework.samples.the_ionian_bookshelf.service.ChangeRequestService;
//import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//
//@WebMvcTest(controllers = ChangeRequestController.class,excludeFilters = @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class)
//, excludeAutoConfiguration = SecurityConfiguration.class)
//@AutoConfigureMockMvc
//class ChangeRequestControllerTests {
//
//	@Autowired
//	private MockMvc mockMvc;
//	
//	@MockBean
//	private ChangeRequestService changeRequestService;
//	
//	@WithMockUser(value = "spring")
//	@Test
//	void testShowChangeRequestListHtml() throws Exception {
//		mockMvc.perform(get("/requests")).andExpect(status().isOk()).andExpect(model().attributeExists("requests"))
//				.andExpect(view().name("requests/listadoRequests"));
//	}
//}
