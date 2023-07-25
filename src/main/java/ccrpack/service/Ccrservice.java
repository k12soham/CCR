package ccrpack.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.imageio.ImageIO;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ccrpack.entity.Answer;
import ccrpack.entity.Candidate;
import ccrpack.entity.CcrAdmin;
import ccrpack.entity.Company;
import ccrpack.entity.Hr;
import ccrpack.entity.OcrResult;
import ccrpack.entity.Question;
import ccrpack.entity.RatingForm;
import ccrpack.repo.AnswerRepo;
import ccrpack.repo.CandidateRepo;
import ccrpack.repo.CcrRepo;
import ccrpack.repo.CompanyRepo;
import ccrpack.repo.HrRepo;
import ccrpack.repo.OcrResultRepository;
import ccrpack.repo.QuestionRepo;
import ccrpack.repo.RatingRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

@Service

public class Ccrservice {

	@Autowired
	CompanyRepo companyRepo;

	@Autowired
	HrRepo hrRepo;

	@Autowired
	RatingRepo ratingRepo;

	@Autowired
	CandidateRepo candidateRepo;

	@Autowired
	CcrRepo ccrRepo;

	@Autowired
	QuestionRepo questionRepo;

	@Autowired
	AnswerRepo answerRepo;

	@Autowired
	OcrResultRepository ocrRepo;

	private ITesseract tesseract;

	public Ccrservice() {
		tesseract = new Tesseract();
		tesseract.setDatapath("src/main/resources/eng.traineddata");
	}

	Hr hr = new Hr();
	Company company = new Company();
	RatingForm ratingForm = new RatingForm();
	Candidate candidate = new Candidate();
	CcrAdmin ccrAdmin = new CcrAdmin();
	OcrResult ocrResult = new OcrResult();

	@Autowired
	private JavaMailSender javaMailSender;

	@PersistenceContext
	EntityManager entityManager;

	public ResponseEntity<?> ccrLogin(CcrAdmin ccrAdmin) {
		Session session = entityManager.unwrap(Session.class);
		try {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<CcrAdmin> cr = cb.createQuery(CcrAdmin.class);

			Root<CcrAdmin> root = cr.from(CcrAdmin.class);
			cr.select(root).where(cb.equal(root.get("ccr_email"), ccrAdmin.getCcr_email()),
					cb.equal(root.get("ccr_password"), ccrAdmin.getCcr_password()));

			TypedQuery<CcrAdmin> query = session.createQuery(cr);

			ccrAdmin = query.getSingleResult();

			if (ccrAdmin != null) {
				session.close();
				return new ResponseEntity<>(ccrAdmin, HttpStatus.OK);

			}
		} catch (Exception e) {
			session.close();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wrong credentials");
		}
		return null;
	}

	public ResponseEntity<?> candlogin(Candidate candidate) {
		Session session = entityManager.unwrap(Session.class);
		try {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Candidate> cr = cb.createQuery(Candidate.class);

			Root<Candidate> root = cr.from(Candidate.class);
			cr.select(root).where(cb.equal(root.get("candidate_email"), candidate.getCandidate_email()),
					cb.equal(root.get("candidate_password"), candidate.getCandidate_password()));

			TypedQuery<Candidate> query = session.createQuery(cr);

			candidate = query.getSingleResult();

			if (candidate != null) {
				session.close();
				return new ResponseEntity<>(candidate, HttpStatus.OK);

			}
		} catch (Exception e) {
			session.close();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wrong credentials");
		}
		return null;
	}

	public ResponseEntity<String> registerCandidate(Candidate candidate) {
		Session session = entityManager.unwrap(Session.class);

		candidate.setCandidate_name(candidate.getCandidate_name());
		candidate.setCandidate_aadhar(candidate.getCandidate_aadhar());
		candidate.setCandidate_password(candidate.getCandidate_password());
		candidate.setCandidate_dob(candidate.getCandidate_dob());
		candidate.setCandidate_email(candidate.getCandidate_email());
		candidate.setCandidate_phone(candidate.getCandidate_phone());

		session.save(candidate);
		session.close();
		return ResponseEntity.status(HttpStatus.CREATED).body("Candidate Registered sucessfully");
	}

	public ResponseEntity<?> hrlogin(Hr hr) {

		Session session = entityManager.unwrap(Session.class);
		try {

			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Hr> cr = cb.createQuery(Hr.class);
			Root<Hr> root = cr.from(Hr.class);
			cr.select(root).where(cb.equal(root.get("hr_email"), hr.getHr_email()),
					cb.equal(root.get("hr_password"), hr.getHr_password()));
			TypedQuery<Hr> query = session.createQuery(cr);

			hr = query.getSingleResult();

			if (hr != null) {

				session.close();
				 
				return new ResponseEntity<>(hr, HttpStatus.OK);
			}
		} catch (Exception e) {
			session.close();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wrong credentials");
		}
		return null;
	}

	public ResponseEntity<?> companyReg(Company company) {
		Session session = entityManager.unwrap(Session.class);
		try {
			hr.setHr_name(company.getHr().getHr_name());
			hr.setHr_phone(company.getHr().getHr_phone());
			hr.setHr_email(company.getHr().getHr_email());
			hr.setHr_password(company.getHr().getHr_password());
			hr.setHr_role("Admin");
			hr.setRatingform(company.getHr().getRatingform());

			company.setCompany_name(company.getCompany_name());
			company.setCompany_address(company.getCompany_address());
			company.setCompany_phone(company.getCompany_phone());
			company.setCompany_tan(company.getCompany_tan());

			company.setHr(hr);
			session.save(hr);
			session.save(company);
			session.close();
			return ResponseEntity.status(HttpStatus.CREATED).body("comapny registered");
		} catch (Exception e) {
			session.close();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something wrong");
		}

	}

	/*
	 * public ResponseEntity<?> AdminAddrecruiter(Hr hr) { Session session =
	 * entityManager.unwrap(Session.class);
	 * 
	 * hr.setHr_name(hr.getHr_name()); hr.setHr_email(hr.getHr_email());
	 * hr.setHr_password("1234"); hr.setAdded_by(hr.getHr_id());
	 * 
	 * 
	 * 
	 * session.save(hr);
	 * 
	 * if (==true && ==true) { int a = hr.getHr_id(); hr.setApprover(a);
	 * 
	 * hr.setHr_role("Admin"); } else if (hr.getApprover().equals(false) &&
	 * hr.getAdded_by().equals(true)) { hr.setApprover(hr.getHr_id());
	 * hr.setHr_role("TeamLead"); } else { hr.setApprover(hr.getHr_id());
	 * hr.setHr_role("Rec"); }
	 * 
	 * hrRepo.save(hr);
	 * 
	 * session.close(); return
	 * ResponseEntity.status(HttpStatus.CREATED).body("Admin saved"); }
	 */

	public ResponseEntity<String> AdminAddrecruiter(Integer hrid, String hr_name, String hr_email, boolean approver,
			boolean add_team) {

		Session session = entityManager.unwrap(Session.class);
		try {
			hr.setHr_name(hr_name);
			hr.setHr_email(hr_email);
			hr.setAdded_by(hrid);
			hr.setHr_password("1234");
			session.save(hr);
			if (approver == true && add_team == true) {
				int a = hr.getHr_id();
				hr.setApprover(a);

				hr.setHr_role("Admin");
			} else if (approver == false && add_team == true) {
				hr.setApprover(hrid);
				hr.setHr_role("TeamLead");
			} else {
				hr.setApprover(hrid);
				hr.setHr_role("Rec");
			}

			hrRepo.save(hr);

			session.close();
			return ResponseEntity.status(HttpStatus.CREATED).body("Admin saved");
		} catch (Exception e) {
			session.close();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something wrong");
		}
	}

	public ResponseEntity<String> TLAddrecruiter(Integer hrid, String hr_name, boolean approver, boolean add_team) {
		Session session = entityManager.unwrap(Session.class);
		try {
			hr.setHr_name(hr_name);
			hr.setAdded_by(hrid);
			session.save(hr);

			CriteriaBuilder cb = session.getCriteriaBuilder();

			CriteriaQuery<Hr> cr = cb.createQuery(Hr.class);
			Root<Hr> root = cr.from(Hr.class);
			cr.select(root).where((cb.equal(root.get("hr_admin_id"), hrid)));
			Query query = session.createQuery(cr);
			Hr z = (Hr) query.getSingleResult();
			int b = z.getApprover();
			System.out.println(b);

			int a = 5;

			if (approver == false && add_team == true) {
				hr.setApprover(b);
				hr.setHr_role("TeamLead");
			} else {
				hr.setApprover(b);
				hr.setHr_role("Rec");
			}

			hrRepo.save(hr);

			session.close();
			return ResponseEntity.status(HttpStatus.CREATED).body("TL saved");
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("something wrong");
		}

	}

	public ResponseEntity<String> submitAnswers(int candidate_id, List<Answer> answers) throws Exception {

		try {
			Candidate candidate = candidateRepo.findById(candidate_id)
					.orElseThrow(() -> new Exception("User not found with id: " + candidate_id));

			for (Answer answer : answers) {
				int questionId = answer.getQuestion().getQuestion_id();
				Question question = questionRepo.findById(questionId)
						.orElseThrow(() -> new Exception("Question not found with id: " + questionId));

				answer.setQuestion(question);
				answer.setCandidate(candidate);
			}
			answerRepo.saveAll(answers);
			return ResponseEntity.ok("Rating added successfully");

		}

		catch (Exception e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something wrong");
		}
	}

	public ResponseEntity<?> getCandidateAverageScore(Candidate candidate) {

		try {
			candidate = candidateRepo.findById(candidate.getCandidate_id())
					.orElseThrow(() -> new Exception("Candidate not found with id: "));

			List<Answer> candidateAnswers = answerRepo.findByCandidate(candidate);

			int totalScore = candidateAnswers.stream().filter(Answer::isAnswer)
					.mapToInt(answer -> answer.getQuestion().getWeightage()).sum();
			int totalRecords = candidateAnswers.size();
			int totalQuestions = questionRepo.findAll().size();
			System.out.println(totalScore);
			double averageScore = totalRecords == 0 ? 0.0 : (double) totalScore / totalRecords * totalQuestions;
			return ResponseEntity.ok(averageScore);
		}

		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something wrong");

		}

	}

	public ResponseEntity<String> ChangeApprover(Integer hrid, String hr_email) {
		Session session = entityManager.unwrap(Session.class);

		CriteriaBuilder cb = session.getCriteriaBuilder();

		CriteriaQuery<Hr> cr = cb.createQuery(Hr.class);
		Root<Hr> root = cr.from(Hr.class);
		cr.select(root).where((cb.equal(root.get("hr_email"), hr_email)));
		Query query = session.createQuery(cr);
		Hr z = (Hr) query.getSingleResult();
		System.out.println(z);
		if (z == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");

		} else {

			z.setApprover(hrid);
			hrRepo.save(z);
			return ResponseEntity.status(HttpStatus.CREATED).body("Approver changed");

		}

	}

	// OTP Forgot password
	public ResponseEntity<String> sendOtpByEmail(Candidate candidate) {

		Session session = entityManager.unwrap(Session.class);

		try {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Candidate> cr = cb.createQuery(Candidate.class);

			Root<Candidate> root = cr.from(Candidate.class);
			cr.select(root).where(cb.equal(root.get("candidate_email"), candidate.getCandidate_email()));

			Query query = session.createQuery(cr);

			Candidate retrievedCandidate = (Candidate) query.getSingleResult();

			if (retrievedCandidate != null) {
				int otp = generateOtp();
				// int storingotp=candidate.getCandidate_otp();
				// System.out.println(storingotp);
				retrievedCandidate.setCandidate_otp(otp);

				candidateRepo.save(retrievedCandidate);
				String i = retrievedCandidate.getCandidate_email();
				System.out.println(i);
				sendOtpEmail(i, otp);
				session.close();

				return ResponseEntity.ok("OTP sent successfully");

			}

		} catch (Exception e) {

			session.close();
			return ResponseEntity.badRequest().body("Enter Correct Email");
		}

		return null;

	}

	private int generateOtp() {
		int min = 10000;
		int max = 99999;
		int token = (int) (Math.random() * (max - min + 1) + min);

		return token;
	}

	private void sendOtpEmail(String email, int otp) throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom("yashporlekar8888@gmail.com", "CCR");
		helper.setTo(email);
		String subject = "Here's the link to reset your password";
		String content = "<p>Hello ,</p>" + "<p>You have requested to reset your password.</p>"
				+ "<p>Here is your OTP: " + otp + "<br>" + "<p>Ignore this email if you do remember your password, "
				+ "or you have not made the request.</p>";
		message.setSubject(subject);
		helper.setText(content, true);
		javaMailSender.send(message);
	}

	public ResponseEntity<String> candchangepassforgot(Candidate candidate) {
		Session session = entityManager.unwrap(Session.class);
		try {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Candidate> cr = cb.createQuery(Candidate.class);
			Root<Candidate> root = cr.from(Candidate.class);
			cr.select(root).where(cb.equal(root.get("candidate_otp"), candidate.getCandidate_otp()));
			Query query = session.createQuery(cr);

			Candidate result = (Candidate) query.getSingleResult();
			// Perform password change logic here using the 'result' object

			session.close();
			return ResponseEntity.status(HttpStatus.OK).body("You Have Entered Correct OTP....");
		} catch (Exception e) {
			session.close();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please Enter Correct OTP....");
		}

	}

	public ResponseEntity<String> finalcandchangepass(Candidate candidate) {

		Session session = entityManager.unwrap(Session.class);
		try {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Candidate> cr = cb.createQuery(Candidate.class);

			Root<Candidate> root = cr.from(Candidate.class);
			cr.select(root).where(cb.equal(root.get("candidate_email"), candidate.getCandidate_email()));

			Query query = session.createQuery(cr);

			Candidate retrievedCandidate = (Candidate) query.getSingleResult();

			if (retrievedCandidate != null) {
				retrievedCandidate.setCandidate_password(candidate.getCandidate_password());
				candidateRepo.save(retrievedCandidate);

				session.close();

				return ResponseEntity.status(HttpStatus.CREATED).body("Password Changed Sucessfully");
			}

		} catch (Exception e) {
			session.close();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something wrong");
		}
		return null;

	}

	public ResponseEntity<String> candchangepass(int candidate_id, String currentpass, String newpass) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Candidate> cr = cb.createQuery(Candidate.class);
		Root<Candidate> root = cr.from(Candidate.class);
		cr.select(root).where(cb.equal(root.get("candidate_id"), candidate_id),
				cb.equal(root.get("candidate_password"), currentpass));
		Query query = session.createQuery(cr);
		Candidate results = null;
		try {
			results = (Candidate) query.getSingleResult();
			candidate = candidateRepo.getById(candidate_id);
			candidate.setCandidate_password(newpass);
			candidateRepo.save(candidate);
			session.close();
			return ResponseEntity.status(HttpStatus.CREATED).body("Password Changed Sucessfully");
		} catch (Exception e) {

			session.close();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Current password doesnt matched");
		}
	}

	public ResponseEntity<?> ccrlogin(CcrAdmin ccradmin) {
		Session session = entityManager.unwrap(Session.class);
		try {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<CcrAdmin> cr = cb.createQuery(CcrAdmin.class);
			Root<CcrAdmin> root = cr.from(CcrAdmin.class);
			cr.select(root).where(cb.equal(root.get("ccr_email"), ccradmin.getCcr_email()),
					cb.equal(root.get("ccr_password"), ccradmin.getCcr_password()));
			TypedQuery<CcrAdmin> query = session.createQuery(cr);

			ccrAdmin = query.getSingleResult();

			if (ccrAdmin != null) {
				session.close();

				return new ResponseEntity<>(ccrAdmin, HttpStatus.OK);
			}
		} catch (Exception e) {
			session.close();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wrong credentials");
		}
		return null;
	}

	public ResponseEntity<String> addccradmin(CcrAdmin ccradmin) {

		Session session = entityManager.unwrap(Session.class);

		ccradmin.setCcr_name(ccradmin.getCcr_name());
		ccradmin.setCcr_email(ccradmin.getCcr_email());
		ccradmin.setCcr_password("Welcome@123");
		ccradmin.setCcr_phone(ccradmin.getCcr_phone());
		ccradmin.setCcr_role("ccr");
		session.save(ccradmin);
		return ResponseEntity.status(HttpStatus.CREATED).body("New CCR Admin Added Sucessfully....");
	}

	public ResponseEntity<String> saveYesNoAns(RatingForm ratingForm) {

		boolean[] answers = { ratingForm.isQ1(), ratingForm.isQ2(), ratingForm.isQ3(), ratingForm.isQ4(),
				ratingForm.isQ5(), ratingForm.isQ6(), ratingForm.isQ7(), ratingForm.isQ8(), ratingForm.isQ9(),
				ratingForm.isQ10(), };
//		String[] categories = { ratingForm.getCategory1(), ratingForm.getCategory2(), ratingForm.getCategory3(),
//				ratingForm.getCategory4(), ratingForm.getCategory5() };

		int[] weightages = { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 };

		int totalScore = 0;
//		int category1Score = 0;
//		int category2Score = 0;
//		int category3Score = 0;
//		int category4Score = 0;
//		int category5Score = 0;

		for (int i = 0; i < answers.length; i++) {
			if (answers[i]) {
				totalScore += weightages[i];
			}
		}

//		for (int i = 0; i < answers.length; i++) {
//			if (answers[i]) {
//				switch (i / 2) {
//				case 0:
//					category1Score += weightages[i];
//					break;
//				case 1:
//					category2Score += weightages[i];
//					break;
//				case 2:
//					category3Score += weightages[i];
//					break;
//				case 3:
//					category4Score += weightages[i];
//					break;
//				case 4:
//					category5Score += weightages[i];
//					break;
//				}
//			}
//		}

//		totalScore = category1Score + category2Score + category3Score + category4Score + category5Score;
//		ratingForm.setCategory1Score(category1Score);
//		ratingForm.setCategory2Score(category2Score);
//		ratingForm.setCategory3Score(category3Score);
//		ratingForm.setCategory4Score(category4Score);
//		ratingForm.setCategory5Score(category5Score);

		ratingForm.setRating_total(totalScore);

		ratingRepo.save(ratingForm);
		return ResponseEntity.ok("Answers for 10 questions saved");
	}

	public ResponseEntity<String> Rating(Boolean q1, Boolean q2, int total, int candidate_id, int total2, int rec_id) {
		Session session = entityManager.unwrap(Session.class);

		ratingForm.setQ1(q1);
		ratingForm.setQ2(q2);
		ratingForm.setRating_total(total);
		candidate.setCandidate_id(candidate_id);
		hr.setHr_id(rec_id);
		ratingForm.setCandidate(candidate);
		ratingForm.setHr(hr);

		hr = hrRepo.getById(rec_id);
		int a = hr.getApprover();
		hr = hrRepo.getById(a);
		int b = hr.getHr_id();

		ratingForm.setNew_request(true);
		ratingForm.setApprover_id(b);

		session.save(ratingForm);
		hrRepo.save(hr);
		session.close();
		return ResponseEntity.status(HttpStatus.CREATED).body("Rating added");

	}

	public List<RatingForm> Getrequest(int rec_id) {
		Session session = entityManager.unwrap(Session.class);

		CriteriaBuilder cb = session.getCriteriaBuilder();

		CriteriaQuery<RatingForm> cr = cb.createQuery(RatingForm.class);
		Root<RatingForm> root = cr.from(RatingForm.class);

		cr.select(root).where((cb.equal(root.get("new_request"), true)), ((cb.equal(root.get("approver_id"), rec_id))));

		Query query = session.createQuery(cr);
		List<RatingForm> results = query.getResultList();

		session.close();
		return results;

	}

	public ResponseEntity<String> changePassword(int candidate_id, String currentpass, String newpass) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Candidate> cr = cb.createQuery(Candidate.class);
		Root<Candidate> root = cr.from(Candidate.class);

		cr.select(root).where(cb.equal(root.get("candidate_id"), candidate_id),
				cb.equal(root.get("candidate_password"), currentpass));
		Query query = session.createQuery(cr);
		Candidate results = null;
		try {
			results = (Candidate) query.getSingleResult();
			candidate = candidateRepo.getById(candidate_id);
			candidate.setCandidate_password(newpass);
			candidateRepo.save(candidate);
			session.close();

			return ResponseEntity.status(HttpStatus.CREATED).body("Password Changed Sucessfully");
		} catch (NoResultException e) {
			session.close();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Current password doesnt matched");
		}

	}

	
	public String extractTextFromImage(byte[] imageBytes) {
		try {
			return tesseract.doOCR(ImageIO.read(new ByteArrayInputStream(imageBytes)));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResponseEntity<String> uploadFile(MultipartFile file) {
		try {
			OcrResult ocrResult1 = new OcrResult();
			ocrResult1.setName(file.getOriginalFilename());
			ocrResult1.setImageData(file.getBytes());
			ocrRepo.save(ocrResult1);

			return ResponseEntity.ok("Image uploaded successfully.");
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
		}
	}

	public ResponseEntity<byte[]> getFile(Long id) {
		ocrResult = ocrRepo.findById(id).orElse(null);
		if (ocrResult != null) {
			HttpHeaders headers = new HttpHeaders();

			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", ocrResult.getName());

			return new ResponseEntity<>(ocrResult.getImageData(), headers, HttpStatus.OK);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}