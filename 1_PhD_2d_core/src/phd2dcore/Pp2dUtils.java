package phd2dcore;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.commons.codec.binary.Base64;

public class Pp2dUtils {
	//Convert a Base64 encoded 32 bit precision binary string to a Float Array
	public static float[] get32BitStringToFloat(String StringArray) throws Exception {
		byte[] binArray = Base64.decodeBase64(StringArray);
	    ByteBuffer bb = ByteBuffer.wrap(binArray);
	    bb.order(ByteOrder.LITTLE_ENDIAN);
	    	    
	    float[] floatArray = new float[(int)binArray.length/4];
	    floatArray[0]= bb.getFloat(0);
	    for (int index = 1; index <floatArray.length; index ++) {
	    		floatArray[index]=(float)bb.getFloat(index*4);
	    	}
	   return floatArray;
	}
	//Convert a Base64 encoded 64 bit precision binary string to a Float Array
	public static float[] get64BitStringToFloat(String StringArray) throws Exception {
		byte[] binArray = Base64.decodeBase64(StringArray);
	    ByteBuffer bb = ByteBuffer.wrap(binArray);
	    bb.order(ByteOrder.LITTLE_ENDIAN);
	    
	    float[] floatArray = new float[(int)binArray.length/8];
	    floatArray[0]=(float)bb.getDouble(0);
	    for (int index = 1; index <floatArray.length; index ++) {
	    		floatArray[index]=(float)bb.getDouble(index*8);
	    	}
	   return floatArray;
	}			
}
