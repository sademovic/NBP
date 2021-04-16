package com.olx.message.service.validation;

public interface Validation {

	String NOT_SPECIFIED = "Vrijednost nije specificirana!";
	String RECEIVER_DOES_NOT_EXIST = "Ne postoji receiverId! ";
	String SENDER_DOES_NOT_EXIST = "Ne postoji senderId";
	String CHAT_DOES_NOT_EXIST = "Chat ne postoji!";
	String CHAT_ID_NOT_NULL = "Chat id ne moze bit null!";
	String USER_ID_NOT_NULL = "User id ne moze bit null!";
}
