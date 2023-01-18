package it.iacovelli.danceschool;

import it.iacovelli.danceschool.exception.AlunnoAlreadyExistsException;
import it.iacovelli.danceschool.exception.AlunnoNotFoundException;
import it.iacovelli.danceschool.exception.CorsoNotFoundException;
import it.iacovelli.danceschool.exception.InsegnanteNotFoundException;
import it.iacovelli.danceschool.model.dto.*;
import it.iacovelli.danceschool.proxy.AlunnoProxy;
import it.iacovelli.danceschool.proxy.CorsoProxy;
import it.iacovelli.danceschool.proxy.InsegnanteProxy;
import it.iacovelli.danceschool.proxy.PagamentoProxy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional @Rollback
public class DanceSchoolApplicationTests {

	@Autowired
	private AlunnoProxy alunnoProxy;

	@Autowired
	private CorsoProxy corsoProxy;

	@Autowired
	private PagamentoProxy pagamentoProxy;

	@Autowired
	private InsegnanteProxy insegnanteProxy;

	private AlunnoDto alunnoTest;

	private AlunnoDto sameAlunnoTest;

	private CorsoDto corsoTest;

	private InsegnanteDto baseInsegnante;

	@Before
	public void setup() {
		alunnoTest = new AlunnoDto();
		alunnoTest.setName("xxx");
		alunnoTest.setSurname("xxx");
		alunnoTest.setFiscalCode("xxxxxxxxxxxxxxxx");
		alunnoTest.setBirthdayPlace("Foggia");
		alunnoTest.setTelephone("dhdbd");
		alunnoTest.setParentFiscalCode("XXXXXXXXXXXXXXXX");

		sameAlunnoTest = new AlunnoDto();
		sameAlunnoTest.setName("dghgd");
		sameAlunnoTest.setSurname("gnnccnb");
		sameAlunnoTest.setFiscalCode("xxxxxxxxxxxxxxxx");
		sameAlunnoTest.setBirthdayPlace("Foggia");
		sameAlunnoTest.setTelephone("ffdfsgbgfn");
		sameAlunnoTest.setParentFiscalCode("XXXXXXXXXXXXXXXX   ");

		corsoTest = new CorsoDto();
		corsoTest.setName("Corsfdbd");
		corsoTest.setDescription("gfnfg");

		baseInsegnante = new InsegnanteDto();
		baseInsegnante.setName("Nome");
		baseInsegnante.setSurname("Cognome");
		baseInsegnante.setFiscalCode("AAAAAAAAAAAAAAAA");
		baseInsegnante.setSalary(2300.0);
		baseInsegnante.setBirthdayPlace("Foggia");
		baseInsegnante.setTelephone("fdhddd");
		corsoTest.setTeacher(baseInsegnante);
	}


	@Test
	public void getStudent() {
		alunnoProxy.addStudent(alunnoTest);
		AlunnoDto student = alunnoProxy.getStudentByFiscalCode(alunnoTest.getFiscalCode());
		Assert.isTrue(alunnoTest.getFiscalCode().equals(student.getFiscalCode()), "Gli studenti devono essere uguali");
	}

	@Test(expected = AlunnoAlreadyExistsException.class)
	public void addSameStudentAndGetError() {
		alunnoProxy.addStudent(alunnoTest);
		alunnoProxy.addStudent(sameAlunnoTest);
	}

	@Test(expected = AlunnoNotFoundException.class)
	public void getStudentNotExist() {
		alunnoProxy.addStudent(alunnoTest);
		alunnoProxy.getStudentByFiscalCode("999999L");
	}

	@Test
	public void editStudent() {
		alunnoProxy.addStudent(alunnoTest);
		alunnoTest.setName("Nicola");
		alunnoProxy.editStudent(alunnoTest);
		AlunnoDto student = alunnoProxy.getStudentByFiscalCode(alunnoTest.getFiscalCode());
		Assert.isTrue(student.getName().equals("Nicola"), "Il nome dello studente deve essere Nicola");
	}

	@Test
	public void getCourse() {
		insegnanteProxy.addInsegnante(baseInsegnante);
		long courseId = corsoProxy.addCourse(corsoTest, baseInsegnante.getFiscalCode());
		corsoTest.setId(courseId);
		CorsoDto corsoById = corsoProxy.getCorsoById(courseId);
		Assert.isTrue(corsoById.getId() == courseId, "I corsi devono essere uguali");
	}

	@Test
	public void editCourse() {
		insegnanteProxy.addInsegnante(baseInsegnante);
		String courseName = "Danza Classica";
		long id = corsoProxy.addCourse(corsoTest, baseInsegnante.getFiscalCode());
		corsoTest.setId(id);
		corsoTest.setName(courseName);
		corsoProxy.editCourse(corsoTest);
		CorsoDto corsoById = corsoProxy.getCorsoById(id);
		Assert.isTrue(corsoById.getName().equals(courseName), "Il nome del corso adesso deve essere danza classica");
	}

	@Test(expected = CorsoNotFoundException.class)
	public void getCourseNotExist() {
		insegnanteProxy.addInsegnante(baseInsegnante);
		corsoProxy.addCourse(corsoTest, baseInsegnante.getFiscalCode());
		corsoProxy.getCorsoById(99999L);
	}

	@Test
	public void subscribeStudent() {
		insegnanteProxy.addInsegnante(baseInsegnante);
		alunnoProxy.addStudent(alunnoTest);
		long courseId = corsoProxy.addCourse(corsoTest, baseInsegnante.getFiscalCode());
		corsoTest.setId(courseId);
		alunnoProxy.subscribeStudent(alunnoTest.getFiscalCode(), courseId);
		List<CorsoDto> coursesOfStudent = alunnoProxy.getCoursesOfStudent(alunnoTest.getFiscalCode());
		List<AlunnoDto> studentsOfCourse = corsoProxy.getStudentsOfCourse(courseId);
		Assert.isTrue(coursesOfStudent.contains(corsoTest), "Lo studente deve contenere il corso con id " + courseId);
		Assert.isTrue(studentsOfCourse.contains(alunnoTest), "Il corso tra gli iscritti deve avere lo studente con matricola " + alunnoTest.getFiscalCode());
	}

	@Test
	public void addPayment() {
		alunnoProxy.addStudent(alunnoTest);
		PagamentoDto pagamento = new PagamentoDto();
		pagamento.setAmount(20.4);
		pagamento.setPaymentDate(LocalDate.now());
		pagamento.setRelatedCourse("xxxxx");
		pagamento.setStudentId(alunnoTest.getFiscalCode());
		long pagamentoId = alunnoProxy.addStudentPayment(pagamento);
		pagamento.setId(pagamentoId);
		List<PagamentoDto> studentPayments = alunnoProxy.getStudentPayments(alunnoTest.getFiscalCode());
		Assert.isTrue(studentPayments.get(0).getId() == pagamentoId, "Lo studente deve avere questo pagamento");
	}

	@Test
	public void getEarnings() {
		alunnoProxy.addStudent(alunnoTest);
		PagamentoDto pagamento = new PagamentoDto();
		pagamento.setAmount(20.4);
		pagamento.setPaymentDate(LocalDate.parse("2015-07-15"));
		pagamento.setRelatedCourse("xxxxx");
		pagamento.setStudentId(alunnoTest.getFiscalCode());
		long pagamentoId = alunnoProxy.addStudentPayment(pagamento);
		pagamento.setId(pagamentoId);
		PagamentoDto pagamento1 = new PagamentoDto();
		pagamento1.setAmount(20.6);
		pagamento1.setPaymentDate(LocalDate.parse("2015-07-01"));
		pagamento1.setRelatedCourse("xxxxx");
		pagamento1.setStudentId(alunnoTest.getFiscalCode());
		long pagamentoId1 = alunnoProxy.addStudentPayment(pagamento1);
		pagamento1.setId(pagamentoId1);
		EarningDto periodEarnings = pagamentoProxy.getPeriodEarnings(LocalDate.parse("2015-06-01"), LocalDate.parse("2015-08-01"));
		EarningDto dailyEarnings = pagamentoProxy.getDailyEarnings(LocalDate.parse("2015-07-01"));
		Assert.isTrue(periodEarnings.getEarnings() == 41, "Gli incassi del periodo devono essere 50€ mentre invece è " + periodEarnings.getEarnings() + "€");
		Assert.isTrue(dailyEarnings.getEarnings() == 20.6, "Gli incassi del giorno devono essere 20.6€ mentre invece è " + dailyEarnings.getEarnings() + "€");
	}

	@Test
	public void getNewSubscriptionOfMonth() {
		insegnanteProxy.addInsegnante(baseInsegnante);
		SubscriptionDto oldSubscriptionForMonth = corsoProxy.getNumberSubscribersForMonth(String.valueOf(LocalDate.now().getMonth().getValue()), String.valueOf(LocalDate.now().getYear()));
		alunnoProxy.addStudent(alunnoTest);
		long courseId = corsoProxy.addCourse(corsoTest, baseInsegnante.getFiscalCode());
		corsoTest.setId(courseId);
		alunnoProxy.subscribeStudent(alunnoTest.getFiscalCode(), courseId);

		SubscriptionDto numberSubscribersForMonth = corsoProxy.getNumberSubscribersForMonth(LocalDate.now().format(DateTimeFormatter.ofPattern("MM")), String.valueOf(LocalDate.now().getYear()));
		Assert.isTrue(numberSubscribersForMonth.getSubscriptions() == (oldSubscriptionForMonth.getSubscriptions()+1), "Le iscrizioni del mese non combaciano");
	}

	@Test
	public void getNewSubscriptionOfYear() {
		insegnanteProxy.addInsegnante(baseInsegnante);
		SubscriptionDto oldSubscriptionForYear = corsoProxy.getNumberSubscribersForYear(String.valueOf(LocalDate.now().getYear()));
		alunnoProxy.addStudent(alunnoTest);
		long courseId = corsoProxy.addCourse(corsoTest, baseInsegnante.getFiscalCode());
		corsoTest.setId(courseId);
		alunnoProxy.subscribeStudent(alunnoTest.getFiscalCode(), courseId);

		SubscriptionDto numberSubscribersForYear = corsoProxy.getNumberSubscribersForYear(String.valueOf(LocalDate.now().getYear()));
		Assert.isTrue(numberSubscribersForYear.getSubscriptions() == (oldSubscriptionForYear.getSubscriptions() + 1), "Le iscrizioni dell'anno non combaciano");
	}

	@Test
	public void addTeacher() {
		insegnanteProxy.addInsegnante(baseInsegnante);
		InsegnanteDto insegnanteById = insegnanteProxy.getInsegnanteByFiscalCode(baseInsegnante.getFiscalCode());
		Assert.isTrue(insegnanteById.getFiscalCode().equals(baseInsegnante.getFiscalCode()), "Le matricole devenon essere uguali");
	}

	@Test(expected = InsegnanteNotFoundException.class)
	public void getTeacherNotPresent() {
		InsegnanteDto insegnanteById = insegnanteProxy.getInsegnanteByFiscalCode("-500");
	}

}
