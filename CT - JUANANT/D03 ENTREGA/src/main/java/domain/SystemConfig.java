package domain;

import java.util.ArrayList;
import java.util.List;

public class SystemConfig {
	
	//ATRIBUTOS DEL SISTEMA.
	public List<String> badWords;
	public List<String> goodWords;
	public Double finderTimer;
	public Integer finderMaxResults;
	public String name;
	public String banner;
	public String welcomeMessage;
	public List<String> spamWords;
	public Integer vat;
	public String phonePrefix;
	public List<String> creditCardPrefix;
	
	
	
	public enum badWords {
		NOT, BAD, HORRIBLE, AVERAGE, DISASTER
	};

	public enum goodWords {
		GOOD, FANTASTIC, EXCELLENT, GREAT, AMAZING, TERRIFIC, BEAUTIFUL
	}

	public List<String> getBadWords() {
		return badWords;
	}

	public void setBadWords(List<String> badWords) {
		this.badWords = badWords;
	}

	public List<String> getGoodWords() {
		return goodWords;
	}

	public void setGoodWords(List<String> goodWords) {
		this.goodWords = goodWords;
	}

	public Double getFinderTimer() {
		return finderTimer;
	}

	public void setFinderTimer(Double finderTimer) {
		this.finderTimer = finderTimer;
	}

	public Integer getFinderMaxResults() {
		return finderMaxResults;
	}

	public void setFinderMaxResults(Integer finderMaxResults) {
		this.finderMaxResults = finderMaxResults;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getWelcomeMessage() {
		return welcomeMessage;
	}

	public void setWelcomeMessage(String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}

	public List<String> getSpamWords() {
		return spamWords;
	}

	public void setSpamWords(List<String> spamWords) {
		this.spamWords = spamWords;
	}

	public Integer getVat() {
		return vat;
	}

	public void setVat(Integer vat) {
		this.vat = vat;
	}

	public String getPhonePrefix() {
		return phonePrefix;
	}

	public void setPhonePrefix(String phonePrefix) {
		this.phonePrefix = phonePrefix;
	}

	public List<String> getCreditCardPrefix() {
		return creditCardPrefix;
	}

	public void setCreditCardPrefix(List<String> creditCardPrefix) {
		this.creditCardPrefix = creditCardPrefix;
	}
	
	public final static double getFinderTimerMins(){
		return 60.0;
	}

	public static final Integer getFinderCacheMaxResults(){
		return 20;
	}
	
	
	public static final List<String> staticgetSpamWords(){
		List<String> res = new ArrayList<String>();

		
		res.add("sex");
		res.add("viagra");
		res.add("cialis");
		res.add("one million");
		res.add("you've been selected");
		res.add("Nigeria");
		res.add("sexo");
		res.add("un millon");
		res.add("ha sido seleccionado");
		
		return res;
	}
	
	//Añadir el resto de cosas
	
}
