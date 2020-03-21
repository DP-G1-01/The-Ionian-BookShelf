//package org.springframework.samples.theionianbookshelf.web;
//
//import org.assertj.core.util.Lists;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import the_ionian_bookshelf.model.Branch;
//import the_ionian_bookshelf.model.Rune;
//import the_ionian_bookshelf.service.RuneService;
//import the_ionian_bookshelf.web.RuneController;
//
//import static org.hamcrest.xml.HasXPath.hasXPath;
//import static org.mockito.BDDMockito.given;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.test.context.support.WithMockUser;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import java.util.Set;
//
///**
// * Test class for the {@link VetController}
// */
//@WebMvcTest(RuneController.class)
//class RuneControllerTests {
//
//	@Autowired
//	private RuneController runeController;
//
//	@MockBean
//	private RuneService runeService;
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@BeforeEach
//	void setup() {
//
//		Branch b1 = new Branch();
//		b1.setId(1);
//		b1.setName("Rama1");
//		b1.setDescription("Desc1");
//		b1.setImage("http://www.miimagenderama.com");
//
//		Rune rune = new Rune();
//		rune.setName("NombreRuna");
//		rune.setDescription("Descripcion");
//		rune.setId(1);
//		rune.setBranch(b1);
//		rune.setNode("1");
//
//		Rune rune2 = new Rune();
//		rune2.setName("NombreRuna2");
//		rune2.setDescription("Descripcion2");
//		rune2.setId(2);
//		rune2.setBranch(b1);
//		rune2.setNode("KEY");
//		given(this.runeService.findRunes()).willReturn((Set<Rune>) Lists.newArrayList(rune, rune2));
//	}
//
//	@WithMockUser(value = "spring")
//	@Test
//	void testInitCreationForm() throws Exception {
//		mockMvc.perform(get("/runes/new")).andExpect(status().isOk()).andExpect(model().attributeExists("rune"))
//				.andExpect(view().name("rune/editRune"));
//	}
//
//	// Habria que mirar como se pone una referencia a branch cuando usas el .param()
//	@WithMockUser(value = "spring")
//	@Test
//	void testProcessCreationFormSuccess() throws Exception {
//		mockMvc.perform(post("/runes/new").param("name", "name1").param("description", "desc1").param("branch", "1")
//				.param("node", "1")).andExpect(status().is3xxRedirection());
//	}
//
////En teoria deberia fallar porque branch esta nulo
//	@WithMockUser(value = "spring")
//	@Test
//	void testProcessCreationFormHasErrors() throws Exception {
//		mockMvc.perform(post("/runes/new").param("name", "name1").param("description", "desc1").param("node", "1"))
//				.andExpect(status().isOk()).andExpect(model().attributeHasErrors("rune"))
//				.andExpect(view().name("runes/editRune"));
//	}
//
//	@WithMockUser(value = "spring")
//	@Test
//	void testShowVetListHtml() throws Exception {
//		mockMvc.perform(get("/runes.html")).andExpect(status().isOk()).andExpect(model().attributeExists("runes"))
//				.andExpect(view().name("runes/listadoRunes"));
//	}
//
//}
