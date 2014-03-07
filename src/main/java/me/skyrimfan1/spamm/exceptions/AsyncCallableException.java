package me.skyrimfan1.spamm.exceptions;

public class AsyncCallableException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public AsyncCallableException(Exception e){
		super(e.toString()+" decided to be fickle. Report this to skyrimfan1 ASAP.");
	}

}
