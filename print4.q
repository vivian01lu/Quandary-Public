mutable int print4(int thread, Ref counters, Ref other) {
	acq(counters);
	int l = (int)left(counters);
	setLeft(counters, l + 1);
	rel(counters);
	print (int)left(counters);
	acq(other);
	setRight(counters, randomInt(4));
	rel(other);
	
	return 1;
}

mutable Q main(int arg) {
	Ref counters = 0 . 0;
	Ref other = nil . nil;
	int dummy = [ [ print4(0, counters, other) + print4(1, counters, other) ] + [ print4(2, counters, other) + print4(3, counters, other) ] ];

	return dummy;
}
