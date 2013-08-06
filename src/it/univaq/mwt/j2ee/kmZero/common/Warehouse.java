package it.univaq.mwt.j2ee.kmZero.common;

public class Warehouse {
	
	/*private static String coordinates = "42.360542, 14.201263";

	public static String getCoordinates() {
		return coordinates;
	}

	public static void setCoordinates(String coordinates) {
		Warehouse.coordinates = coordinates;
	}*/
	

	private static float latitude = 42.360542f;
	private static float longitude = 14.201263f;
	
	public static float getLatitude() {
		return latitude;
	}
	public static void setLatitude(float latitude) {
		Warehouse.latitude = latitude;
	}
	public static float getLongitude() {
		return longitude;
	}
	public static void setLongitude(float longitude) {
		Warehouse.longitude = longitude;
	}
	
	

}
