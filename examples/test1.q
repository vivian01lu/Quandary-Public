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

mutable Q main(mutable int arg) {
    mutable Ref list = 0 . nil;
    while (arg > 0) {
		int index = randomInt(arg);
		list = rmElement(list, index);
		list = 2 . list;
		arg = arg - 1;
	}
    return length(list);
}

int length(Ref list) {
	if (isNil(list) != 0) return 0;
	return 1 + length((Ref)right(list));
}
mutable Ref rmElement(Ref list, int index) {
	if (isNil(list) != 0)
		return nil;
	if (index == 0) {
		return (Ref)right(list);
	}
	setRight(list, rmElement((Ref)right(list), index-1));
	return list;
}
