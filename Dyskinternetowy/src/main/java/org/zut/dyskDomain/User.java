package org.zut.dyskDomain;

public class User 
{
	private int Id;
	private String Imie;
	private String Nazwisko;
	private String Login;
	private String Haslo;
	private String Email;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getImie() {
		return Imie;
	}
	public void setImie(String imie) {
		Imie = imie;
	}
	public String getNazwisko() {
		return Nazwisko;
	}
	public void setNazwisko(String nazwisko) {
		Nazwisko = nazwisko;
	}
	public String getLogin() {
		return Login;
	}
	public void setLogin(String login) {
		Login = login;
	}
	public String getHaslo() {
		return Haslo;
	}
	public void setHaslo(String haslo) {
		Haslo = haslo;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public User(int id, String imie, String nazwisko, String login, String haslo, String email) {
		super();
		Id = id;
		Imie = imie;
		Nazwisko = nazwisko;
		Login = login;
		Haslo = haslo;
		Email = email;
	}
	public User()
	{
		
	}

}
