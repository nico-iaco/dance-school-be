package it.iacovelli.danceschool.mapper

import it.iacovelli.danceschool.model.Corso
import it.iacovelli.danceschool.model.Insegnante
import it.iacovelli.danceschool.model.dto.InsegnanteDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.Named

@Mapper(componentModel = "spring")
abstract class InsegnanteMapper {

    @Mapping(source = "courses", target = "taughtCourses", qualifiedByName = ["mappingCourses"])
    abstract fun insegnanteToDto(insegnante: Insegnante): InsegnanteDto

    @Mapping(target = "courses", ignore = true)
    abstract fun dtoToInsegnante(insegnanteDto: InsegnanteDto): Insegnante

    @Mapping(target = "courses", ignore = true)
    abstract fun updateInsegnanteFromDto(insegnanteDto: InsegnanteDto, @MappingTarget insegnante: Insegnante)

    @Named("mappingCourses")
    protected fun mappingCourses(courses: List<Corso>): Int {
        return courses.size
    }

}