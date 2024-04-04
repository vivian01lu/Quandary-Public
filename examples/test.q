/* Q main(int arg){
    /* mutable Ref r = 3 . 5;
    r = nil;
    Ref s = 1 . 7;
    return s; */

    /* mutable Ref r = 3 . 5;
    mutable Ref r = 3 . 5;
    r = nil;
    Ref s = (1 . 2) . 7; 
   
    return r;
} */

/* 
Q main (int arg) {
    mutable Ref list = nil; 
    mutable int n = 0;
    while( 1 == 1 ){
        list = n . list;
        n = n + 1;
    }
   return list;
} */

/* Q main( int arg ) {
   Ref x = 3 . nil;
   mutable Ref y = x . 5;
   free y;
   Ref z = x . nil;
   return z;

   //Interpreter returned ((3 . nil) . nil)
   //Quandary process returned 0
} */


/* Q main( int arg ) {
   Ref x = 3 . nil;
   mutable Ref y = x . 5;
   free x;
   Ref z = x . nil;
   return z;

   //if we free x then try to use x it will get stuck
} */


Q main( int arg ) {
   Ref x = 3 . nil;
   mutable Ref y = x . 5;
   free y;
   Ref z = x . nil;
   return z;
}