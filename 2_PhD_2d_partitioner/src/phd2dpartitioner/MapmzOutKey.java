package phd2dpartitioner;

public class MapmzOutKey {

	public static String[] getKey(double wpm) {
	int outKey1 = 0;
	int outKey2 = -1;
	
	if( (wpm >0) && (wpm <= 359.127616740354 )){outKey1 = 0;}
	else if( (wpm >359.127616740354) && (wpm <= 388.548595653319 )){outKey1 = 2;}
	else if( (wpm >388.548829787821) && (wpm <= 421.874504636373 )){outKey1 = 4;}
	else if( (wpm >421.874602265293) && (wpm <= 454.868317356975 )){outKey1 = 6;}
	else if( (wpm >454.868531991324) && (wpm <= 489.884657831247 )){outKey1 = 8;}
	else if( (wpm >489.885150484342) && (wpm <= 528.022282388061 )){outKey1 = 10;}
	else if( (wpm >528.022754696937) && (wpm <= 566.840021133553 )){outKey1 = 12;}
	else if( (wpm >566.840087104684) && (wpm <= 607.718931408507 )){outKey1 = 14;}
	else if( (wpm >607.722170552694) && (wpm <= 650.26669367176 )){outKey1 = 16;}
	else if( (wpm >650.266976246323) && (wpm <= 687.399608589181 )){outKey1 = 18;}
	else if( (wpm >687.399631924531) && (wpm <= 725.568715333237 )){outKey1 = 20;}
	else if( (wpm >725.568816299109) && (wpm <= 762.375692391261 )){outKey1 = 22;}
	else if( (wpm >762.37573323488) && (wpm <= 798.160520298278 )){outKey1 = 24;}
	else if( (wpm >798.160543990307) && (wpm <= 835.650609352819 )){outKey1 = 26;}
	else if( (wpm >835.650927919743) && (wpm <= 878.929622251696 )){outKey1 = 28;}
	else if( (wpm >878.929696500751) && (wpm <= 920.457583301648 )){outKey1 = 30;}
	else if( (wpm >920.457627164107) && (wpm <= 968.134217124589 )){outKey1 = 32;}
	else if( (wpm >968.13426637484) && (wpm <= 1028.25052879222 )){outKey1 = 34;}
	else if( (wpm >1028.25131802593) && (wpm <= 1113.10127103992 )){outKey1 = 36;}
	else if( (wpm >1113.10128083944) && (wpm <= 1247.29458922817 )){outKey1 = 38;}
	else if( (wpm >1247.29552514549) && (wpm <= 1754.05192508543 )){outKey1 = 40;}
	
	if( (wpm >351.710864622068) && (wpm <= 373.218467289394 )){outKey2 = 1;}
	else if( (wpm >373.218470378698) && (wpm <= 405.872739183259 )){outKey2 = 3;}
	else if( (wpm >405.873013495733) && (wpm <= 437.662352987276 )){outKey2 = 5;}
	else if( (wpm >437.667460239514) && (wpm <= 472.413466297723 )){outKey2 = 7;}
	else if( (wpm >472.415217174433) && (wpm <= 508.420154427563 )){outKey2 = 9;}
	else if( (wpm >508.420838029546) && (wpm <= 547.549901471384 )){outKey2 = 11;}
	else if( (wpm >547.549995980255) && (wpm <= 586.598073269579 )){outKey2 = 13;}
	else if( (wpm >586.59835712232) && (wpm <= 629.954659981203 )){outKey2 = 15;}
	else if( (wpm >629.955093740532) && (wpm <= 668.526523011354 )){outKey2 = 17;}
	else if( (wpm >668.526785936263) && (wpm <= 706.489593154938 )){outKey2 = 19;}
	else if( (wpm >706.489817651148) && (wpm <= 744.727315591535 )){outKey2 = 21;}
	else if( (wpm >744.72735368123) && (wpm <= 779.427364880545 )){outKey2 = 23;}
	else if( (wpm >779.427389950918) && (wpm <= 816.382558979535 )){outKey2 = 25;}
	else if( (wpm >816.382637132622) && (wpm <= 857.175750258683 )){outKey2 = 27;}
	else if( (wpm >857.175957215565) && (wpm <= 899.934228067109 )){outKey2 = 29;}
	else if( (wpm >899.934435369284) && (wpm <= 943.225286094443 )){outKey2 = 31;}
	else if( (wpm >943.22539938152) && (wpm <= 995.386782326206 )){outKey2 = 33;}
	else if( (wpm >995.387585193738) && (wpm <= 1067.83504733831 )){outKey2 = 35;}
	else if( (wpm >1067.83531226384) && (wpm <= 1167.34257338722 )){outKey2 = 37;}
	else if( (wpm >1167.34289003893) && (wpm <= 1502.55586957494 )){outKey2 = 39;}
	else if( (wpm >1502.56422629266 )){outKey2 = 41;}
	
	String[] returnKeys = new String[2];
	returnKeys[0] = Integer.toString(outKey1);
	returnKeys[1] = Integer.toString(outKey2);
	return returnKeys;
	
	}
	
}
