package twod;

public class MapmzOutKey {
	
	public static int getKey(double wpm) {
	int outKey = 0;
	
		if( (wpm > 0.00 ) && (wpm < 351.00)){outKey = 0;}
		else if( (wpm >=351.00 ) && (wpm < 354.00)){outKey = 1;}
		else if( (wpm >=354.00 ) && (wpm < 356.80)){outKey = 2;}
		else if( (wpm >=356.80 ) && (wpm < 357.00)){outKey = 3;}
	    else if( (wpm >=357.00 ) && (wpm < 357.25)){outKey = 4;}			    
	    else if( (wpm >=357.25 ) && (wpm < 357.40)){outKey = 5;}
	    else if( (wpm >=357.40 ) && (wpm < 357.50)){outKey = 6;}			 
		else if( (wpm >=357.50 ) && (wpm < 358.40)){outKey = 7;}
		else if( (wpm >=358.40 ) && (wpm < 364.50)){outKey = 8;}
		else if( (wpm >=364.50 ) && (wpm < 367.50)){outKey = 9;}
		else if( (wpm >=367.50 ) && (wpm < 370.00)){outKey = 10;}
		else if( (wpm >=370.00 ) && (wpm < 372.00)){outKey = 11;}
		else if( (wpm >=372.00 ) && (wpm < 374.00)){outKey = 12;}				
		else if( (wpm >=374.00 ) && (wpm < 377.00)){outKey = 13;}
		else if( (wpm >=377.00 ) && (wpm < 379.70)){outKey = 14;}
		else if( (wpm >=379.70 ) && (wpm < 383.00)){outKey = 15;}
		else if( (wpm >=383.00 ) && (wpm < 387.30)){outKey = 16;}
		else if( (wpm >=387.30 ) && (wpm < 390.00)){outKey = 17;}
		else if( (wpm >=390.00 ) && (wpm < 393.00)){outKey = 18;}
		else if( (wpm >=393.00 ) && (wpm < 396.00)){outKey = 19;}
		else if( (wpm >=396.00 ) && (wpm < 401.00)){outKey = 20;}
		else if( (wpm >=401.00 ) && (wpm < 403.00)){outKey = 21;}
		else if( (wpm >=403.00 ) && (wpm < 404.30)){outKey = 22;}
		else if( (wpm >=404.30 ) && (wpm < 405.60)){outKey = 23;}
		else if( (wpm >=405.60 ) && (wpm < 407.00)){outKey = 24;}
		else if( (wpm >=407.00 ) && (wpm < 409.00)){outKey = 25;}
		else if( (wpm >=409.00 ) && (wpm < 411.00)){outKey = 26;}
		else if( (wpm >=411.00 ) && (wpm < 413.00)){outKey = 27;}
		else if( (wpm >=413.00 ) && (wpm < 415.00)){outKey = 28;}
		else if( (wpm >=415.00 ) && (wpm < 417.00)){outKey = 29;}
		else if( (wpm >=417.00 ) && (wpm < 419.00)){outKey = 30;}
		else if( (wpm >=419.00 ) && (wpm < 421.00)){outKey = 31;}
		else if( (wpm >=421.00 ) && (wpm < 422.50)){outKey = 32;}
		else if( (wpm >=422.50 ) && (wpm < 425.10)){outKey = 33;}
		else if( (wpm >=425.10 ) && (wpm < 427.00)){outKey = 34;}
		else if( (wpm >=427.00 ) && (wpm < 429.00)){outKey = 35;}
		else if( (wpm >=429.00 ) && (wpm < 429.70)){outKey = 36;}
		else if( (wpm >=429.70 ) && (wpm < 430.10)){outKey = 37;}
		else if( (wpm >=430.10 ) && (wpm < 430.70)){outKey = 38;}
		else if( (wpm >=430.70 ) && (wpm < 431.00)){outKey = 39;}
		else if( (wpm >=431.00 ) && (wpm < 431.30)){outKey = 40;}
		else if( (wpm >=431.30 ) && (wpm < 431.60)){outKey = 41;}
		else if( (wpm >=431.60 ) && (wpm < 432.00)){outKey = 42;}
		else if( (wpm >=432.00 ) && (wpm < 433.70)){outKey = 43;}
		else if( (wpm >=433.70 ) && (wpm < 436.00)){outKey = 44;}
		else if( (wpm >=436.00 ) && (wpm < 439.00)){outKey = 45;}
		else if( (wpm >=439.00 ) && (wpm < 442.00)){outKey = 46;}
		else if( (wpm >=442.00 ) && (wpm < 444.00)){outKey = 47;}
		else if( (wpm >=444.00 ) && (wpm < 446.00)){outKey = 48;}
		else if( (wpm >=446.00 ) && (wpm < 447.30)){outKey = 49;}
		else if( (wpm >=447.30 ) && (wpm < 448.20)){outKey = 50;}
		else if( (wpm >=448.20 ) && (wpm < 449.70)){outKey = 51;}
		else if( (wpm >=449.70 ) && (wpm < 451.80)){outKey = 52;}
		else if( (wpm >=451.80 ) && (wpm < 454.00)){outKey = 53;}
		else if( (wpm >=454.00 ) && (wpm < 456.00)){outKey = 54;}
		else if( (wpm >=456.00 ) && (wpm < 458.00)){outKey = 55;}
		else if( (wpm >=458.00 ) && (wpm < 460.00)){outKey = 56;}
		else if( (wpm >=460.00 ) && (wpm < 462.00)){outKey = 57;}
		else if( (wpm >=462.00 ) && (wpm < 465.00)){outKey = 58;}
		else if( (wpm >=465.00 ) && (wpm < 468.80)){outKey = 59;}
		else if( (wpm >=468.80 ) && (wpm < 472.00)){outKey = 60;}
		else if( (wpm >=472.00 ) && (wpm < 475.00)){outKey = 61;}
		else if( (wpm >=475.00 ) && (wpm < 478.00)){outKey = 62;}
		else if( (wpm >=478.00 ) && (wpm < 482.00)){outKey = 63;}
		else if( (wpm >=482.00 ) && (wpm < 486.00)){outKey = 64;}
		else if( (wpm >=486.00 ) && (wpm < 490.00)){outKey = 65;}
		else if( (wpm >=490.00 ) && (wpm < 494.00)){outKey = 66;}
		else if( (wpm >=494.00 ) && (wpm < 498.00)){outKey = 67;}
		else if( (wpm >=498.00 ) && (wpm < 502.00)){outKey = 68;}
		else if( (wpm >=502.00 ) && (wpm < 505.20)){outKey = 69;}
		else if( (wpm >=505.20 ) && (wpm < 509.00)){outKey = 70;}
		else if( (wpm >=509.00 ) && (wpm < 514.00)){outKey = 71;}
		else if( (wpm >=514.00 ) && (wpm < 518.00)){outKey = 72;}
		else if( (wpm >=518.00 ) && (wpm < 522.00)){outKey = 73;}
		else if( (wpm >=522.00 ) && (wpm < 526.00)){outKey = 74;}
		else if( (wpm >=526.00 ) && (wpm < 532.00)){outKey = 75;}
		else if( (wpm >=532.00 ) && (wpm < 538.00)){outKey = 76;}
		else if( (wpm >=538.00 ) && (wpm < 544.00)){outKey = 77;}
		else if( (wpm >=544.00 ) && (wpm < 549.00)){outKey = 78;}
		else if( (wpm >=549.00 ) && (wpm < 554.10)){outKey = 79;}
		else if( (wpm >=554.10 ) && (wpm < 559.90)){outKey = 80;}
		else if( (wpm >=559.90 ) && (wpm < 565.00)){outKey = 81;}
		else if( (wpm >=565.00 ) && (wpm < 571.00)){outKey = 82;}
		else if( (wpm >=571.00 ) && (wpm < 578.00)){outKey = 83;}
		else if( (wpm >=578.00 ) && (wpm < 585.00)){outKey = 84;}
		else if( (wpm >=585.00 ) && (wpm < 593.00)){outKey = 85;}
		else if( (wpm >=593.00 ) && (wpm < 602.00)){outKey = 86;}
		else if( (wpm >=602.00 ) && (wpm < 610.00)){outKey = 87;}
		else if( (wpm >=610.00 ) && (wpm < 617.00)){outKey = 88;}
		else if( (wpm >=617.00 ) && (wpm < 627.00)){outKey = 89;}
		else if( (wpm >=627.00 ) && (wpm < 638.00)){outKey = 90;}
		else if( (wpm >=638.00 ) && (wpm < 648.00)){outKey = 91;}
		else if( (wpm >=648.00 ) && (wpm < 659.00)){outKey = 92;}
		else if( (wpm >=659.00 ) && (wpm < 669.00)){outKey = 93;}
		else if( (wpm >=669.00 ) && (wpm < 680.00)){outKey = 94;}
		else if( (wpm >=680.00 ) && (wpm < 690.00)){outKey = 95;}
		else if( (wpm >=690.00 ) && (wpm < 705.00)){outKey = 96;}
		else if( (wpm >=705.00 ) && (wpm < 716.00)){outKey = 97;}
		else if( (wpm >=716.00 ) && (wpm < 730.00)){outKey = 98;}
		else if( (wpm >=730.00 ) && (wpm < 759.00)){outKey = 99;}
		else if( (wpm >=759.00 ) && (wpm < 776.00)){outKey = 100;}
		else if( (wpm >=776.00 ) && (wpm < 822.00)){outKey = 101;}
		else if( (wpm >=822.00 ) && (wpm < 905.00)){outKey = 102;}
		else {outKey = 103;}
	return outKey;
	
	}
	
}
