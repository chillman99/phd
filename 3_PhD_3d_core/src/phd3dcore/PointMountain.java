package phd3dcore;

import java.util.Comparator;

public class PointMountain implements Comparable<PointMountain>{
        @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + charge;
		long temp;
		temp = Double.doubleToLongBits(retentionTime);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(sumI);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(wpm);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PointMountain other = (PointMountain) obj;
		if (charge != other.charge)
			return false;
		if (Double.doubleToLongBits(retentionTime) != Double
				.doubleToLongBits(other.retentionTime))
			return false;
		if (Double.doubleToLongBits(sumI) != Double
				.doubleToLongBits(other.sumI))
			return false;
		if (Double.doubleToLongBits(wpm) != Double.doubleToLongBits(other.wpm))
			return false;
		return true;
	}

	
	public boolean matches(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PointMountain other = (PointMountain) obj;
		if (charge != other.charge)
			return false;
		//Check mass inside the 7 ppm window	
		if (this.charge * this.wpm >= ((other.wpm * other.charge) - (other.wpm * other.charge * 0.000007))  
			&  (this.charge * this.wpm <= (other.wpm * other.charge) + (other.wpm * other.charge * 0.000007))
			//Added 01/06/2017
			&  (this.retentionTime != other.retentionTime)
					) return true; 		
			return false;
		}

		private double wpm;
        private double sumI;
        private double smoothI;
        private double retentionTime;
        private int scanNumber;
        private int scanOrder;
        private int charge;
        private int mountainID;
        private int newMountainID;
        private double normI;
        private int checked;
        private int boundary;
        
        public PointMountain (	double wpm, double sumI, double smoothI, double retentionTime, int scanNumber, 
        						int scanOrder, int charge, int mountainID, int newMountainID, double normI,
        						int checked, int boundary) {
			super();
			this.wpm = wpm;
			this.sumI = sumI;
			this.smoothI = smoothI;
			this.retentionTime = retentionTime;
			this.scanNumber = scanNumber;
			this.scanOrder = scanOrder;
			this.charge = charge;
			this.mountainID = mountainID;
			this.newMountainID = newMountainID;
			this.normI = normI;
			this.checked = checked;
			this.boundary = boundary;
		}       
	
		public double getMass() {
			return wpm*charge;
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
		
		public double getSmoothI() {
			return smoothI;
		}
		
		public void setSmoothI(double smoothI) {
			this.smoothI = smoothI;
		}
		
		public double getRetentionTime() {
			return retentionTime;
		}
		
		public void setRetentionTime(double retentionTime) {
			this.retentionTime = retentionTime;
		}
	
		public int getScanNumber() {
			return scanNumber;
		}
		
		public void setScanNumber(int scanNumber) {
			this.scanNumber = scanNumber;
		}
		
		public int getScanOrder() {
			return scanOrder;
		}
		
		public void setScanOrder(int scanOrder) {
			this.scanOrder = scanOrder;
		}
		
		public int getCharge() {
			return charge;
		}
		
		public void setCharge(int charge) {
			this.charge = charge;
		}
	
		public int getMountainID() {
			return mountainID;
		}
		
		public void setMountainID(int mountainID) {
			this.mountainID = mountainID;
		}
		
		public int getNewMountainID() {
			return newMountainID;
		}
		
		public void setNewMountainID(int newMountainID) {
			this.newMountainID = newMountainID;
		}
		
		
		public double getNormI() {
			return normI;
		}
		
		public void setNormI(double normI) {
			this.normI = normI;
		}
		
		public int getChecked() {
			return checked;
		}
		
		public void setChecked(int checked) {
			this.checked = checked;
		}
		
		public int getBoundary() {
			return boundary;
		}
		
		public void setBoundary(int boundary) {
			this.boundary = boundary;
		}
		
		//Custom Compare function to allow array sorting on Mass
		public int compareTo(PointMountain compareWeightedPoint) {
		return Double.compare(this.getWpm(), compareWeightedPoint.getWpm());
		}	
		
}

 