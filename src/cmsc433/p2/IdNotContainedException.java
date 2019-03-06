package cmsc433.p2;

public class IdNotContainedException extends Exception{
	public IdNotContainedException(String itemId){
		super(itemId + " is not in Menu.");
	}
}