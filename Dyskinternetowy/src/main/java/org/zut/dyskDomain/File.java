package org.zut.dyskDomain;

import java.sql.Date;

public class File {
	private int Id;
	private String Lokalizacja;
	private boolean Folder;
	private String Nazwa;
	private String SumaKontrolna;
	private String Rozmiar;
	private String Format;
	private int Wlasciciel;
	private String Opis;
	private Date DataDodania;
	public File() { }
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getLokalizacja() {
		return Lokalizacja;
	}
	public void setLokalizacja(String lokalizacja) {
		Lokalizacja = lokalizacja;
	}
	public boolean isFolder() {
		return Folder;
	}
	public void setFolder(boolean folder) {
		Folder = folder;
	}
	public boolean getFolder() {
		return Folder;
	}
	public String getNazwa() {
		return Nazwa;
	}
	public void setNazwa(String nazwa) {
		Nazwa = nazwa;
	}
	public String getSumaKontrolna() {
		return SumaKontrolna;
	}
	public void setSumaKontrolna(String sumaKontrolna) {
		SumaKontrolna = sumaKontrolna;
	}
	public String getRozmiar() {
		return Rozmiar;
	}
	public void setRozmiar(String rozmiar) {
		Rozmiar = rozmiar;
	}
	public String getFormat() {
		return Format;
	}
	public void setFormat(String format) {
		Format = format;
	}
	public int getWlasciciel() {
		return Wlasciciel;
	}
	public void setWlasciciel(int wlasciciel) {
		Wlasciciel = wlasciciel;
	}
	public String getOpis() {
		return Opis;
	}
	public void setOpis(String opis) {
		Opis = opis;
	}
	public Date getDataDodania() {
		return DataDodania;
	}
	public void setDataDodania(Date dataDodania) {
		DataDodania = dataDodania;
	}
}
