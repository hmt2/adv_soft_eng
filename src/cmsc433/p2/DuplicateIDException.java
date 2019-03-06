package cmsc433.p2;

public class DuplicateIDException extends Exception{
	public DuplicateIDException(int dup){
		super("Duplicate id = " + dup);
	}
}
