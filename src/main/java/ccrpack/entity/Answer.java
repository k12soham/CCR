package ccrpack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer answer_id;
	private boolean answer;

	@ManyToOne
	@JoinColumn(name = "candidate_id")
	private Candidate candidate;

	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question;

	public Answer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Answer(Integer answer_id, boolean answer, Candidate candidate, Question question) {
		super();
		this.answer_id = answer_id;
		this.answer = answer;
		this.candidate = candidate;
		this.question = question;
	}

	public Integer getAnswer_id() {
		return answer_id;
	}

	public void setAnswer_id(Integer answer_id) {
		this.answer_id = answer_id;
	}

	public boolean isAnswer() {
		return answer;
	}

	public void setAnswer(boolean answer) {
		this.answer = answer;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	
	
}
