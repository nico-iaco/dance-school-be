package it.iacovelli.danceschool.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import java.io.Serializable
import java.util.*

/**
 * This is the model of Alunno.
 * @version 1.1
 * @author nicola.iacovelli
 */
@Entity
class Alunno : Person(), Serializable {

    /**
     * This field represents the parent fiscal code, its max length is 16 character and cannot be null
     */
    @Column(name = "parent_fiscal_code", length = 16, nullable = false)
    var parentFiscalCode: String? = null

    /**
     * This field represents the city where the student lives, its max length is 100 character
     */
    @Column(name = "city", length = 100)
    var city: String? = null

    /**
     * This field represents the address where the student lives, its max length is 100 character
     */
    @Column(name = "address", length = 100)
    var address: String? = null

    /**
     * This field represents the cap of the `city` field, its max length is 5 character
     */
    @Column(name = "cap", length = 5)
    var cap: String? = null

    /**
     * This list contains the courses in which the student is enrolled
     */
    @OneToMany(mappedBy = "student", targetEntity = Iscrizione::class)
    var enrollment: List<Iscrizione> = ArrayList()

    /**
     * This list contains the payments of the student
     */
    @OneToMany(mappedBy = "student", targetEntity = Pagamento::class)
    var payments: List<Pagamento> = ArrayList()

    override fun toString(): String {
        super.toString()
        return "Alunno(parentFiscalCode=$parentFiscalCode, city=$city, address=$address, cap=$cap)"
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }


}
