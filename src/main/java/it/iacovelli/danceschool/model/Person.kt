package it.iacovelli.danceschool.model

import it.iacovelli.danceschool.model.type.GenderType
import java.time.LocalDate
import javax.persistence.*

/**
 * This is the base model of Alunno & Insegnante.
 * @version 1.0
 * @author nicola.iacovelli
 */
@MappedSuperclass
abstract class Person {

    /**
     * This field represents the student/teacher fiscal code and is unique, its max length is 16 character and cannot be null
     */
    @Id
    @Column(name = "fiscalCode", length = 16)
    lateinit var fiscalCode: String

    /**
     * This flag represents if the student/teacher is actually enrolled in some course
     */
    @Column(name = "active")
    var active: Boolean = false

    /**
     * This field represents the student/teacher name, its max length is 50 character and cannot be null
     */
    @Column(name = "name", length = 50, nullable = false)
    var name: String? = null

    /**
     * This field represents the student/teacher surname, its max length is 50 character and cannot be null
     */
    @Column(name = "surname", length = 50, nullable = false)
    var surname: String? = null

    /**
     * This field represents where the student/teacher was born, its max length is 100 character and cannot be null
     */
    @Column(name = "birthdayPlace", length = 100, nullable = false)
    var birthdayPlace: String? = null

    /**
     * This field represents the gender of the student/teacher
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 10)
    var gender: GenderType? = null

    /**
     * This field represents the birthday of the student/teacher
     */
    @Column(name = "birthday", columnDefinition = "DATE")
    var birthday: LocalDate? = null

    /**
     * This field represents the telephone number of the student/teacher, its max length is 10 character and cannot be null
     */
    @Column(name = "telephone", length = 10, nullable = false)
    var telephone: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Person) return false

        if (fiscalCode != other.fiscalCode) return false

        return true
    }

    override fun hashCode(): Int {
        return fiscalCode.hashCode()
    }

    override fun toString(): String {
        return "Person(fiscalCode=$fiscalCode, active=$active, name=$name, surname=$surname, birthdayPlace=$birthdayPlace, gender=$gender, birthday=$birthday, telephone=$telephone)"
    }

}
