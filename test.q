Q main(int arg){
    Ref emptyList = nil;
    Ref ListWithOneElement =5 . nil;
    Ref ListWithThreeElement = 5 . ((8 . (9 . nil))) . ((2 . 3) . nil));
    Ref List = (3 . (4 . (5 .(2 . nil))));
    return isList(List);
    print isAtom(nil)
    
}

Ref insertAtEnd(Ref oldList,int elem){
    if(isNil(oldList) != 0){
        return elem . nil;
    }
    return left(oldList) . insertAtEnd((Ref)right(oldList),elem);
}

int contains(Ref list,int elem){
    if(isNil(list) != 0){
        return 0;
    }
    if((int)first(list) == elem){
        return 1;
    }
    return contains(rest(list),elem);
}
/*
Q is super type; int and Ref are subtypes
*/

/*  
Built-in functions left and right:
Q left(Ref)
Q right(Ref)
*/
Ref rest(Ref list){
    return (Ref)right(list);
}

Q first(Ref list){
    return left(list);
}
Q second(Ref list){
    return left((Ref)right(list));
}
Q third(Ref list){
    return first(rest(rest(list)));
}


int fact(int n){
    if(n <= 1){
        return 1;
    }
    return n * fact(n-1);
}

