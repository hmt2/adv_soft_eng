package exceptions;


/** Exception for Item and used also in Order : if the Id is not in proper format 
 * (it should not be too long and should contain a category header (DES, HOT, CLD, ADD, FOD) )
 * 
 *
 */
@SuppressWarnings("serial")
public class InvalidIdException extends Exception{
	public InvalidIdException(String message){
		super(message);
	}
}
