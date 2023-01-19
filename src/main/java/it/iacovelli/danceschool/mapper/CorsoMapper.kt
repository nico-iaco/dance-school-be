package it.iacovelli.danceschool.mapper

import it.iacovelli.danceschool.model.Corso
import it.iacovelli.danceschool.model.Insegnante
import it.iacovelli.danceschool.model.Iscrizione
import it.iacovelli.danceschool.model.dto.CorsoDto
import it.iacovelli.danceschool.model.dto.InsegnanteDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.Named
import org.springframework.beans.factory.annotation.Autowired

@Mapper(componentModel = "spring")
abstract class CorsoMapper {

    @Autowired
    lateinit var insegnanteMapper: InsegnanteMapper


    @Mapping(source = "teacher", target = "teacher", qualifiedByName = ["teacherMapping"])
    @Mapping(source = "subscribers", target = "subscribedStudent", qualifiedByName = ["studentMapping"])
    abstract fun corsoToDto(corso: Corso): CorsoDto

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subscribers", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(source = "teacher", target = "teacher", qualifiedByName = ["teacherMapping"])
    abstract fun dtoToCorso(corsoDto: CorsoDto): Corso

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subscribers", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(source = "teacher", target = "teacher", qualifiedByName = ["teacherMapping"])
    abstract fun updateCorsoFromDto(corsoDto: CorsoDto, @MappingTarget corso: Corso)

    @Named("teacherMapping")
    protected fun mapTeacher(teacher: Insegnante): InsegnanteDto {
        return insegnanteMapper.insegnanteToDto(teacher)
    }

    @Named("teacherMapping")
    protected fun mapTeacher(insegnanteDto: InsegnanteDto): Insegnante {
        return insegnanteMapper.dtoToInsegnante(insegnanteDto)
    }

    @Named("studentMapping")
    protected fun mapStudents(enrolled: List<Iscrizione>): Int {
        return enrolled.size
    }
}