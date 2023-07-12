package ccrpack.service;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ccrpack.entity.Candidate;
import ccrpack.entity.CcrAdmin;
import ccrpack.entity.Company;
import ccrpack.entity.HrAdmin;
import ccrpack.entity.RatingForm;
import ccrpack.repo.CandInter;
import ccrpack.repo.CompanyInter;
import ccrpack.repo.HrInter;
import ccrpack.repo.RatingInter;
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
	CompanyInter comp;

	@Autowired
	HrInter hri;
	
	
	@Autowired
	RatingInter ri;
	
	@Autowired
	CandInter candinter;

	

	HrAdmin hra = new HrAdmin();
	Company cm = new Company();
	RatingForm rf=  new RatingForm();
	Candidate cand= new Candidate();
	CcrAdmin cadmin=new CcrAdmin();
	
	
	@PersistenceContext
	EntityManager entityManager;

	public ResponseEntity<String> companyreg(String cname, Long tan, String hr_name, Long phone, String role) {
		Session session = entityManager.unwrap(Session.class);

		cm.setCompany_name(cname);
		cm.setCompany_tan(tan);
		hra.setHr_name(hr_name);
		hra.setHr_phone(phone);
		hra.setHr_role(role);
		hra.setCompany(cm);
		

		session.save(cm);
		session.save(hra);
		 return ResponseEntity.status(HttpStatus.CREATED).body("Candidate registered");
	}

	public ResponseEntity<String> Rating(Boolean q1, Boolean q2, int total,int candidate_id, int total2, int rec_id) {
		Session session = entityManager.unwrap(Session.class);

		rf.setQ1(q1);
		rf.setQ2(q2);
		rf.setRating_total(total);
		cand.setCandidate_id(candidate_id);
		hra.setHr_admin_id(rec_id);
		rf.setCandidate(cand);
		rf.setHrAdmin(hra);

		hra = hri.getById(rec_id);
		int a = hra.getApprover();
		hra = hri.getById(a);
		int b = hra.getHr_admin_id();

		rf.setNew_request(true);
		rf.setApprover_id(b);

		session.save(rf);
		hri.save(hra);
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

	public ResponseEntity<String> AddAdminrecruiter(Integer hrid, String hr_name, boolean approver, boolean add_team) {

		Session session = entityManager.unwrap(Session.class);

		hra.setHr_name(hr_name);
		hra.setAdded_by(hrid);
		session.save(hra);
		if (approver == true && add_team == true) {
			int a = hra.getHr_admin_id();
			System.out.println(a);
			hra.setApprover(a);

			hra.setHr_role("Admin");
		} else if (approver == false && add_team == true) {
			hra.setApprover(hrid);
			hra.setHr_role("TeamLead");
		} else {
			hra.setApprover(hrid);
			hra.setHr_role("Rec");
		}

		hri.save(hra);

		session.close();
		return ResponseEntity.status(HttpStatus.CREATED).body("Admin saved");
	}

	public ResponseEntity<String> AddTLrecruiter(Integer hrid, String hr_name, boolean approver, boolean add_team) {
		Session session = entityManager.unwrap(Session.class);

		hra.setHr_name(hr_name);
		hra.setAdded_by(hrid);
		session.save(hra);

		CriteriaBuilder cb = session.getCriteriaBuilder();

		CriteriaQuery<HrAdmin> cr = cb.createQuery(HrAdmin.class);
		Root<HrAdmin> root = cr.from(HrAdmin.class);
		cr.select(root).where((cb.equal(root.get("hr_admin_id"), hrid)));
		Query query = session.createQuery(cr);
		HrAdmin z = (HrAdmin) query.getSingleResult();
		int b = z.getApprover();
		System.out.println(b);

		int a = 5;

		if (approver == false && add_team == true) {
			hra.setApprover(b);
			hra.setHr_role("TeamLead");
		} else {
			hra.setApprover(b);
			hra.setHr_role("Rec");
		}

		hri.save(hra);

		session.close();
		return ResponseEntity.status(HttpStatus.CREATED).body("TL saved");

	}

	public  ResponseEntity<String>  ChangeApprover(Integer hrid, String hr_email) {
		Session session = entityManager.unwrap(Session.class);

		CriteriaBuilder cb = session.getCriteriaBuilder();

		CriteriaQuery<HrAdmin> cr = cb.createQuery(HrAdmin.class);
		Root<HrAdmin> root = cr.from(HrAdmin.class);
		cr.select(root).where((cb.equal(root.get("hr_email"), hr_email)));
		Query query = session.createQuery(cr);
		HrAdmin z = (HrAdmin) query.getSingleResult();
		System.out.println(z);
		if (z == null) {
		 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");

		} else {

			z.setApprover(hrid);
			hri.save(z);
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
			cand = candinter.getById(candidate_id);
			cand.setCandidate_password(newpass);
			candinter.save(cand);
			session.close();
			
			 return ResponseEntity.status(HttpStatus.CREATED).body("Password Changed Sucessfully");
		} catch (NoResultException e) {
			session.close();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Current password doesnt matched");
		}
	
	}

	public ResponseEntity<?> candlogin(String candidate_email, String candidate_password) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Candidate> cr = cb.createQuery(Candidate.class);
		Root<Candidate> root = cr.from(Candidate.class);
		cr.select(root).where(cb.equal(root.get("candidate_email"), candidate_email),
				cb.equal(root.get("candidate_password"), candidate_password));
		TypedQuery<Candidate> query = session.createQuery(cr);
		Candidate cand = ((org.hibernate.query.Query<Candidate>) query).uniqueResult();
		if (cand != null) {
			session.save(cand);
			session.close();
			return ResponseEntity.status(HttpStatus.OK).body("Candidate Login Sucessfully....");
		} else {
			session.close();
			return ResponseEntity.status(HttpStatus.OK).body("Candidate Not Found....");
		}
		}

	public ResponseEntity<String> candregi(Long candidate_aadhar, String candidate_password, String candidate_name,
			String candidate_email, String candidate_phone, String candidate_dob) {
		Session session = entityManager.unwrap(Session.class);
		cand.setCandidate_name(candidate_name);
		cand.setCandidate_aadhar(candidate_aadhar);
		cand.setCandidate_password(candidate_password);
		cand.setCandidate_dob(candidate_dob);
		cand.setCandidate_email(candidate_email);
		cand.setCandidate_phone(candidate_phone);
		session.save(cand);
		session.close();
		return ResponseEntity.status(HttpStatus.CREATED).body("Candidate Registered Sucessfully....");
		

	}

	public ResponseEntity<?> hrAdminlogin(String hr_email, String hr_password) {
		
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<HrAdmin> cr = cb.createQuery(HrAdmin.class);
		Root<HrAdmin> root = cr.from(HrAdmin.class);
		cr.select(root).where(cb.equal(root.get("hr_email"), hr_email),
				cb.equal(root.get("hr_password"), hr_password));
		TypedQuery<HrAdmin> query = session.createQuery(cr);
		HrAdmin hra = ((org.hibernate.query.Query<HrAdmin>) query).uniqueResult();
		if (hra != null) {
			session.save(hra);
			session.close();
			return ResponseEntity.status(HttpStatus.OK).body("HR Admin Login Sucessfully....");
		} else {
			session.close();
			return ResponseEntity.status(HttpStatus.OK).body("HR Admin Record Not Found....");
		}
		
	
	}

	public ResponseEntity<?> ccrAdminlogin(String ccr_email, String ccr_password) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<CcrAdmin> cr = cb.createQuery(CcrAdmin.class);
		Root<CcrAdmin> root = cr.from(CcrAdmin.class);
		cr.select(root).where(cb.equal(root.get("ccr_email"), ccr_email),
				cb.equal(root.get("ccr_password"), ccr_password));
		TypedQuery<CcrAdmin> query = session.createQuery(cr);
		CcrAdmin cadmin = ((org.hibernate.query.Query<CcrAdmin>) query).uniqueResult();
		if (cadmin != null) {
			session.save(cadmin);
			session.close();
			return ResponseEntity.status(HttpStatus.OK).body("CCR Admin Login Sucessfully....");
		} else {
			session.close();
			return ResponseEntity.status(HttpStatus.OK).body("CCR Admin Record Not Found....");
		}
		
	
	}

//	public ResponseEntity<String> hradminchangepass(int hr_admin_id, String currentpass, String newpass) {
//		
//		Session session = entityManager.unwrap(Session.class);
//		CriteriaBuilder cb = session.getCriteriaBuilder();
//		CriteriaQuery<HrAdmin> cr = cb.createQuery(HrAdmin.class);
//		Root<HrAdmin> root = cr.from(HrAdmin.class);
//		cr.select(root).where(cb.equal(root.get("hr_admin_id"), hr_admin_id),
//				cb.equal(root.get("hr_password"), currentpass));
//		Query query = session.createQuery(cr);
//		HrAdmin results = null;
//		try {
//			results = (HrAdmin) query.getSingleResult();
//			hra = HrInter.getById(hr_admin_id);
//			hra.setHr_password(newpass);
//			hri.save(hra);
//			session.close();
//			
//			 return ResponseEntity.status(HttpStatus.CREATED).body("Password Changed Sucessfully");
//		} catch (NoResultException e) {
//			session.close();
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Current password doesnt matched");
//		}
//	
//		
//	}

	

}
