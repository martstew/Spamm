package me.skyrimfan1.spamm.exceptions;

public class AsyncCallableException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public AsyncCallableException(Exception e){
		super(e.toString()+" screwed up the chat thread. Report this to skyrimfan1 ASAP.");
	}

}
