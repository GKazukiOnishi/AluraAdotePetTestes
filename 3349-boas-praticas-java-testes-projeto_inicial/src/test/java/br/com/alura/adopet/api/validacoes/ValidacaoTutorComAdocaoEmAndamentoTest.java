package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComAdocaoEmAndamentoTest {

    @InjectMocks
    private ValidacaoTutorComAdocaoEmAndamento validacaoTutorComAdocaoEmAndamento;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Spy
    private Tutor tutor = new Tutor(new CadastroTutorDto("TutorTeste", "(11)11234-1234", "teste@teste.com.br"));

    @Mock
    private Tutor outroTutor;

    @Mock
    private Pet pet;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    void naoDeveriaPermitirSolicitacaoDeAdocao() {
        //ARRANGE
        List<Adocao> adocoesMock = List.of(new Adocao(tutor, pet, "Teste"));
        BDDMockito.given(adocaoRepository.findAll()).willReturn(adocoesMock);
        BDDMockito.given(tutorRepository.getReferenceById(BDDMockito.anyLong())).willReturn(tutor);

        //ACT + ASSERT
        Assertions.assertThrows(ValidacaoException.class, () -> validacaoTutorComAdocaoEmAndamento.validar(dto));
    }

    @Test
    void deveriaPermitirSolicitacaoDeAdocao() {
        //ARRANGE
        List<Adocao> adocoesMock = List.of(new Adocao(tutor, pet, "Teste"));
        BDDMockito.given(adocaoRepository.findAll()).willReturn(adocoesMock);
        BDDMockito.given(tutorRepository.getReferenceById(BDDMockito.anyLong())).willReturn(outroTutor);

        //ACT + ASSERT
        Assertions.assertDoesNotThrow(() -> validacaoTutorComAdocaoEmAndamento.validar(dto));
    }
}