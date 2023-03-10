package it.iacovelli.danceschool.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import java.util.*

/**
 * This is the model of Insegnante
 * @version 1.0
 * @author nicola.iacovelli
 */
@Entity
class Insegnante : Person() {

    @OneToMany(mappedBy = "teacher", targetEntity = Corso::class)
    var courses: List<Corso>? = ArrayList()

    @Column(name = "salary")
    var salary: Double? = 0.0

    override fun toString(): String {
        super.toString()
        return "Insegnante(salary=$salary)"
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }


}
