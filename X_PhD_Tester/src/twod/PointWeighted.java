package twod;

import java.util.Comparator;

public class PointWeighted implements Comparable<PointWeighted>{
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PointWeighted other = (PointWeighted) obj;
		if (charge != other.charge)
			return false;
		if (Double.doubleToLongBits(sumI) != Double.doubleToLongBits(other.sumI))
			return false;
		if (Double.doubleToLongBits(wpm) != Double.doubleToLongBits(other.wpm))
			return false;
		//Removed CurveID from equals to deal with duplicates
		//if (curveID != other.curveID)
		//	return false;
		return true;
				
	}

        private int curveID;
        private double wpm;
        private double sumI;
        private double maxI;
        private int charge;
        private int outKey;
        private int outKey2;
        
        public PointWeighted (int curveID, double wpm, double sumI, double maxI, int charge, int outKey, int outKey2) {
			super();
			this.curveID = curveID;
			this.wpm = wpm;
			this.sumI = sumI;
			this.maxI = maxI;
			this.charge = charge;
			this.outKey = outKey;
			this.outKey2 = outKey2;
		}
       
		public int getCurveID() {
			return curveID;
		}
		
		public void setCurveID(int CurveID) {
			this.curveID = CurveID;
		}
	
		public double getWpm() {
			return wpm;
		}
		
		public void setWpm(double wpm) {
			this.wpm = wpm;
		}
	
		public double getSumI() {
			return sumI;
		}
		
		public void setSumI(double sumI) {
			this.sumI = sumI;
		}
		
		public double getMaxI() {
			return maxI;
		}
		
		public void setMaxI(double maxI) {
			this.maxI = maxI;
		}
	
		public int getCharge() {
			return charge;
		}
		
		public void setCharge(int charge) {
			this.charge = charge;
		}
	
		public int getoutKey() {
			return outKey;
		}
		
		public void setoutKey(int outKey) {
			this.outKey = outKey;
		}
		
		public int getoutKey2() {
			return outKey2;
		}
		
		public void setoutKey2(int outKey2) {
			this.outKey2 = outKey2;
		}
		
		//Customer Compare function to allow array sorting on WPM
		public int compareTo(PointWeighted compareWeightedPoint) {
 
			return Double.compare(this.getWpm(), compareWeightedPoint.getWpm());
 
	}	
       
}

//comparator class to sort on MountainID, followed by charge, followed by retention time followed by WPM
class WeightedPointCompare implements Comparator<PointWeighted>{
		public int compare(PointWeighted mp1, PointWeighted mp2) { 			
		int value1 = Double.compare(mp1.getCurveID(), mp2.getCurveID());
		if(value1 == 0){
			int value2 = Double.compare(mp1.getCharge(), mp2.getCharge());
			if (value2 == 0){
				int value3 = Double.compare(mp1.getWpm(), mp2.getWpm());
				if (value3 ==0 ){
					int value4 = Double.compare(mp1.getSumI(), mp2.getSumI());
					return value4;
				}
				return value3;
			}
			return value2;			
		}
		return value1;
		}
		
		//public int compare(PointWeighted mp1, PointWeighted mp2) { 			
		//	int value1 = Double.compare(mp1.getWpm(), mp2.getWpm());
		//	return value1;
		//}
		
}