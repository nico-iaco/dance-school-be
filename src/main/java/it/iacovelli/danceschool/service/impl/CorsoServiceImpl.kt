package it.iacovelli.danceschool.service.impl

import it.iacovelli.danceschool.exception.CorsoNotFoundException
import it.iacovelli.danceschool.model.Alunno
import it.iacovelli.danceschool.model.Corso
import it.iacovelli.danceschool.model.Iscrizione
import it.iacovelli.danceschool.model.dto.SubscriptionDto
import it.iacovelli.danceschool.repository.CorsoRepository
import it.iacovelli.danceschool.repository.IscrizioneRepository
import it.iacovelli.danceschool.service.CorsoService
import it.iacovelli.danceschool.service.InsegnanteService
import jakarta.transaction.Transactional
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Service

/**
 * This is the service class which exposes the course services
 * @version 1.1
 * @author nicola.iacovelli
 */
@Service
open class CorsoServiceImpl(
    /**
     * This is the repository to access to [Corso] entity
     */
    private var corsoRepository: CorsoRepository,
    /**
     * This is the repository to access to [Iscrizione] entity
     */
    private var iscrizioneRepository: IscrizioneRepository,
    private var insegnanteService: InsegnanteService
) : CorsoService {

    /**
     * This method will add a course to the database
     * @param corso represent the course to add
     * @return the ID of the course added into database
     */
    @CacheEvict(cacheNames = ["corsi"])
    override fun addCourse(corso: Corso, fiscalCode: String): Long {
        val teacher = insegnanteService.getInsegnanteByFiscalCode(fiscalCode)
        corso.teacher = teacher
        corso.active = true
        val save = corsoRepository.save(corso)
        return save.id
    }

    /**
     * This method will edit an existing course
     * @param corso the object with new information about the course
     */
    @CacheEvict(cacheNames = ["corsi"])
    override fun editCorso(corso: Corso) {
        corsoRepository.save(corso)
    }

    /**
     * This method is used to get a [Corso] by course Id
     * @param id represent the course's ID
     * @return the course Dto of the course identified by that ID
     * @throws CorsoNotFoundException if the course was not found
     */
    @Throws(CorsoNotFoundException::class)
    override fun getCorsoById(id: Long): Corso {
        return corsoRepository.getCorsoById(id).orElseThrow { CorsoNotFoundException("Il corso non è presente nel DB") }
    }

    /**
     * This method is used to get all active courses
     * @return list of course Dto which represent all active courses at the moment
     */
    override fun allActiveCourse(): List<Corso> {
        return corsoRepository.getAllByActiveTrue()
    }

    /**
     * This method is used to set the active flag of the course
     * @param state represent the next state of active flag
     * @param courseId represent the course's ID
     */
    @Throws(CorsoNotFoundException::class)
    override fun setCorsoActivated(state: Boolean, courseId: Long) {
        val corso = getCorsoById(courseId)
        corso.active = state
        corsoRepository.save(corso)
    }

    /**
     * This method is used to get all students of a course
     * @param id represent the course's ID
     * @return list of student Dto which represent all the student enrolled in this course
     * @throws CorsoNotFoundException if the course was not found
     */
    @Transactional
    @Throws(CorsoNotFoundException::class)
    override fun getStudentsOfCourse(id: Long): List<Alunno> {
        val corso = getCorsoById(id)
        val subscriptions = iscrizioneRepository.findAllByCourse(corso)
        return subscriptions.stream().map<Alunno>{ i -> i.student}.toList()
    }

    /**
     * This method is used to get the amount of new subscription there were for that month
     * @param month represent which month you want to now
     * @param year represent which year you want to now
     * @return the [SubscriptionDto] which represent how many new subscribers there were for that month
     */
    override fun getNumberSubscribersForMonth(month: String, year: String): SubscriptionDto {
        return iscrizioneRepository.getNumberSubscriptionForMonth(month, year)
    }

    /**
     * This method is used to get the amount of new subscription there were for that year
     * @param year represent which year you want to now
     * @return the [SubscriptionDto] which represent how many new subscribers there were for that year
     */
    override fun getNumberSubscribersForYear(year: String): SubscriptionDto {
        return iscrizioneRepository.getNumberSubscriptionForYear(year)
    }

}
