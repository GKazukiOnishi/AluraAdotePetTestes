package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {

    @InjectMocks
    private AbrigoService abrigoService;

    @Mock
    private CadastroAbrigoDto dto;

    @Mock
    private AbrigoRepository abrigoRepository;

    @Captor
    private ArgumentCaptor<Abrigo> abrigoCaptor;

    @Test
    void deveriaDarErroDeDadoJaCadastrado() {
        //ARRANGE
        BDDMockito.given(abrigoRepository.existsByNomeOrTelefoneOrEmail(dto.nome(), dto.telefone(), dto.email())).willReturn(true);

        //ASSERT + ACT
        Assertions.assertThrows(ValidacaoException.class, () -> abrigoService.cadatrar(dto));
    }

    @Test
    void deveriaSalvarAbrigo() {
        //ARRANGE
        BDDMockito.given(abrigoRepository.existsByNomeOrTelefoneOrEmail(dto.nome(), dto.telefone(), dto.email())).willReturn(false);

        //ACT
        abrigoService.cadatrar(dto);

        //ASSERT
        BDDMockito.then(abrigoRepository).should().save(abrigoCaptor.capture());
        Abrigo abrigoSalvo = abrigoCaptor.getValue();
        Assertions.assertEquals(dto.nome(), abrigoSalvo.getNome());
        Assertions.assertEquals(dto.telefone(), abrigoSalvo.getTelefone());
        Assertions.assertEquals(dto.email(), abrigoSalvo.getEmail());
    }

    @Test
    void deveriaNaoEncontrarAbrigo() {
        //ARRANGE
        BDDMockito.given(abrigoRepository.findByNome(BDDMockito.anyString())).willReturn(Optional.empty());

        //ASSERT + ACT
        Assertions.assertThrows(ValidacaoException.class, () -> abrigoService.listarPetsDoAbrigo(BDDMockito.anyString()));
    }
}