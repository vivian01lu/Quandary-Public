#include <stdio.h>


int main() {
    int x = 0;
    if (x > 5 && test(x) != 0)
        printf("1");
   printf("2");
}

int test(int x) {
    // Modify this function to return a non-zero value under specific conditions
    if (x == 0) {
        return 1; // Return non-zero value when x is 0
    }

    // Original infinite loop
    while (1 == 1) {}

    return 0;
}