package ccrpack.controller;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.persister.collection.mutation.RowMutationOperations.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ccrpack.entity.Candidate;
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

@CrossOrigin(origins="http://localhost:3000")
@RestController
public class Mycontroller {
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

	@PostMapping(value = "/candidateregi")
	public ResponseEntity<?> candregi(@RequestParam Long candidate_aadhar, @RequestParam String candidate_password, @RequestParam String candidate_name,
			@RequestParam String candidate_email, @RequestParam String candidate_phone, @RequestParam String candidate_dob) {
		Session session = entityManager.unwrap(Session.class);
		cand.setCandidate_name(candidate_name);
		cand.setCandidate_aadhar(candidate_aadhar);
		cand.setCandidate_password(candidate_password);
		cand.setCandidate_dob(candidate_dob);
		cand.setCandidate_email(candidate_email);
		cand.setCandidate_phone(candidate_phone);
		System.out.println("registeration sucess");
		session.save(cand);
		session.close();
		return new ResponseEntity<Candidate>(cand, HttpStatus.OK);
		}
	@PostMapping(value = "/candidatelogin")
	public ResponseEntity<?> candlogin(@RequestParam Long candidate_aadhar, @RequestParam String candidate_password) {
	    Session session = entityManager.unwrap(Session.class);
	    CriteriaBuilder cb = session.getCriteriaBuilder();
	    CriteriaQuery<Candidate> cr = cb.createQuery(Candidate.class);
	    Root<Candidate> root = cr.from(Candidate.class);
	    cr.select(root).where(
	        cb.equal(root.get("candidate_aadhar"), candidate_aadhar),
	        cb.equal(root.get("candidate_password"), candidate_password)
	    );
	    TypedQuery<Candidate> query = session.createQuery(cr);
	    Candidate cand = ((org.hibernate.query.Query<Candidate>) query).uniqueResult();
	    if (cand != null) {
	        System.out.println("Login Candidate success");
	        session.save(cand);
	    } else {
	        System.out.println("Candidate not found");
	    }
	    session.close();
	    return new ResponseEntity<>(cand, HttpStatus.OK);
	}
	@PutMapping(value="/candchangepass")
	public ResponseEntity<?>changePassword(@RequestParam int candidate_id,@RequestParam String currentpass,@RequestParam String newpass) {
		    Session session = entityManager.unwrap(Session.class);
		    CriteriaBuilder cb = session.getCriteriaBuilder();
		    CriteriaQuery<Candidate> cr = cb.createQuery(Candidate.class);
		    Root<Candidate> root = cr.from(Candidate.class);
		    cr.select(root).where(
		    	cb.equal(root.get("candidate_id"), candidate_id),
		        cb.equal(root.get("candidate_password"), currentpass)
	      );
		    Query query = session.createQuery(cr);
		    Candidate results=null;
		    try {
		        results = (Candidate) query.getSingleResult();
		        cand = candinter.getById(candidate_id);
		    	cand.setCandidate_password(newpass);
		    	candinter.save(cand);
		    	session.close();
		    	return new ResponseEntity<>(cand, HttpStatus.OK);	
		    }
		    catch (NoResultException e) {
		    	session.close();
				return (ResponseEntity<?>) ResponseEntity.badRequest().body(" Current Password doesn't match...");
		    }
		}

	@PostMapping(value = "/addcomapny")
	public ResponseEntity<?> companyreg(@RequestParam String cname, @RequestParam Long tan,
			@RequestParam String hr_name, @RequestParam Long phone, @RequestParam String role) {

		Session session = entityManager.unwrap(Session.class);

		cm.setCompany_name(cname);
		cm.setCompany_tan(tan);
		hra.setHr_name(hr_name);
		hra.setHr_phone(phone);
		hra.setHr_role(role);
		hra.setCompany(cm);
		

		session.save(cm);
		session.save(hra);

		return null;

	}
	
	
	@PostMapping(value = "/rating")
	public ResponseEntity<?> Rating (@RequestParam Boolean q1,@RequestParam Boolean q2,@RequestParam int total,
			@RequestParam int candidate_id,@RequestParam int rec_id) {

		Session session = entityManager.unwrap(Session.class);


		rf.setQ1(q1);
		rf.setQ2(q2);
		rf.setRating_total(total);
		cand.setCandidate_id(candidate_id);
		hra.setHr_admin_id(rec_id);
		rf.setCandidate(cand);
		rf.setHrAdmin(hra);
		
		hra=hri.getById(rec_id);
		int a= hra.getApprover();
		hra=hri.getById(a);
		int b=hra.getHr_admin_id();

		rf.setNew_request(true);
		rf.setApprover_id(b);
		
		session.save(rf);
		hri.save(hra);
session.close();
		return null;

	}
	
	@GetMapping(value = "/getnew")
	public List<RatingForm> Getrequest (@RequestParam int rec_id) {

		Session session = entityManager.unwrap(Session.class);

		CriteriaBuilder cb = session.getCriteriaBuilder();

		CriteriaQuery<RatingForm> cr = cb.createQuery(RatingForm.class);
		Root<RatingForm> root = cr.from(RatingForm.class);

		cr.select(root).where((cb.equal(root.get("new_request"), true)),
				((cb.equal(root.get("approver_id"), rec_id))));

		Query query = session.createQuery(cr);
		List<RatingForm> results = query.getResultList();
		System.out.println(results);
		session.close();

		return  results;
		

	}
	
	
	@PostMapping(value = "/addAdminrecruiter")
	public ResponseEntity<?> Addrecruiter(@RequestParam Integer hrid ,@RequestParam String hr_name, @RequestParam boolean approver,
			@RequestParam boolean add_team) {

		Session session = entityManager.unwrap(Session.class);

	
			hra.setHr_name(hr_name);
			hra.setAdded_by(hrid);
			session.save(hra);
			if(approver==true&&add_team==true)
			{
				int a=hra.getHr_admin_id();
				System.out.println(a);
				hra.setApprover(a);
			
				hra.setHr_role("Admin");
			}
			else if(approver==false&&add_team==true)
			{
				hra.setApprover(hrid);
				hra.setHr_role("TeamLead");
			}
			else {
				hra.setApprover(hrid);
				hra.setHr_role("Rec");
			}
			

			hri.save(hra);
		
		
		
		session.close();
		return null;

	}
	
	@PostMapping(value = "/addTLrecruiter")
	public ResponseEntity<?> AddTLrecruiter(@RequestParam Integer hrid ,@RequestParam String hr_name, @RequestParam boolean approver,
			@RequestParam boolean add_team) {

		Session session = entityManager.unwrap(Session.class);
	
			
			hra.setHr_name(hr_name);
			hra.setAdded_by(hrid);
			session.save(hra);
	
			
			CriteriaBuilder cb = session.getCriteriaBuilder();

			CriteriaQuery<HrAdmin> cr = cb.createQuery(HrAdmin.class);
			Root<HrAdmin> root = cr.from(HrAdmin.class);
			cr.select(root).where((cb.equal(root.get("hr_admin_id"), hrid)));
			Query query = session.createQuery(cr);
			HrAdmin z= (HrAdmin) query.getSingleResult();
			int b= z.getApprover();
			System.out.println(b);
			
			
			int a= 5;
	
			 if(approver==false&&add_team==true)
			{
				hra.setApprover(b);
				hra.setHr_role("TeamLead");
			}
			else {
				hra.setApprover(b);
				hra.setHr_role("Rec");
			}
			

			hri.save(hra);
		

		session.close();
		return null;

	}
	
	
	@PostMapping(value = "/changeapprover")
	public HrAdmin ChangeApprover(@RequestParam Integer hrid ,@RequestParam String hr_email) {

		Session session = entityManager.unwrap(Session.class);
		
		CriteriaBuilder cb = session.getCriteriaBuilder();

		CriteriaQuery<HrAdmin> cr = cb.createQuery(HrAdmin.class);
		Root<HrAdmin> root = cr.from(HrAdmin.class);
		cr.select(root).where((cb.equal(root.get("hr_email"), hr_email)));
		Query query = session.createQuery(cr);
		HrAdmin z= (HrAdmin) query.getSingleResult();
		System.out.println(z);
		if (z == null) {
			return (HrAdmin) ResponseEntity.badRequest();

		} else {
		
			z.setApprover(hrid);
			hri.save(z);
			return null;
		}
		
		
	}
	
	
	
	
}
