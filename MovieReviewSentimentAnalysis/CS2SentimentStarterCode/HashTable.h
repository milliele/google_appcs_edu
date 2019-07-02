#ifndef HASHTABLE_H
#define HASHTABLE_H

#include <string>
#include <list>
#include <iostream>
#include "WordEntry.h"
#include <functional>

using namespace std;

class HashTable {

    private:
        list<WordEntry> *hashTable;
        int computeHash(string);
        int size;
        hash<string> hash_code;

    public:
        HashTable(int);
        bool contains(string);
        double getAverage(string);
        void put(string,int);
         ~HashTable();
};

#endif
