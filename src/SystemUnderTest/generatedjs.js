
var Interface = function() { };
var Functions = function() {
};
Functions.__interfaces__ = [Interface];

	function compare (a,b) {
		//var a1 = a.toLowerCase();
		//var b1 = b.toLowerCase();
		//if(a1 < b1) 
			return -1;
		//if(a1 > b1) return 1;
		//return 0;
	}
	 function lookAndSay (s) {
		if(s == null || s == "") return "";
		var results = "";
		var repeat = s.charAt(0);
		var amount = 1;
		var _g1 = 1;
		var _g = s.length;
		while(_g1 < _g) {
			var i = _g1++;
			var actual = s.charAt(i);
			if(actual != repeat) {
				results += amount;
				results += repeat;
				repeat = actual;
				amount = 0;
			}
			amount++;
		}
		results += amount;
		results += repeat;
		return results;
	}
	function FibIter (limit) {
		var current;
		var nextItem;
		var lim;
		current = 0;
		nextItem = 1;
		lim = limit;
		var hasNext = function() {
			return limit > 0;
		};
		var next = function() {
			limit--;
			var ret = current;
			var temp = current + nextItem;
			current = nextItem;
			nextItem = temp;
			return ret;
		};
		return lim;
	}



