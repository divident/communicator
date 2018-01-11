package server.beans;

import java.io.Serializable;

/*
 *  Opis klasy: 
 *  Klasa ta ma zadanie opakowywa� tre�� komunikatu wysy�anego do servera. 
 *  Z punktu widzenia servera przy przekazywaniu wiadomo�ci kluczowa jest 
 *  znajomo�� 'nicku' albo komunikatu 
 */
@SuppressWarnings("serial")
public class Message implements Serializable {

	
	// Attributes 
	String nickTo;              
	String nickFrom;       
	String message;
	
	
	// Constructor 
	public Message(String nickTo, String nickFrom, String message) {
		this.nickTo = nickTo;
		this.nickFrom = nickFrom;
		this.message = message;
	}

	
	// Getters 
	public String getNickTo() {
		return nickTo;
	}
	
	public String getNickFrom() {
		return nickFrom;
	}
	
	public String getMessage() {
		return message;
	}


	// Setters 
	public void setNickTo(String nickTo) {
		this.nickTo = nickTo;
	}

	public void setNickFrom(String nickFrom) {
		this.nickFrom = nickFrom;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}


	@Override
	public String toString() {
		return  nickFrom ;
	}
}
