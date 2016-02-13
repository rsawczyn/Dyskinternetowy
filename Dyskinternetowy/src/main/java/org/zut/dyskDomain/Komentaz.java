package org.zut.dyskDomain;

import java.util.Date;

public class Komentaz 
{
	private int Id;
	private int Tworca;
	private String Tworca_Nazwa;
	private Date date;
	private String Tresc;
	private int Przypisany;
	public String getTworca_Nazwa() {
		return Tworca_Nazwa;
	}
	public void setTworca_Nazwa(String tworca_Nazwa) {
		Tworca_Nazwa = tworca_Nazwa;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getTworca() {
		return Tworca;
	}
	public void setTworca(int tworca) {
		Tworca = tworca;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTresc() {
		return Tresc;
	}
	public void setTresc(String tresc) {
		Tresc = tresc;
	}
	public int getPrzypisany() {
		return Przypisany;
	}
	public void setPrzypisany(int przypisany) {
		Przypisany = przypisany;
	}
	public Komentaz() {
		super();
	}
	public Komentaz(int id, int tworca, Date date, String tresc, int przypisany)
	{
		super();
		Id = id;
		Tworca = tworca;
		this.date = date;
		Tresc = tresc;
		Przypisany = przypisany;
	}
	
}
