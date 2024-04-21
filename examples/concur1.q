mutable Q main(int arg) {
    mutable Ref counters = 0 . 0;
    int dummy = [ doLeftInc(counters) + doLeftInc(counters) ];
    return counters;
}

mutable int doLeftInc(Ref counters) {
   
        acq(counters);
        int value = (int)left(counters);
        setLeft(counters, value + 1);
        rel(counters);
        return 1;
}
