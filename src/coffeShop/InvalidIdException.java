package coffeShop;



public class InvalidIdException extends Exception{
	public InvalidIdException(String id){
		super(id + " not valid. ItemId must contain a category header (DES, HOT, CLD, ADD, FOD) and a 3 digit number");
	}
}
