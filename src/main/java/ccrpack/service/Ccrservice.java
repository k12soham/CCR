package ccrpack.service;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ccrpack.entity.Candidate;
import ccrpack.entity.Company;
import ccrpack.entity.Hr;
import ccrpack.entity.RatingForm;
import ccrpack.repo.CandidateRepo;
import ccrpack.repo.CompanyRepo;
import ccrpack.repo.HrRepo;
import ccrpack.repo.RatingRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

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

	Hr hr = new Hr();
	Company company = new Company();
	RatingForm ratingForm = new RatingForm();
	Candidate candidate = new Candidate();

	@PersistenceContext
	EntityManager entityManager;

//	public ResponseEntity<String> companyreg(String cname, Long tan, String hr_name, Long phone, String role) {
//		Session session = entityManager.unwrap(Session.class);
//
//		company.setCompany_name(cname);
//		company.setCompany_tan(tan);
//		hr.setHr_name(hr_name);
//		hr.setHr_phone(phone);
//		hr.setHr_role(role);
//		hr.setCompany(company);
//
//		session.save(company);
//		session.save(hr);
//		return ResponseEntity.status(HttpStatus.CREATED).body("Candidate registered");
//	}

	

	public ResponseEntity<String> companyReg(Company company) {
		Session session = entityManager.unwrap(Session.class);
		hr.setHr_name(company.getHr().getHr_name());
		company.setCompany_name(company.getCompany_name());
		company.setHr(hr);
		session.save(hr);
		session.save(company);
		return ResponseEntity.status(HttpStatus.CREATED).body("comapny registered");
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

	public ResponseEntity<String> AdminAddrecruiter(Integer hrid, String hr_name, boolean approver, boolean add_team) {

		Session session = entityManager.unwrap(Session.class);

		hr.setHr_name(hr_name);
		hr.setAdded_by(hrid);
		session.save(hr);
		if (approver == true && add_team == true) {
			int a = hr.getHr_id();
			System.out.println(a);
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
	}

	public ResponseEntity<String> TLAddrecruiter(Integer hrid, String hr_name, boolean approver, boolean add_team) {
		Session session = entityManager.unwrap(Session.class);

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


	public ResponseEntity<String> changePassword(int candidate_id, String currentpass,String newpass) {
		
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

	public ResponseEntity<String> candlogin(Long candidate_aadhar, String candidate_password) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Candidate> cr = cb.createQuery(Candidate.class);
		Root<Candidate> root = cr.from(Candidate.class);
		cr.select(root).where(cb.equal(root.get("candidate_aadhar"), candidate_aadhar),
				cb.equal(root.get("candidate_password"), candidate_password));
		TypedQuery<Candidate> query = session.createQuery(cr);
		Candidate candidate = ((org.hibernate.query.Query<Candidate>) query).uniqueResult();
		if (candidate != null) {
			session.save(candidate);
			session.close();
			return ResponseEntity.status(HttpStatus.OK).body("Candidate Login Sucessfully....");
		} else {
			session.close();
			return ResponseEntity.status(HttpStatus.OK).body("Candidate Not Found....");
		}
		}

	public ResponseEntity<String> candregi(Long candidate_aadhar, String candidate_password, String candidate_name,
			String candidate_email, String candidate_phone, String candidate_dob)
	{
		Session session = entityManager.unwrap(Session.class);
		candidate.setCandidate_name(candidate_name);
		candidate.setCandidate_aadhar(candidate_aadhar);
		candidate.setCandidate_password(candidate_password);
		candidate.setCandidate_dob(candidate_dob);
		candidate.setCandidate_email(candidate_email);
		candidate.setCandidate_phone(candidate_phone);
		System.out.println("registeration sucess");
		session.save(candidate);
		session.close();
		return ResponseEntity.status(HttpStatus.CREATED).body("Candidate Registered Sucessfully....");
		

	}


}
