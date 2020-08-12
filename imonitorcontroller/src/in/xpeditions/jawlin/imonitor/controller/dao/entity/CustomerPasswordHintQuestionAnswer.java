/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class CustomerPasswordHintQuestionAnswer {
	
	private long quesId;
	private String hintQuestion;
	private String hintAnswer;
	//private boolean isHintQuesAnswerSet;
	
	public long getQuesId() {
		return quesId;
	}
	public void setQuesId(long quesId) {
		this.quesId = quesId;
	}
	public String getHintQuestion() {
		return hintQuestion;
	}
	public void setHintQuestion(String hintQuestion) {
		this.hintQuestion = hintQuestion;
	}
	public String getHintAnswer() {
		return hintAnswer;
	}
	public void setHintAnswer(String hintAnswer) {
		this.hintAnswer = hintAnswer;
	}
//	public boolean isHintQuesAnswerSet() {
//		return isHintQuesAnswerSet;
//	}
//	public void setHintQuesAnswerSet(boolean isHintQuesAnswerSet) {
//		this.isHintQuesAnswerSet = isHintQuesAnswerSet;
//	}


}
