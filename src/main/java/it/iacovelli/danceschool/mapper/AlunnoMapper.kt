package it.iacovelli.danceschool.mapper

import it.iacovelli.danceschool.model.Alunno
import it.iacovelli.danceschool.model.Iscrizione
import it.iacovelli.danceschool.model.dto.AlunnoDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.Named

@Mapper(componentModel = "spring")
abstract class AlunnoMapper {

    @Mapping(source = "enrollment", target = "coursesNumber", qualifiedByName = ["mappingCourses"])
    abstract fun alunnoToDto(alunno: Alunno): AlunnoDto

    @Mapping(target = "active", ignore = true)
    @Mapping(target = "enrollment", ignore = true)
    @Mapping(target = "payments", ignore = true)
    abstract fun dtoToAlunno(alunnoDto: AlunnoDto): Alunno

    @Mapping(target = "active", ignore = true)
    @Mapping(target = "enrollment", ignore = true)
    @Mapping(target = "payments", ignore = true)
    abstract fun updateAlunnoFromDto(alunnoDto: AlunnoDto, @MappingTarget alunno: Alunno)

    @Named("mappingCourses")
    protected fun mapCourses(subscriptions: List<Iscrizione>): Int {
        return subscriptions.size
    }

}