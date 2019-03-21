package exceptions;

/* Exception for Menu : if the itemId is not found in the menu
 * 
 */
@SuppressWarnings("serial")
public class IdNotContainedException extends Exception{
	public IdNotContainedException(String itemId){
		super(itemId + " is not in Menu.");
	}
}