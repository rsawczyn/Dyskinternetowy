package org.zut.dyskDomain;

import java.io.Serializable;

public class AjaxDataHelper  implements Serializable
{
	String Tworca;
	String Przypisany;
	String Tresc;
	public String getTworca() {
		return Tworca;
	}
	public void setTworca(String tworca) {
		Tworca = tworca;
	}
	public String getPrzypisany() {
		return Przypisany;
	}
	public void setPrzypisany(String przypisany) {
		Przypisany = przypisany;
	}
	public String getTresc() {
		return Tresc;
	}
	public void setTresc(String tresc) {
		Tresc = tresc;
	}
	
}
