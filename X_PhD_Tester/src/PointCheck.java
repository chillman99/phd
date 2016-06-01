public class PointCheck implements Comparable<PointCheck>{
	
        private double wpmA;
        private double smoothA;
        private int chargeA;
        private int newMountainIDA;
        private double normA;
        private double wpmB;
        private double smoothB;
        private int chargeB;
        private int newMountainIDB;
        private double normB;
        
        public PointCheck (double wpmA, double smoothA, int chargeA, int newMountainIDA, double normA,
        		double wpmB, double smoothB, int chargeB, int newMountainIDB, double normB) {
			super();
			this.wpmA = wpmA;
			this.smoothA = smoothA;
			this.chargeA = chargeA;
			this.newMountainIDA = newMountainIDA;
			this.normA = normA;
			this.wpmB = wpmB;
			this.smoothB = smoothB;
			this.chargeB = chargeB;
			this.newMountainIDB = newMountainIDB;
			this.normB = normB;
		}       
	
		public double getWpmA() {
			return wpmA;
		}
		
		public void setWpmA(double wpmA) {
			this.wpmA = wpmA;
		}
	
		public double getSmoothA() {
			return smoothA;
		}
		
		public void setSmoothA(double smoothA) {
			this.smoothA = smoothA;
		}
		public int getChargeA() {
			return chargeA;
		}
		
		public void setChargeA(int chargeA) {
			this.chargeA = chargeA;
		}
		public int getNewMountainIDA() {
			return newMountainIDA;
		}
		
		public void setNewMountainIDA(int newMountainIDA) {
			this.newMountainIDA = newMountainIDA;
		}
		
		public double getNormA() {
			return normA;
		}
		
		public void setNormA(double normA) {
			this.normA = normA;
		}
		
		public double getWpmB() {
			return wpmB;
		}
		
		public void setWpmB(double wpmB) {
			this.wpmB = wpmB;
		}
	
		public double getSmoothB() {
			return smoothB;
		}
		
		public void setSmoothB(double smoothB) {
			this.smoothB = smoothB;
		}
		public int getChargeB() {
			return chargeB;
		}
		
		public void setChargeB(int chargeB) {
			this.chargeB = chargeB;
		}
		public int getNewMountainIDB() {
			return newMountainIDB;
		}
		
		public void setNewMountainIDB(int newMountainIDB) {
			this.newMountainIDB = newMountainIDB;
		}
		
		public double getNormB() {
			return normB;
		}
		
		public void setNormB(double normB) {
			this.normB = normB;
		}
		
		//Custom Compare function to allow array sorting on Mass
		public int compareTo(PointCheck compareCheckPoint) {
		return Double.compare(this.getWpmA(), compareCheckPoint.getWpmA());
		}	
		
}

 		
