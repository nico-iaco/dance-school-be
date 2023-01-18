package it.iacovelli.danceschool.mapper

import it.iacovelli.danceschool.model.Alunno
import it.iacovelli.danceschool.model.Iscrizione
import it.iacovelli.danceschool.model.dto.AlunnoDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named

@Mapper(componentModel = "spring")
abstract class AlunnoMapper {

    @Mapping(source = "enrollment", target = "coursesNumber", qualifiedByName = ["mappingCourses"])
    abstract fun alunnoToDto(alunno: Alunno) : AlunnoDto

    abstract fun dtoToAlunno(alunnoDto: AlunnoDto) : Alunno

    @Named("mappingCourses")
    protected fun mapCourses(subscriptions : List<Iscrizione>) : Int {
        return subscriptions.size
    }

}