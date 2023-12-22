package ccrpack.controller;

import java.io.UnsupportedEncodingException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ccrpack.entity.Answer;
import ccrpack.entity.Candidate;
import ccrpack.entity.CcrAdmin;
import ccrpack.entity.Comment;
import ccrpack.entity.Company;
import ccrpack.entity.Hr;
import ccrpack.entity.OcrResult;

import ccrpack.entity.RatingForm;
import ccrpack.repo.OcrResultRepo;

import ccrpack.entity.Question;

import ccrpack.repo.CandidateRepo;

import ccrpack.service.Ccrservice;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

//new
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class Ccrcontroller {
//comment
	@Autowired
	Ccrservice ccrservice;

	@Autowired
	CandidateRepo candinter;

	@Autowired
	OcrResultRepo ocrRepo;

	Hr hra = new Hr();
	Company cm = new Company();
	RatingForm rf = new RatingForm();
	Candidate cand = new Candidate();
	CcrAdmin cadmin = new CcrAdmin();
	OcrResult ocrResult = new OcrResult();

	Question q = new Question();
	Answer a = new Answer();

	@PersistenceContext
	EntityManager entityManager;

	@Value("${upload.dir}") // Define the directory where you want to store uploaded images in
							// application.properties
	private String uploadDir;

	// Login CCR Admin
	@PostMapping(value = "/ccradminlogin")
	public ResponseEntity<?> ccrLogin(@RequestBody CcrAdmin ccrAdmin) {
		return ccrservice.ccrLogin(ccrAdmin);
	}

	// Login Candidate
	@PostMapping(value = "/candidatelogin")
	public ResponseEntity<?> candlogin(@RequestBody Candidate candidate) {
		return ccrservice.candlogin(candidate);
	}

	// Register Candidate
	@PostMapping(value = "/registercandidate")
	public ResponseEntity<String> registerCandidate(@RequestBody Candidate candidate) {
		return ccrservice.registerCandidate(candidate);
	}

	// HR Login
	@PostMapping(value = "/hrlogin")
	public ResponseEntity<?> hrlogin(@RequestBody Hr hr) {
		return ccrservice.hrlogin(hr);
	}

	// Company & HR Registration
	@PostMapping(value = "/addcomapny")
	public ResponseEntity<?> companyReg(@RequestBody Company company) {

		return ccrservice.companyReg(company);

	}

	// Add recruiter from Admin
	/*
	 * @PostMapping(value = "/Adminaddrecruiter") public ResponseEntity<String>
	 * AdminAddrecruiter(@RequestParam Integer hrid, @RequestParam String hr_name,
	 * 
	 * @RequestParam String hr_email, @RequestParam boolean approver, @RequestParam
	 * boolean add_team) {
	 * 
	 * return ccrservice.AdminAddrecruiter(hrid, hr_name, hr_email, approver,
	 * add_team);
	 * 
	 * }
	 */

	// Add recruiter from Admin
	@PostMapping(value = "/Adminaddrecruiter")
	public ResponseEntity<String> AdminAddrecruiter(@RequestParam Integer hrid, @RequestBody Hr hr) {

		return ccrservice.AdminAddrecruiter(hrid, hr);

	}

	// Add recruiter from TeamLead
	/*
	 * @PostMapping(value = "/TLaddrecruiter") public ResponseEntity<?>
	 * TLAddrecruiter(@RequestParam Integer hrid, @RequestParam String hr_name,
	 * 
	 * @RequestParam int approver, @RequestParam boolean add_team) {
	 * 
	 * return ccrservice.TLAddrecruiter(hrid, hr_name, approver, add_team); }
	 */

	@PostMapping(value = "/Recruiteraddrecruiter")
	public ResponseEntity<?> RecruiterAddrecruiter(@RequestBody Hr hr) {

		return ccrservice.RecruiterAddrecruiter(hr);
	}

	// add answers of candidate
	@PostMapping("/answers")
	public ResponseEntity<?> submitAnswers(@RequestParam int candidate_id, @RequestBody List<Answer> answers)
			throws Exception {

		return ccrservice.submitAnswers(candidate_id, answers);
	}

	// find average score
	@GetMapping("/averageScore")
	public ResponseEntity<?> getCandidateAverageScore(@RequestBody Candidate candidate) {

		return ccrservice.getCandidateAverageScore(candidate);
	}

	// rating form data save for yes no questions and calculate total score
	@PostMapping(value = "/saveYesNo")
	public ResponseEntity<String> saveYesNoAns(@RequestBody RatingForm ratingForm) {

		return ccrservice.saveYesNoAns(ratingForm);
	}

	@PostMapping(value = "/rating")
	public ResponseEntity<String> Rating(@RequestParam Boolean q1, @RequestParam Boolean q2, @RequestParam int total,
			@RequestParam int candidate_id, @RequestParam int rec_id) {

		return ccrservice.Rating(q1, q2, total, candidate_id, total, rec_id);
	}

	// New request for admin from recruiters
	@GetMapping(value = "/getnew")
	public List<RatingForm> Getrequest(@RequestParam int rec_id) {

		return ccrservice.Getrequest(rec_id);

	}

	// Change approver
	@PostMapping(value = "/changeapprover")
	public ResponseEntity<String> ChangeApprover(@RequestParam Integer hrid, @RequestParam String hr_email) {

		return ccrservice.ChangeApprover(hrid, hr_email);

	}

	// Forgot password API
	@PostMapping(value = "/forgot-password")
	public ResponseEntity<String> sendOtpByEmail(@RequestBody Candidate candidate)
			throws UnsupportedEncodingException, MessagingException {

		return ccrservice.sendOtpByEmail(candidate);
	}

	// OTP Validation
	@PostMapping(value = "/candchangepassforgot")
	public ResponseEntity<String> candchangepassforgot(@RequestBody Candidate candidate) {
		return ccrservice.candchangepassforgot(candidate);
	}

	// Change password (Using otp)
	@PutMapping(value = "/finalcandchangepass")
	public ResponseEntity<String> finalcandchangepass(@RequestBody Candidate candidate) {
		return ccrservice.finalcandchangepass(candidate);
	}

	// CCR Admin & Super Admin Login
	@PostMapping(value = "/ccrlogin")
	public ResponseEntity<?> ccrlogin(@RequestBody CcrAdmin ccradmin) {
		return ccrservice.ccrlogin(ccradmin);
	}

	// Add CCR Admin from Super Admin dashboard for ccradmin
	@PostMapping(value = "/addccradmin")
	public ResponseEntity<String> addccradmin(@RequestBody CcrAdmin ccradmin) {

		return ccrservice.addccradmin(ccradmin);

	}

	// save image/pdf/excel in database
	@PostMapping(value = "/uploadFile")
	public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) {

		return ccrservice.uploadFile(file);

	}

	// get image/pdf/excel in database
	@GetMapping("/getFile")
	public ResponseEntity<byte[]> getFile(@RequestParam Long id) {

		return ccrservice.getFile(id);

	}

	// Get characters from image
	@PostMapping(value = "/extactCharactersFromImage/{imageId}")
	public ResponseEntity<?> getCharFromImg(@PathVariable Long imageId) {
		return ccrservice.getCharFromImg(imageId);

	}

	// Backround verfication candidate Aadhar
	@PostMapping("/background-verify")
	public ResponseEntity<?> BackgroundVerify(@RequestParam("image") MultipartFile file) {

		return ccrservice.BackgroundVerify(file);
	}

	@GetMapping("/getFile1")
	public ResponseEntity<byte[]> getFile1(@RequestParam Integer candidate_id) {
		return ccrservice.getFile1(candidate_id);
	}

	////////// Comment Section on rating form
	@PostMapping("/commentsapprove")
	public ResponseEntity<?> commentsapprove(@RequestParam Integer candidate_id, @RequestParam Integer hr_id,
			@RequestParam String comment) {
		return ccrservice.commentsapprove(candidate_id, hr_id, comment);
	}

	///// get all candidates review request list
	@GetMapping(value = "/getallcandidatescomment")
	public List<Comment> getallcandidatescomment() {

		return ccrservice.getallcandidatescomment();

	}

	///// get hr request for approval to hradmin
	@GetMapping("/getcommentrequest")
	public ResponseEntity<?> getcommentrequest(@RequestParam Integer comment_id) {
		return ccrservice.getcommentrequest(comment_id);
	}
//	@GetMapping("/getcommentrequest")
//	public ResponseEntity<?> getcommentrequest(@RequestBody Comment comment) {
//		return ccrservice.getcommentrequest(comment);
//	}

	/////// comment approved by hr admin
	@PostMapping("/commentaccept")
	public ResponseEntity<?> commentaccept(@RequestParam Integer candidate_id, @RequestParam Integer hr_id,
			@RequestParam Integer comment_id) {
		return ccrservice.commentaccept(candidate_id, hr_id, comment_id);
	}

	/////// given suggestion by hr admin to recruiter to change comment
	@PostMapping("/commentsuggestion")
	public ResponseEntity<?> commentsuggestion(@RequestParam Integer candidate_id, @RequestParam Integer hr_id,
			@RequestParam Integer comment_id, @RequestParam String suggestion) {
		return ccrservice.commentsuggestion(candidate_id, hr_id, comment_id, suggestion);
	}

	/////// after giving suggestion the update comment request will go to the
	/////// recruiter
	@GetMapping("/newcommenttocand")
	public ResponseEntity<?> getsuggestion(@RequestParam Integer candidate_id, @RequestParam Integer hr_id,
			@RequestParam Integer comment_id) {
		return ccrservice.getsuggestion(candidate_id, hr_id, comment_id);
	}

	/// superadmin dash
	//////// list of ccr admin
	@GetMapping("/getlistccradmin")
	public List<CcrAdmin> getlistccradmin() {

		return ccrservice.getlistccradmin();

	}

	////// delete ccr admin by super admin
	@DeleteMapping("/delccradmin")
	public ResponseEntity<?> delccradmin(@RequestParam String ccr_email) {
		return ccrservice.delccradmin(ccr_email);
	}

	/////////// UPDATE PROFILE CANDIDATE
	// Change password (from update account)
	@PutMapping(value = "/candchangepass")
	public ResponseEntity<String> candchangepass(@RequestParam int candidate_id, @RequestParam String currentpass,
			@RequestParam String newpass) {
		return ccrservice.candchangepass(candidate_id, currentpass, newpass);
	}

	@PutMapping(value = "/updatecandprofile")
	public ResponseEntity<?> updatecandprofile(@RequestParam int candidate_id, @RequestParam String newphone,
			@RequestParam String newemail) {
		return ccrservice.updatecandprofile(candidate_id, newphone, newemail);
	}

	/////////// UPDATE PROFILE Recruiter
	// Change password (from update account)
	@PutMapping(value = "/recchangepass")
	public ResponseEntity<String> recchangepass(@RequestParam int hr_id, @RequestParam String currentpass,
			@RequestParam String newpass) {
		return ccrservice.recchangepass(hr_id, currentpass, newpass);
	}

	@PutMapping(value = "/updaterecprofile")
	public ResponseEntity<?> updaterecprofile(@RequestParam int hr_id, @RequestParam String newphone,
			@RequestParam String newemail) {
		return ccrservice.updaterecprofile(hr_id, newphone, newemail);
	}

/////////// UPDATE PROFILE CCR ADMIN
// Change password (from update account)
	@PutMapping(value = "/ccrchangepass")
	public ResponseEntity<String> ccrchangepass(@RequestParam int ccr_admin_id, @RequestParam String currentpass,
			@RequestParam String newpass) {
		return ccrservice.ccrchangepass(ccr_admin_id, currentpass, newpass);
	}

	@PutMapping(value = "/updateccrprofile")
	public ResponseEntity<?> updateccrprofile(@RequestParam int ccr_admin_id, @RequestParam Long newphone,
			@RequestParam String newemail) {
		return ccrservice.updateccrprofile(ccr_admin_id, newphone, newemail);
	}

}
