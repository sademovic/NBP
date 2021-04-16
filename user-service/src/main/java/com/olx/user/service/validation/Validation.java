package com.olx.user.service.validation;

public interface Validation {

    String NOT_SPECIFIED = "Vrijednost nije specificirana!";
    
    String ALREADY_EXISTS = "Korisnik sa ovim email-om vec postoji!";
    
    String USER_DOES_NOT_EXISTS = "Korisnik sa ovim email-om ne postoji!";
    
    String USER_PASSWORD_INCORRECT ="Neispravan password!";
    
    String PASSWORD_UPPER_CASE = "Password mora sadrzavati veliko slovo.";
    
    String PASSWORD_LOWER_CASE = "Password mora sadrzavati malo slovo.";
    
    String PASSWORD_NUMBER = "Password mora sadrzavati cifu.";
    
    String PASSWORD_LENGTH = "Password mora sadrzavati najmanje 6 karaktera!";
    
    String PASSWORD_SPECIAL ="Password mora sadrzavati karakter koji nije slovo ili cifra!";
    
    String EMAIL_INVALID = "Email mora biti formata a+@a+.a+, a+ oznacava jedan ili vise karaktera (A-Z, a-z, 0-9, _ , - i .)!";
    
    String FIRSTNAME_INVALID ="Ime mora da pocinje velik slovom i da sadrzava bar dva karaktera!";
    
    String LASTNAME_INVALID ="Prezime mora da pocinje velik slovom i da sadrzava bar dva karaktera!";
    
    String PHONE_NUMBER_INVALID = "Broj telefona mora biti formata +***********.";
    
    String LOCATION_INVALID = "Lokacija mora sadrzati najmanje jednu rijec koja pocinje velikim slovom!";
    
    String REGEX_EMAIL ="^[A-Za-z\\.\\-\\_]+\\@{1}[a-z]+\\.{1}[a-z]+$";
    
    String REGEX_PHONENUMBER ="^[0-9]{11}$";
    
    String REGEX_TEXT="^([A-Z]+[A-Za-z]+\\s{0,1})+$";
}
