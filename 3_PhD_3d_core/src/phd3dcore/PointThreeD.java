package phd3dcore;
import java.util.Comparator;

public class PointThreeD {
	
	        private int curveID;
	        private double wpm;
	        private double sumI;
	        private double wpRT;
	        private double minRT;
	        private double maxRT;
	        private int charge;
	        private int numPeaks;
	        private double weightedSD;
	        private double maxI;
	        
	        public PointThreeD (int curveID, double wpm, double sumI, double wpRT, double minRT,  double maxRT, int charge, int numPeaks, double weightedSD, double maxI) {
				super();
				this.curveID = curveID;
				this.wpm = wpm;
				this.sumI = sumI;
				this.wpRT = wpRT;
				this.minRT = minRT;
				this.maxRT = maxRT;
				this.charge = charge;
				this.numPeaks = numPeaks;
				this.weightedSD = weightedSD;
				this.maxI = maxI;
			}       
		
			public int getCurveID() {
				return curveID;
			}
			
			public void setCurveID(int curveID) {
				this.curveID = curveID;
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
			
			public double getWpRT() {
				return wpRT;
			}
			
			public void setWpRT(double wpRT) {
				this.wpRT = wpRT;
			}
			
			public double getMinRT() {
				return minRT;
			}
			
			public void setMinRT(double minRT) {
				this.minRT = minRT;
			}
		
			public double getMaxRT() {
				return maxRT;
			}
			
			public void setMaxRT(double maxRT) {
				this.maxRT = maxRT;
			}
			
			public int getCharge() {
				return charge;
			}
			
			public void setCharge(int charge) {
				this.charge = charge;
			}
			
			public int getNumPeaks() {
				return numPeaks;
			}
			
			public void setNumPeaks(int numPeaks) {
				this.numPeaks = numPeaks;
			}
			
			public double getWeightedSD() {
				return weightedSD;
			}
			
			public void setWeightedSD(double weightedSD) {
				this.weightedSD = weightedSD;
			}
			
			public double getMaxI() {
				return maxI;
			}
			
			public void setMaxI(double maxI) {
				this.maxI = maxI;
			}
			
			//Customer Compare function to allow array sorting on Mass
			public int compareTo(PointMountain compareWeightedPoint) {
			return Double.compare(this.getWpm(), compareWeightedPoint.getWpm());
			}	
			
	}

//comparator class to sort on curveID
class ThreeDPointCompare implements Comparator<PointThreeD>{
	
//comparator class to sort on charge, followed by retention time followed by WPM
 	public int compare(PointThreeD mp1, PointThreeD mp2) { 			
		int value1 = Double.compare(mp1.getCharge(), mp2.getCharge());
		if(value1 == 0){
			int value2 = Double.compare(mp1.getWpRT(), mp2.getWpRT());
			if (value2 == 0){
				int value3 = Double.compare(mp1.getWpm(), mp2.getWpm());
				return value3;
			}
			return value2;			
		}
		return value1;				
 	}
 	
}
