/* mutable Q main(int arg) {
    mutable Q q = (15 . 0) . (1 . 0);
    setRight((Ref)q, 2 . 0);
    method(q);
	return q;
}
mutable Q method(Q q){
    setRight((Ref)q, 3 . 0);
    return q;
} */

Q main(int arg) {
    return isList(nil . nil);
}

int isList(Q q) {
    if (isNil(q) != 0) {
        return 1;
    }
    if (isAtom(q) != 0) {
        return 0;
    }
    return isList(right((Ref)q));
}
