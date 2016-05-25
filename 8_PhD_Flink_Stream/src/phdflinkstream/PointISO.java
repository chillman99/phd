package phdflinkstream;

public class PointISO {
	
    @Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + charge;
	long temp;
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
	PointISO other = (PointISO) obj;
	if (charge != other.charge)
		return false;
	if (Double.doubleToLongBits(sumI) != Double
			.doubleToLongBits(other.sumI))
		return false;
	if (Double.doubleToLongBits(wpm) != Double.doubleToLongBits(other.wpm))
		return false;
	return true;
}

	        private int envelopeID;
	        private int levelID;
	        private int charge;
	        private double wpm;
	        private double sumI;
	        private double wpRT;	
	        
	        public PointISO (int envelopeID, int levelID, int charge,  double wpm, double sumI, double wpRT) {
				super();
				this.envelopeID = envelopeID;
				this.levelID = levelID;
				this.charge = charge;
				this.wpm = wpm;
				this.sumI = sumI;
				this.wpRT = wpRT;
			}       
		
			public int getEnvelopeID() {
				return envelopeID;
			}
			
			public void setEnvelopeID(int envelopeID) {
				this.envelopeID = envelopeID;
			}
			
			public int getLevelID() {
				return levelID;
			}
			
			public void setLevelID(int levelID) {
				this.levelID = levelID;
			}
			
			public int getCharge() {
				return charge;
			}
			
			public void setCharge(int charge) {
				this.charge = charge;
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
			
	}

