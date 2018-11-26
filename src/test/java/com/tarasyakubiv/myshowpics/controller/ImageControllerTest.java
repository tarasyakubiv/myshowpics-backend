package com.tarasyakubiv.myshowpics.controller;

import java.util.ArrayList;
import java.util.List;

import com.tarasyakubiv.myshowpics.domain.Image;
import com.tarasyakubiv.myshowpics.service.ImageService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


@RunWith(SpringRunner.class)
@WebMvcTest
public class ImageControllerTest {
    @MockBean
	private ImageService imageService;
	
	@Autowired
    private MockMvc mvc;
    
    @Test
	public void getRequestedImages() throws Exception {
		//given
		List<Image> images = new ArrayList<>();
		given(imageService.getAllImages()).willReturn(images);
		
		// when
		MockHttpServletResponse response = mvc.perform(
				get("/images").contentType(MediaType.APPLICATION_JSON))
        .andReturn().getResponse();
	
		// then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}



}