mutable Q main( int arg ) {
    if (arg == 1){
        mutable int i = 0;
        Ref x = 3 . nil;

        /* Declare a local variable to keep track of the latest object */
        mutable Ref currentRef = x;

        while (i < 16) {
            /* Create a new object and make the current reference point to it */
            currentRef = 1 . currentRef;
            i = i + 1;
        }
    }else if (arg == 2){
        
        mutable Ref s = nil . 5;
        mutable Ref r = s . 3;
        setLeft(s, r);
        r = nil;
        s = nil;


        mutable int i = 0;
        Ref x = 3 . nil;

        /* Declare a local variable to keep track of the latest object */
        mutable Ref currentRef = x;

        while (i < 14) {
            /* Create a new object and make the current reference point to it */
            currentRef = 1 . currentRef;
            i = i + 1;
        }

    }else if (arg == 3){
        mutable int i = 0;
        Ref x = 3 . nil;

        /* Declare a local variable to keep track of the latest object */
        mutable Ref currentRef = x;

        while (i < 18) {
            /* Create a new object and make the current reference point to it */
            currentRef = 1 . currentRef;
            free (currentRef);
            i = i + 1;
        }

    }else if (arg == 4){
        /* Declare variables */
        mutable int i = 0;
        mutable Ref x = 1 . 3;
        /* Allocate memory explicitly until the heap size reaches 384 bytes :// 16 objects * 24 bytes/object = 384 bytes */
        while (i < 20) {   
            /* x = 1 . x ; */
            i = i + 1;
            Ref y = 1 . 3;
        }
    }
    return nil;
}