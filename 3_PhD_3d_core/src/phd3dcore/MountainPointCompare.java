package phd3dcore;

import java.util.Comparator;

//comparator class to sort on MountainID, followed by charge, followed by retention time followed by WPM
public class MountainPointCompare implements Comparator<PointMountain>{
	
 		public int compare(PointMountain mp1, PointMountain mp2) { 			
			int value1 = Double.compare(mp1.getMountainID(), mp2.getMountainID());
			if(value1 == 0){
				int value2 = Double.compare(mp1.getCharge(), mp2.getCharge());
				if (value2 == 0){
					int value3 = Double.compare(mp1.getRetentionTime(), mp2.getRetentionTime());
					if (value3 ==0 ){
						int value4 = Double.compare(mp1.getWpm(), mp2.getWpm());
						return value4;
					}
					return value3;
				}
				return value2;			
			}
			return value1;
 		}
 		
}