package phd2dpartitioner;

public class MapmzOutKey {

	public static String[] getKey(double wpm) {
	int outKey = 0;
	int outKey2 = 0;	

	if( (wpm >	350.001409048478) && (wpm <= 363.874374521282 )){outKey = 0;}
	else if( (wpm >	363.874380955016) && (wpm <= 373.097892539298 )){outKey = 1;}
	else if( (wpm >	373.097892634861) && (wpm <= 381.863343916069 )){outKey = 2;}
	else if( (wpm >	381.863578726329) && (wpm <= 391.447189187219 )){outKey = 3;}
	else if( (wpm >	391.447292443365) && (wpm <= 401.557600503796 )){outKey = 4;}
	else if( (wpm >	401.557843817841) && (wpm <= 411.240248964784 )){outKey = 5;}
	else if( (wpm >	411.240413299315) && (wpm <= 420.467425070127 )){outKey = 6;}
	else if( (wpm >	420.467436686836) && (wpm <= 428.890965824743 )){outKey = 7;}
	else if( (wpm >	428.890966176282) && (wpm <= 434.832355828981 )){outKey = 8;}
	else if( (wpm >	434.832361045841) && (wpm <= 443.990637490612 )){outKey = 9;}
	else if( (wpm >	443.990642832401) && (wpm <= 452.047026926839 )){outKey = 10;}
	else if( (wpm >	452.047152899943) && (wpm <= 461.037000447722 )){outKey = 11;}
	else if( (wpm >	461.037041180462) && (wpm <= 469.996025447137 )){outKey = 12;}
	else if( (wpm >	469.996090227483) && (wpm <= 480.004134841523 )){outKey = 13;}
	else if( (wpm >	480.004139756728) && (wpm <= 489.105633978623 )){outKey = 14;}
	else if( (wpm >	489.105784907443) && (wpm <= 499.262190182883 )){outKey = 15;}
	else if( (wpm >	499.262192174619) && (wpm <= 509.061759913115 )){outKey = 16;}
	else if( (wpm >	509.061782802754) && (wpm <= 519.549907056383 )){outKey = 17;}
	else if( (wpm >	519.550039729299) && (wpm <= 528.276480485803 )){outKey = 18;}
	else if( (wpm >	528.276534128915) && (wpm <= 537.797056950555 )){outKey = 19;}
	else if( (wpm >	537.797059062192) && (wpm <= 548.07821146884 )){outKey = 20;}
	else if( (wpm >	548.078214542076) && (wpm <= 559.538521582005 )){outKey = 21;}
	else if( (wpm >	559.538953739441) && (wpm <= 571.29012327528 )){outKey = 22;}
	else if( (wpm >	571.290126216271) && (wpm <= 581.960070696646 )){outKey = 23;}
	else if( (wpm >	581.960117284894) && (wpm <= 594.314870523572 )){outKey = 24;}
	else if( (wpm >	594.314894169354) && (wpm <= 606.111496346689 )){outKey = 25;}
	else if( (wpm >	606.111516744575) && (wpm <= 616.814516034407 )){outKey = 26;}
	else if( (wpm >	616.814535510904) && (wpm <= 630.807591449048 )){outKey = 27;}
	else if( (wpm >	630.807697941548) && (wpm <= 642.33468265098 )){outKey = 28;}
	else if( (wpm >	642.334718051392) && (wpm <= 657.097226969508 )){outKey = 29;}
	else if( (wpm >	657.098879420764) && (wpm <= 669.17810402705 )){outKey = 30;}
	else if( (wpm >	669.178104429872) && (wpm <= 683.31857885286 )){outKey = 31;}
	else if( (wpm >	683.319033921934) && (wpm <= 698.174964020303 )){outKey = 32;}
	else if( (wpm >	698.175074692151) && (wpm <= 715.601630726113 )){outKey = 33;}
	else if( (wpm >	715.601655834173) && (wpm <= 732.880952679688 )){outKey = 34;}
	else if( (wpm >	732.881260875752) && (wpm <= 753.404296793891 )){outKey = 35;}
	else if( (wpm >	753.404507811071) && (wpm <= 784.788305758586 )){outKey = 36;}
	else if( (wpm >	784.788307557314) && (wpm <= 813.415480342533 )){outKey = 37;}
	else if( (wpm >	813.415520285834) && (wpm <= 847.7793064019 )){outKey = 38;}
	else if( (wpm >	847.781863537524) && (wpm <= 898.809905933577 )){outKey = 39;}
	else if( (wpm >	898.810068094704) && (wpm <= 968.090632558067 )){outKey = 40;}
	else if( (wpm >	968.090678747018) && (wpm <= 1582.83802468534 )){outKey = 41;}

	//Overlap
	if( (wpm >	363.8743745	) && (wpm <= 	368.4861303	 )){outKey2 = 	0	;}
	else if( (wpm >	373.0978925	) && (wpm <= 	377.4806182	 )){outKey2 = 	1	;}
	else if( (wpm >	381.8633439	) && (wpm <= 	386.6551491	 )){outKey2 = 	2	;}
	else if( (wpm >	391.4471892	) && (wpm <= 	396.5023432	 )){outKey2 = 	3	;}
	else if( (wpm >	401.5576005	) && (wpm <= 	406.3988031	 )){outKey2 = 	4	;}
	else if( (wpm >	411.240249	) && (wpm <= 	415.8537549	 )){outKey2 = 	5	;}
	else if( (wpm >	420.4674251	) && (wpm <= 	424.6791896	 )){outKey2 = 	6	;}
	else if( (wpm >	428.8909658	) && (wpm <= 	431.8616607	 )){outKey2 = 	7	;}
	else if( (wpm >	434.8323558	) && (wpm <= 	439.4114941	 )){outKey2 = 	8	;}
	else if( (wpm >	443.9906375	) && (wpm <= 	448.0188295	 )){outKey2 = 	9	;}
	else if( (wpm >	452.0470269	) && (wpm <= 	456.5419507	 )){outKey2 = 	10	;}
	else if( (wpm >	461.0370004	) && (wpm <= 	465.5164926	 )){outKey2 = 	11	;}
	else if( (wpm >	469.9960254	) && (wpm <= 	475.0000478	 )){outKey2 = 	12	;}
	else if( (wpm >	480.0041348	) && (wpm <= 	484.554882	 )){outKey2 = 	13	;}
	else if( (wpm >	489.105634	) && (wpm <= 	494.1838366	 )){outKey2 = 	14	;}
	else if( (wpm >	499.2621902	) && (wpm <= 	504.1619741	 )){outKey2 = 	15	;}
	else if( (wpm >	509.0617599	) && (wpm <= 	514.305822	 )){outKey2 = 	16	;}
	else if( (wpm >	519.5499071	) && (wpm <= 	523.9131274	 )){outKey2 = 	17	;}
	else if( (wpm >	528.2764805	) && (wpm <= 	533.0367419	 )){outKey2 = 	18	;}
	else if( (wpm >	537.797057	) && (wpm <= 	542.9376332	 )){outKey2 = 	19	;}
	else if( (wpm >	548.0782115	) && (wpm <= 	553.808365	 )){outKey2 = 	20	;}
	else if( (wpm >	559.5385216	) && (wpm <= 	565.4141063	 )){outKey2 = 	21	;}
	else if( (wpm >	571.2901233	) && (wpm <= 	576.6250955	 )){outKey2 = 	22	;}
	else if( (wpm >	581.9600707	) && (wpm <= 	588.1374473	 )){outKey2 = 	23	;}
	else if( (wpm >	594.3148705	) && (wpm <= 	600.2131716	 )){outKey2 = 	24	;}
	else if( (wpm >	606.1114963	) && (wpm <= 	611.462996	 )){outKey2 = 	25	;}
	else if( (wpm >	616.814516	) && (wpm <= 	623.811044	 )){outKey2 = 	26	;}
	else if( (wpm >	630.8075914	) && (wpm <= 	636.5710838	 )){outKey2 = 	27	;}
	else if( (wpm >	642.3346827	) && (wpm <= 	649.7159371	 )){outKey2 = 	28	;}
	else if( (wpm >	657.097227	) && (wpm <= 	663.1368393	 )){outKey2 = 	29	;}
	else if( (wpm >	669.178104	) && (wpm <= 	676.2483412	 )){outKey2 = 	30	;}
	else if( (wpm >	683.3185789	) && (wpm <= 	690.7465439	 )){outKey2 = 	31	;}
	else if( (wpm >	698.174964	) && (wpm <= 	706.888242	 )){outKey2 = 	32	;}
	else if( (wpm >	715.6016307	) && (wpm <= 	724.2412791	 )){outKey2 = 	33	;}
	else if( (wpm >	732.8809527	) && (wpm <= 	743.1424706	 )){outKey2 = 	34	;}
	else if( (wpm >	753.4042968	) && (wpm <= 	769.0961958	 )){outKey2 = 	35	;}
	else if( (wpm >	784.7883058	) && (wpm <= 	799.1018922	 )){outKey2 = 	36	;}
	else if( (wpm >	813.4154803	) && (wpm <= 	830.5973734	 )){outKey2 = 	37	;}
	else if( (wpm >	847.7793064	) && (wpm <= 	873.2933276	 )){outKey2 = 	38	;}
	else if( (wpm >	898.8099059	) && (wpm <= 	933.4501882	 )){outKey2 = 	39	;}
	else if( (wpm >	968.0906326	) && (wpm <= 	1275.464306	 )){outKey2 = 	40	;}
	
	if( (wpm >	356.9378982	) && (wpm <= 	363.874381	 )){outKey2 = 	1	;}
	else if( (wpm >	368.4861368	) && (wpm <= 	373.0978926	 )){outKey2 = 	2	;}
	else if( (wpm >	377.4808531	) && (wpm <= 	381.8635787	 )){outKey2 = 	3	;}
	else if( (wpm >	386.6554872	) && (wpm <= 	391.4472924	 )){outKey2 = 	4	;}
	else if( (wpm >	396.5026898	) && (wpm <= 	401.5578438	 )){outKey2 = 	5	;}
	else if( (wpm >	406.3992107	) && (wpm <= 	411.2404133	 )){outKey2 = 	6	;}
	else if( (wpm >	415.8539308	) && (wpm <= 	420.4674367	 )){outKey2 = 	7	;}
	else if( (wpm >	424.6792016	) && (wpm <= 	428.8909662	 )){outKey2 = 	8	;}
	else if( (wpm >	431.8616662	) && (wpm <= 	434.832361	 )){outKey2 = 	9	;}
	else if( (wpm >	439.4115046	) && (wpm <= 	443.9906428	 )){outKey2 = 	10	;}
	else if( (wpm >	448.0189609	) && (wpm <= 	452.0471529	 )){outKey2 = 	11	;}
	else if( (wpm >	456.5421174	) && (wpm <= 	461.0370412	 )){outKey2 = 	12	;}
	else if( (wpm >	465.5165981	) && (wpm <= 	469.9960902	 )){outKey2 = 	13	;}
	else if( (wpm >	475.0001174	) && (wpm <= 	480.0041398	 )){outKey2 = 	14	;}
	else if( (wpm >	484.5550378	) && (wpm <= 	489.1057849	 )){outKey2 = 	15	;}
	else if( (wpm >	494.1839895	) && (wpm <= 	499.2621922	 )){outKey2 = 	16	;}
	else if( (wpm >	504.1619989	) && (wpm <= 	509.0617828	 )){outKey2 = 	17	;}
	else if( (wpm >	514.3059776	) && (wpm <= 	519.5500397	 )){outKey2 = 	18	;}
	else if( (wpm >	523.9133138	) && (wpm <= 	528.2765341	 )){outKey2 = 	19	;}
	else if( (wpm >	533.0367977	) && (wpm <= 	537.7970591	 )){outKey2 = 	20	;}
	else if( (wpm >	542.9376383	) && (wpm <= 	548.0782145	 )){outKey2 = 	21	;}
	else if( (wpm >	553.8088002	) && (wpm <= 	559.5389537	 )){outKey2 = 	22	;}
	else if( (wpm >	565.4145414	) && (wpm <= 	571.2901262	 )){outKey2 = 	23	;}
	else if( (wpm >	576.625145	) && (wpm <= 	581.9601173	 )){outKey2 = 	24	;}
	else if( (wpm >	588.1375176	) && (wpm <= 	594.3148942	 )){outKey2 = 	25	;}
	else if( (wpm >	600.2132157	) && (wpm <= 	606.1115167	 )){outKey2 = 	26	;}
	else if( (wpm >	611.4630359	) && (wpm <= 	616.8145355	 )){outKey2 = 	27	;}
	else if( (wpm >	623.81117	) && (wpm <= 	630.8076979	 )){outKey2 = 	28	;}
	else if( (wpm >	636.5712257	) && (wpm <= 	642.3347181	 )){outKey2 = 	29	;}
	else if( (wpm >	649.717625	) && (wpm <= 	657.0988794	 )){outKey2 = 	30	;}
	else if( (wpm >	663.1384921	) && (wpm <= 	669.1781044	 )){outKey2 = 	31	;}
	else if( (wpm >	676.2487967	) && (wpm <= 	683.3190339	 )){outKey2 = 	32	;}
	else if( (wpm >	690.7471096	) && (wpm <= 	698.1750747	 )){outKey2 = 	33	;}
	else if( (wpm >	706.8883778	) && (wpm <= 	715.6016558	 )){outKey2 = 	34	;}
	else if( (wpm >	724.2416125	) && (wpm <= 	732.8812609	 )){outKey2 = 	35	;}
	else if( (wpm >	743.1429899	) && (wpm <= 	753.4045078	 )){outKey2 = 	36	;}
	else if( (wpm >	769.0964086	) && (wpm <= 	784.7883076	 )){outKey2 = 	37	;}
	else if( (wpm >	799.1019339	) && (wpm <= 	813.4155203	 )){outKey2 = 	38	;}
	else if( (wpm >	830.5999705	) && (wpm <= 	847.7818635	 )){outKey2 = 	39	;}
	else if( (wpm >	873.2960469	) && (wpm <= 	898.8100681	 )){outKey2 = 	40	;}
	else if( (wpm >	933.4503965	) && (wpm <= 	968.0906787	 )){outKey2 = 	41	;}
	
	String[] returnKeys = new String[2];
	returnKeys[0] = Integer.toString(outKey);
	returnKeys[1] = Integer.toString(outKey2);
	return returnKeys;
	
	}
	
}
