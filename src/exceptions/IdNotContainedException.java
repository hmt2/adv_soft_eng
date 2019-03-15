package exceptions;

public class IdNotContainedException extends Exception{
	public IdNotContainedException(String itemId){
		super(itemId + " is not in Menu.");
	}
}