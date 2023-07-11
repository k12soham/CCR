package ccrpack.service;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ccrpack.entity.Candidate;
import ccrpack.entity.Company;
import ccrpack.entity.HrAdmin;
import ccrpack.entity.RatingForm;
import ccrpack.repo.CandInter;
import ccrpack.repo.CompanyInter;
import ccrpack.repo.HrInter;
import ccrpack.repo.RatingInter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
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

}
