package it.iacovelli.danceschool.service.impl

import it.iacovelli.danceschool.exception.InsegnanteNotFoundException
import it.iacovelli.danceschool.model.Insegnante
import it.iacovelli.danceschool.repository.InsegnanteRepository
import it.iacovelli.danceschool.service.InsegnanteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class InsegnanteServiceImpl : InsegnanteService {

    @Autowired
    private lateinit var insegnanteRepository: InsegnanteRepository

    override fun addInsegnante(insegnante: Insegnante) {
        insegnanteRepository.save(insegnante)
    }

    @Throws(InsegnanteNotFoundException::class)
    override fun editInsegnante(insegnante: Insegnante) {
        val dbTeacher = getInsegnanteByFiscalCode(insegnante.fiscalCode)
        insegnanteUpdater(dbTeacher, insegnante)
        insegnanteRepository.save(dbTeacher)
    }

    @Throws(InsegnanteNotFoundException::class)
    override fun getInsegnanteByFiscalCode(fiscalCode: String): Insegnante {
        return insegnanteRepository.findInsegnanteByFiscalCode(fiscalCode).orElseThrow { InsegnanteNotFoundException("Insegnante con matricola $fiscalCode non trovato") }
    }

    override fun allActiveTeacher(): List<Insegnante> {
        return insegnanteRepository.getAllByActiveTrue()
    }

    private fun insegnanteUpdater(oldTeacher : Insegnante, newTeacher : Insegnante) {
        oldTeacher.salary = newTeacher.salary
        oldTeacher.telephone = newTeacher.telephone
    }

}
