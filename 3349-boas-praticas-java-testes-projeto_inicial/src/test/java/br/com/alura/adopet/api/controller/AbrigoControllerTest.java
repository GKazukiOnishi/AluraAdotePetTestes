package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.service.AbrigoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AbrigoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AbrigoService abrigoService;

    @Autowired
    private JacksonTester<CadastroAbrigoDto> jsonDto;

    @Test
    void deveriaCadastrarNovoAbrigo() throws Exception {
        //ARRANGE
        CadastroAbrigoDto dto = new CadastroAbrigoDto("Abrigo Teste", "(11)11234-1234", "teste@teste.com");

        //ACT
        MockHttpServletResponse response = mvc.perform(
                post("/abrigos")
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void naoDeveriaCadastrarAbrigoComCamposInvalidos() throws Exception {
        //ARRANGE
        CadastroAbrigoDto dto = new CadastroAbrigoDto("Abrigo Teste", "(11)34-1234", "teste@teste.com");

        //ACT
        MockHttpServletResponse response = mvc.perform(
                post("/abrigos")
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(400, response.getStatus());
    }
}