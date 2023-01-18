package it.iacovelli.danceschool.mapper

import it.iacovelli.danceschool.model.Alunno
import it.iacovelli.danceschool.model.Pagamento
import it.iacovelli.danceschool.model.dto.PagamentoDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named

@Mapper(componentModel = "spring")
abstract class PagamentoMapper {

    @Mapping(source = "student", target = "studentId", qualifiedByName = ["studentMapping"])
    abstract fun pagamentoToDto(pagamento: Pagamento) : PagamentoDto

    abstract fun dtoToPagamento(pagamentoDto: PagamentoDto) : Pagamento

    @Named("studentMapping")
    protected fun mapStudent(alunno: Alunno) : String {
        return alunno.fiscalCode
    }

}