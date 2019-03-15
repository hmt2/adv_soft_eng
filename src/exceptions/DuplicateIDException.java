package exceptions;

public class DuplicateIDException extends Exception{
	public DuplicateIDException(int dup){
		super("Duplicate id = " + dup);
	}
}
