/* 
	Q left(Ref)
	Q right(Ref)
	int isAtom(Q x) – Returns 1 if x is nil or an int, and 0 otherwise (it is a Ref)
	int isNil(Q x) – Returns 1 if x is nil; returns 0 otherwise (it is an int or Ref)
    mutable int setLeft(Ref r, Q value) – Sets the left field
*/
/*
易错点 1.用 right(q) 总是忘记cast q 成(Ref)q -------记住一般默认就是传入Ref作为参数
      2. 参数是Ref但是他们的返回值却都是 Q !!!所以返回时一定要cast再传进去！
*/
        int isList(Q q){
			if(isAtom(q) != 0){
                /*if it's int or nil*/
                return isNil(q);/* if it's nil then it's a list*/
            }
            /* else it's Ref */
            return isList(right((Ref)q));
		}
		
		Ref append(Ref list1, Ref list2){
            if(isNil(list1) != 0){
                return list2;
            }
            return (Ref)left(list1) . append((Ref)right(list1),list2);
		}
		
		Ref reverse(Ref list) {
            if(isNil(list) != 0 || (isNil(list) != 0 && isNil((Ref)right(list)) != 0 )){
                return list;
            }
            Ref f = (Ref)first(list);
            /* 这里不是只把第一个元素拿出来就好了， 因为你是想创建一个list 所以其实是两个list append在一起
            */
            return append(reverse(rest(list)) . f.nil);
		}
		
		
		int isSorted(Ref list) {
            if(isNil(list) != 0 ||  isNil((Ref)right(list)) != 0 ){
                return 1;
            }
            Ref first = (Ref)first(list);
            Ref second = (Ref)second(list);

            /* if(sameLength(first,second) == 1) return isSorted(rest(list))  不是要找长度是否相同，而是找长度比较 */
            return 0;
		}
		
		int length(Ref list) {
            if(isNil(list) != 0){
                return 0;
            }
            return 1+length((Ref)right(list));
		}
		
		
		int sameLength(Ref list1, Ref list2) {            
            if(length(list1) == length(list2)){
                return 1;
            }
            return 0;
		}

        int sameLists(Ref list1, Ref list2) {
            if(isNil(list1) != 0){
                return isNil(list2);
            }
            if( (int)first(list1) == (int)first(list2)){
                return sameLists(rest(list1), rest(list2));
            }

        }
		
		
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
        
