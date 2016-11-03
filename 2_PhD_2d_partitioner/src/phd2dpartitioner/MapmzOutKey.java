package phd2dpartitioner;

public class MapmzOutKey {

	public static String[] getKey(double wpm) {
	int outKey = 0;
	int outKey2 = 0;	
	outKey2=-1;		
	
	if( (wpm >	0	) && (wpm <= 	356.0695117	 )){outKey = 	0	;}
	else if( (wpm >	356.0695117	) && (wpm <= 	358.150333	 )){outKey = 	1	;}
	else if( (wpm >	358.1503536	) && (wpm <= 	363.2159724	 )){outKey = 	2	;}
	else if( (wpm >	363.2159739	) && (wpm <= 	369.4350423	 )){outKey = 	3	;}
	else if( (wpm >	369.4350423	) && (wpm <= 	373.1850458	 )){outKey = 	4	;}
	else if( (wpm >	373.185047	) && (wpm <= 	377.2289312	 )){outKey = 	5	;}
	else if( (wpm >	377.2289312	) && (wpm <= 	383.1921859	 )){outKey = 	6	;}
	else if( (wpm >	383.1921891	) && (wpm <= 	387.4992377	 )){outKey = 	7	;}
	else if( (wpm >	387.4992881	) && (wpm <= 	392.4629254	 )){outKey = 	8	;}
	else if( (wpm >	392.4629254	) && (wpm <= 	399.4096499	 )){outKey = 	9	;}
	else if( (wpm >	399.4096499	) && (wpm <= 	404.2184769	 )){outKey = 	10	;}
	else if( (wpm >	404.218481	) && (wpm <= 	408.4310074	 )){outKey = 	11	;}
	else if( (wpm >	408.431075	) && (wpm <= 	413.7818551	 )){outKey = 	12	;}
	else if( (wpm >	413.781874	) && (wpm <= 	418.2083069	 )){outKey = 	13	;}
	else if( (wpm >	418.2083108	) && (wpm <= 	422.4563805	 )){outKey = 	14	;}
	else if( (wpm >	422.4563805	) && (wpm <= 	428.8912899	 )){outKey = 	15	;}
	else if( (wpm >	428.8912899	) && (wpm <= 	431.0676483	 )){outKey = 	16	;}
	else if( (wpm >	431.0676485	) && (wpm <= 	433.046774	 )){outKey = 	17	;}
	else if( (wpm >	433.0467767	) && (wpm <= 	439.2519327	 )){outKey = 	18	;}
	else if( (wpm >	439.2519348	) && (wpm <= 	446.1205831	 )){outKey = 	19	;}
	else if( (wpm >	446.1205831	) && (wpm <= 	449.0968167	 )){outKey = 	20	;}
	else if( (wpm >	449.0968168	) && (wpm <= 	454.7468819	 )){outKey = 	21	;}
	else if( (wpm >	454.7469067	) && (wpm <= 	460.7557108	 )){outKey = 	22	;}
	else if( (wpm >	460.7557108	) && (wpm <= 	465.287012	 )){outKey = 	23	;}
	else if( (wpm >	465.2870177	) && (wpm <= 	473.9857898	 )){outKey = 	24	;}
	else if( (wpm >	473.9857935	) && (wpm <= 	482.5802883	 )){outKey = 	25	;}
	else if( (wpm >	482.5802883	) && (wpm <= 	493.2596906	 )){outKey = 	26	;}
	else if( (wpm >	493.2596906	) && (wpm <= 	504.604976	 )){outKey = 	27	;}
	else if( (wpm >	504.6050014	) && (wpm <= 	516.0187753	 )){outKey = 	28	;}
	else if( (wpm >	516.0187783	) && (wpm <= 	524.2832513	 )){outKey = 	29	;}
	else if( (wpm >	524.2832531	) && (wpm <= 	538.1632214	 )){outKey = 	30	;}
	else if( (wpm >	538.1632215	) && (wpm <= 	551.9362095	 )){outKey = 	31	;}
	else if( (wpm >	551.9362127	) && (wpm <= 	568.0139073	 )){outKey = 	32	;}
	else if( (wpm >	568.0139073	) && (wpm <= 	588.3450272	 )){outKey = 	33	;}
	else if( (wpm >	588.3450272	) && (wpm <= 	608.5769002	 )){outKey = 	34	;}
	else if( (wpm >	608.5792822	) && (wpm <= 	626.1134664	 )){outKey = 	35	;}
	else if( (wpm >	626.1134664	) && (wpm <= 	656.6976313	 )){outKey = 	36	;}
	else if( (wpm >	656.6976361	) && (wpm <= 	680.8323753	 )){outKey = 	37	;}
	else if( (wpm >	680.8323898	) && (wpm <= 	708.3552505	 )){outKey = 	38	;}
	else if( (wpm >	708.3552613	) && (wpm <= 	743.7105268	 )){outKey = 	39	;}
	else if( (wpm >	743.7105886	) && (wpm <= 	784.3751882	 )){outKey = 	40	;}
	else if( (wpm >	784.3752088			 )){outKey = 	41	;}
							
	if( (wpm >	353.0337994	) && (wpm <= 	356.0695117	 )){outKey2 = 	1	;}
	else if( (wpm >	357.1099429	) && (wpm <= 	358.1503536	 )){outKey2 = 	2	;}
	else if( (wpm >	360.6831645	) && (wpm <= 	363.2159739	 )){outKey2 = 	3	;}
	else if( (wpm >	366.3255081	) && (wpm <= 	369.4350423	 )){outKey2 = 	4	;}
	else if( (wpm >	371.3100452	) && (wpm <= 	373.185047	 )){outKey2 = 	5	;}
	else if( (wpm >	375.2069891	) && (wpm <= 	377.2289312	 )){outKey2 = 	6	;}
	else if( (wpm >	380.2105618	) && (wpm <= 	383.1921891	 )){outKey2 = 	7	;}
	else if( (wpm >	385.3457637	) && (wpm <= 	387.4992881	 )){outKey2 = 	8	;}
	else if( (wpm >	389.9811067	) && (wpm <= 	392.4629254	 )){outKey2 = 	9	;}
	else if( (wpm >	395.9362877	) && (wpm <= 	399.4096499	 )){outKey2 = 	10	;}
	else if( (wpm >	401.8140675	) && (wpm <= 	404.218481	 )){outKey2 = 	11	;}
	else if( (wpm >	406.3248118	) && (wpm <= 	408.431075	 )){outKey2 = 	12	;}
	else if( (wpm >	411.106484	) && (wpm <= 	413.781874	 )){outKey2 = 	13	;}
	else if( (wpm >	415.9950944	) && (wpm <= 	418.2083108	 )){outKey2 = 	14	;}
	else if( (wpm >	420.3323456	) && (wpm <= 	422.4563805	 )){outKey2 = 	15	;}
	else if( (wpm >	425.6738352	) && (wpm <= 	428.8912899	 )){outKey2 = 	16	;}
	else if( (wpm >	429.9794693	) && (wpm <= 	431.0676485	 )){outKey2 = 	17	;}
	else if( (wpm >	432.0572139	) && (wpm <= 	433.0467767	 )){outKey2 = 	18	;}
	else if( (wpm >	436.1493568	) && (wpm <= 	439.2519348	 )){outKey2 = 	19	;}
	else if( (wpm >	442.6862589	) && (wpm <= 	446.1205831	 )){outKey2 = 	20	;}
	else if( (wpm >	447.6087	) && (wpm <= 	449.0968168	 )){outKey2 = 	21	;}
	else if( (wpm >	451.9218742	) && (wpm <= 	454.7469067	 )){outKey2 = 	22	;}
	else if( (wpm >	457.7513088	) && (wpm <= 	460.7557108	 )){outKey2 = 	23	;}
	else if( (wpm >	463.0213671	) && (wpm <= 	465.2870177	 )){outKey2 = 	24	;}
	else if( (wpm >	469.6364075	) && (wpm <= 	473.9857935	 )){outKey2 = 	25	;}
	else if( (wpm >	478.2830409	) && (wpm <= 	482.5802883	 )){outKey2 = 	26	;}
	else if( (wpm >	487.9199895	) && (wpm <= 	493.2596906	 )){outKey2 = 	27	;}
	else if( (wpm >	498.9323587	) && (wpm <= 	504.6050014	 )){outKey2 = 	28	;}
	else if( (wpm >	510.3118914	) && (wpm <= 	516.0187783	 )){outKey2 = 	29	;}
	else if( (wpm >	520.1510166	) && (wpm <= 	524.2832531	 )){outKey2 = 	30	;}
	else if( (wpm >	531.2232373	) && (wpm <= 	538.1632215	 )){outKey2 = 	31	;}
	else if( (wpm >	545.0497187	) && (wpm <= 	551.9362127	 )){outKey2 = 	32	;}
	else if( (wpm >	559.97506	) && (wpm <= 	568.0139073	 )){outKey2 = 	33	;}
	else if( (wpm >	578.1794672	) && (wpm <= 	588.3450272	 )){outKey2 = 	34	;}
	else if( (wpm >	598.4633458	) && (wpm <= 	608.5792822	 )){outKey2 = 	35	;}
	else if( (wpm >	617.3463743	) && (wpm <= 	626.1134664	 )){outKey2 = 	36	;}
	else if( (wpm >	641.4055536	) && (wpm <= 	656.6976361	 )){outKey2 = 	37	;}
	else if( (wpm >	668.7650201	) && (wpm <= 	680.8323898	 )){outKey2 = 	38	;}
	else if( (wpm >	694.5938309	) && (wpm <= 	708.3552613	 )){outKey2 = 	39	;}
	else if( (wpm >	726.0329558	) && (wpm <= 	743.7105886	 )){outKey2 = 	40	;}
	else if( (wpm >	764.042909			 )){outKey2 = 	41	;}
							
	if( (wpm >	0	) && (wpm <= 	357.1099223	 )){outKey2 = 	0	;}
	else if( (wpm >	358.150333	) && (wpm <= 	360.6831424	 )){outKey2 = 	1	;}
	else if( (wpm >	363.2159724	) && (wpm <= 	366.3255066	 )){outKey2 = 	2	;}
	else if( (wpm >	369.4350423	) && (wpm <= 	371.3100441	 )){outKey2 = 	3	;}
	else if( (wpm >	373.1850458	) && (wpm <= 	375.206988	 )){outKey2 = 	4	;}
	else if( (wpm >	377.2289312	) && (wpm <= 	380.2105585	 )){outKey2 = 	5	;}
	else if( (wpm >	383.1921859	) && (wpm <= 	385.3457102	 )){outKey2 = 	6	;}
	else if( (wpm >	387.4992377	) && (wpm <= 	389.9810564	 )){outKey2 = 	7	;}
	else if( (wpm >	392.4629254	) && (wpm <= 	395.9362877	 )){outKey2 = 	8	;}
	else if( (wpm >	399.4096499	) && (wpm <= 	401.8140634	 )){outKey2 = 	9	;}
	else if( (wpm >	404.2184769	) && (wpm <= 	406.3247401	 )){outKey2 = 	10	;}
	else if( (wpm >	408.4310074	) && (wpm <= 	411.1063975	 )){outKey2 = 	11	;}
	else if( (wpm >	413.7818551	) && (wpm <= 	415.9950715	 )){outKey2 = 	12	;}
	else if( (wpm >	418.2083069	) && (wpm <= 	420.3323417	 )){outKey2 = 	13	;}
	else if( (wpm >	422.4563805	) && (wpm <= 	425.6738352	 )){outKey2 = 	14	;}
	else if( (wpm >	428.8912899	) && (wpm <= 	429.9794691	 )){outKey2 = 	15	;}
	else if( (wpm >	431.0676483	) && (wpm <= 	432.057211	 )){outKey2 = 	16	;}
	else if( (wpm >	433.046774	) && (wpm <= 	436.149352	 )){outKey2 = 	17	;}
	else if( (wpm >	439.2519327	) && (wpm <= 	442.6862568	 )){outKey2 = 	18	;}
	else if( (wpm >	446.1205831	) && (wpm <= 	447.6086999	 )){outKey2 = 	19	;}
	else if( (wpm >	449.0968167	) && (wpm <= 	451.9218492	 )){outKey2 = 	20	;}
	else if( (wpm >	454.7468819	) && (wpm <= 	457.7512839	 )){outKey2 = 	21	;}
	else if( (wpm >	460.7557108	) && (wpm <= 	463.0213614	 )){outKey2 = 	22	;}
	else if( (wpm >	465.287012	) && (wpm <= 	469.636398	 )){outKey2 = 	23	;}
	else if( (wpm >	473.9857898	) && (wpm <= 	478.2830371	 )){outKey2 = 	24	;}
	else if( (wpm >	482.5802883	) && (wpm <= 	487.9199895	 )){outKey2 = 	25	;}
	else if( (wpm >	493.2596906	) && (wpm <= 	498.9323333	 )){outKey2 = 	26	;}
	else if( (wpm >	504.604976	) && (wpm <= 	510.3118629	 )){outKey2 = 	27	;}
	else if( (wpm >	516.0187753	) && (wpm <= 	520.1510118	 )){outKey2 = 	28	;}
	else if( (wpm >	524.2832513	) && (wpm <= 	531.2232355	 )){outKey2 = 	29	;}
	else if( (wpm >	538.1632214	) && (wpm <= 	545.0497155	 )){outKey2 = 	30	;}
	else if( (wpm >	551.9362095	) && (wpm <= 	559.9750568	 )){outKey2 = 	31	;}
	else if( (wpm >	568.0139073	) && (wpm <= 	578.1794672	 )){outKey2 = 	32	;}
	else if( (wpm >	588.3450272	) && (wpm <= 	598.4609637	 )){outKey2 = 	33	;}
	else if( (wpm >	608.5769002	) && (wpm <= 	617.3439922	 )){outKey2 = 	34	;}
	else if( (wpm >	626.1134664	) && (wpm <= 	641.4055488	 )){outKey2 = 	35	;}
	else if( (wpm >	656.6976313	) && (wpm <= 	668.7650009	 )){outKey2 = 	36	;}
	else if( (wpm >	680.8323753	) && (wpm <= 	694.5938057	 )){outKey2 = 	37	;}
	else if( (wpm >	708.3552505	) && (wpm <= 	726.0328833	 )){outKey2 = 	38	;}
	else if( (wpm >	743.7105268	) && (wpm <= 	764.0428266	 )){outKey2 = 	39	;}
	else if( (wpm >	784.3751882	) && (wpm <= 	1192.183155	 )){outKey2 = 	40	;}

	String[] returnKeys = new String[2];
	returnKeys[0] = Integer.toString(outKey);
	returnKeys[1] = Integer.toString(outKey2);
	return returnKeys;
	
	}
	
}
