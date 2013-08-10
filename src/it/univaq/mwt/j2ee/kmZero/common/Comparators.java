package it.univaq.mwt.j2ee.kmZero.common;

import java.util.Comparator;

import it.univaq.mwt.j2ee.kmZero.business.model.Image;

public class Comparators {
	
	private Comparator<Image> imagePositionComparator;
	
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

}
