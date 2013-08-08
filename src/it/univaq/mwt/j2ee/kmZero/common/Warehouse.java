package it.univaq.mwt.j2ee.kmZero.common;

public class Warehouse {
	
	private static String address = "Via dei Vestini, 66100 Chieti CH, Italia";
	
	public static String getAddress() {
		return address;
	}
	public static void setAddress(String address) {
		Warehouse.address = address;
	}

}
