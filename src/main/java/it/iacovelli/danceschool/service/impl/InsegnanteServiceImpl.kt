package it.iacovelli.danceschool.service.impl

import it.iacovelli.danceschool.exception.InsegnanteNotFoundException
import it.iacovelli.danceschool.model.Insegnante
import it.iacovelli.danceschool.repository.InsegnanteRepository
import it.iacovelli.danceschool.service.InsegnanteService
import org.springframework.stereotype.Service

@Service
class InsegnanteServiceImpl(private var insegnanteRepository: InsegnanteRepository) : InsegnanteService {

    override fun addInsegnante(insegnante: Insegnante) {
        insegnanteRepository.save(insegnante)
    }

    override fun editInsegnante(insegnante: Insegnante) {
        insegnanteRepository.save(insegnante)
    }

    @Throws(InsegnanteNotFoundException::class)
    override fun getInsegnanteByFiscalCode(fiscalCode: String): Insegnante {
        return insegnanteRepository.findInsegnanteByFiscalCode(fiscalCode).orElseThrow { InsegnanteNotFoundException("Insegnante con matricola $fiscalCode non trovato") }
    }

    override fun allActiveTeacher(): List<Insegnante> {
        return insegnanteRepository.getAllByActiveTrue()
    }

}
