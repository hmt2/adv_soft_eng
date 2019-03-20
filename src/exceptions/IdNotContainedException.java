package exceptions;

/* Exception for Menu : if the itemId is not found in the menu
 * 
 */
public class IdNotContainedException extends Exception{
	public IdNotContainedException(String itemId){
		super(itemId + " is not in Menu.");
	}
}