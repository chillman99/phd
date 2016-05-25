package aster3dpeaks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.asterdata.ncluster.sqlmr.OutputInfo;
import com.asterdata.ncluster.sqlmr.PartitionFunction;
import com.asterdata.ncluster.sqlmr.RuntimeContract;
import com.asterdata.ncluster.sqlmr.data.ColumnDefinition;
import com.asterdata.ncluster.sqlmr.data.PartitionDefinition;
import com.asterdata.ncluster.sqlmr.data.RowEmitter;
import com.asterdata.ncluster.sqlmr.data.RowIterator;
import com.asterdata.ncluster.sqlmr.data.SqlType;

import phd3dcore.*;

public class PeaksPick3d implements PartitionFunction {
	public static String inputFile = new String();
	
	public PeaksPick3d(RuntimeContract contract) {

		List<SqlType> expectedInputTypes = new ArrayList<SqlType>();
		expectedInputTypes.add(SqlType.getType("integer"));			//pkey
		expectedInputTypes.add(SqlType.getType("integer"));			//scan
		expectedInputTypes.add(SqlType.getType("integer"));			//mslvl
		expectedInputTypes.add(SqlType.getType("double precision"));//rettime
		expectedInputTypes.add(SqlType.getType("integer"));			//id
		expectedInputTypes.add(SqlType.getType("double precision"));//wpm
		expectedInputTypes.add(SqlType.getType("double precision"));//sumi
		expectedInputTypes.add(SqlType.getType("integer"));			//charge
		
		List<SqlType> actualInputTypes = new ArrayList<SqlType>();

		for (ColumnDefinition d : contract.getInputInfo().getColumns())
			actualInputTypes.add(d.getColumnType());

		List<ColumnDefinition> outputColumns = new ArrayList<ColumnDefinition>();
			
		outputColumns.add(new ColumnDefinition("charge", SqlType.getType("Integer")));		
		outputColumns.add(new ColumnDefinition("mass", SqlType.getType("double precision")));		
		outputColumns.add(new ColumnDefinition("intensity", SqlType.getType("double precision")));
		outputColumns.add(new ColumnDefinition("rt", SqlType.getType("double precision")));
	
		
		//outputColumns.add(new ColumnDefinition("maxrt", SqlType.getType("double precision")));
		//outputColumns.add(new ColumnDefinition("minrt", SqlType.getType("double precision")));
		
		contract.setOutputInfo(new OutputInfo(outputColumns));
		contract.complete();
	}
	
	public void operateOnPartition(PartitionDefinition partitionDefinition, RowIterator inputIterator, RowEmitter outputEmitter)
	{
		ArrayList<PointMountain> mountainPoints = new ArrayList<PointMountain>();		    
	    	 
	   	//Cache Pp3dr values in an array for processing - must fit in memory, depends on number of Pp3drs
		//Read all the weighted points into an in-memory object array
		int pkey = 0;
		int count = 0;
		while ( inputIterator.advanceToNextRow() ){
			    
	   		    mountainPoints.add (
		    		new PointMountain(	
		    		inputIterator.getDoubleAt(5),
		    		inputIterator.getDoubleAt(6),
		    		inputIterator.getDoubleAt(6),
		    		inputIterator.getDoubleAt(3),
		    		inputIterator.getIntAt(1),
					0,
					inputIterator.getIntAt(7),
					-1,1,0,0));
	   		    if (count>0){
	   		    	if (pkey !=  inputIterator.getIntAt(0) ){
	   		    		pkey = -1;
	   		    	}
	   		    	else pkey = inputIterator.getIntAt(0);
	   		    } else pkey = inputIterator.getIntAt(0);
	   		    count++;
		}
        
		if (mountainPoints.size() > 5) {
			//Now we can sort on by Retention Time followed by WPM using a custom compare from MountainPoint   		   
		   	Collections.sort(mountainPoints, new MountainPointCompare());		  		   
		   	//Create Arraylist of 3D peaks
			//Instantiate Reduce3Dpeaks object and run the method
		   	Pp3d3DPeaks run3dPeaks = new Pp3d3DPeaks();			
		   	ArrayList<PointMountain> outputPoints = run3dPeaks.ThreeDPeaks(mountainPoints);
		   	
		   	//Smooth Intensities of the 3D peaks
		   	Pp3dSmoothIntesity.smoothIntensity(outputPoints);	
			//Recalculate 3D peaks taking into account Overlapping Peaks		
		   	Pp3dOverlaps.calcOverlaps(outputPoints);   	
		   	//Create final 3D peaks and set Mountain ID
		   	ArrayList<PointThreeD> threedDpoints = Pp3dFinalPeaks.calcMountains(outputPoints);	  
			//ISO Peaks
		   	ArrayList<PointISO> MonoISO = Pp3dISOPeaks.calcISO(threedDpoints, outputPoints);
	
		
//		   	//Write out Final Peaks

		   for (int k = 0; k<MonoISO.size();k++){
				outputEmitter.addInt(MonoISO.get(k).getCharge());
				outputEmitter.addDouble(MonoISO.get(k).getWpm());
				outputEmitter.addDouble(MonoISO.get(k).getSumI());
				outputEmitter.addDouble(MonoISO.get(k).getWpRT());
				outputEmitter.emitRow();
			}

		   //	for (int k = 0; k<threedDpoints.size();k++){
		   //
		   //		outputEmitter.addInt(threedDpoints.get(k).getCharge()); 
		   //		outputEmitter.addDouble(threedDpoints.get(k).getWpm()); 
		   //		outputEmitter.addDouble(threedDpoints.get(k).getSumI());
		   //		outputEmitter.addDouble(threedDpoints.get(k).getWpRT()); 
		   //		outputEmitter.addDouble(threedDpoints.get(k).getMaxRT());
		   //		outputEmitter.addDouble(threedDpoints.get(k).getMinRT());
		   //	outputEmitter.emitRow();
		   //	}		 
		
		}
	
	}
	
}	
