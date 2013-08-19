package it.univaq.mwt.j2ee.kmZero.common;

import java.util.Comparator;

import it.univaq.mwt.j2ee.kmZero.business.model.Image;
import it.univaq.mwt.j2ee.kmZero.business.model.Product;

public class Comparators {
	
	private Comparator<Image> imagePositionComparator;
	private Comparator<Product> productPositionComparator;
	
	public Comparators(){}

	public Comparator<Image> getImagePositionComparator() {
		
		imagePositionComparator = new Comparator<Image>() {

			@Override
			public int compare(Image o1, Image o2) {	
				return o1.getPosition() - o2.getPosition();
			}
		};		
		return imagePositionComparator;
	}
	
	public Comparator<Product> getProductPositionComparator() {
		
		productPositionComparator = new Comparator<Product>() {

			@Override
			public int compare(Product o1, Product o2) {	
				return o1.getPosition() - o2.getPosition();
			}
		};		
		return productPositionComparator;
	}

}
