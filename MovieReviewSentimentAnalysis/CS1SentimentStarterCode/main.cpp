//
// Created by 黄乐玫 on 2019-07-01.
//
#include <fstream>
#include <iostream>
#include <stdio.h>
#include <string>
#include <unordered_map>
#include <math.h>
#include <set>

#define DATA_PATH "../CS1SentimentStarterCode/"
#define DATA_NAME "movieReviews.txt"
#define NEG_NAME "negative.txt"
#define POS_NAME "positve.txt"

using namespace std;

typedef long long lld;

void func1() {
    ifstream fin(string(DATA_PATH)+string(DATA_NAME));

    int reviewScore;
    string reviewText;
    string word;

    printf("Enter a word: ");
    cin>>word;

    lld totalScore=0;
    int times=0;

    while(fin>>reviewScore)
    {
        getline(fin,reviewText);
//        cout<<reviewText<<endl;
        if(reviewText.find(word)!=string::npos) {
            totalScore += reviewScore;
            times++;
        }
    }
    printf("%s appears %.1f times.\n",word.c_str(), (float)times);
    printf("The average score for reviews containing the word %s is %.16f\n",word.c_str(),times==0?0:1.0*totalScore/times);
}

void func2() {
    unordered_map<string,pair<lld,lld>> dict;
    string negname;
    printf("Enter the name of the file with words you want to find the average score for: ");
    cin>>negname;
    ifstream wordlist(string(DATA_PATH)+negname), review(string(DATA_PATH)+string(DATA_NAME));
    string word;
    while(wordlist>>word) {
        dict.insert(make_pair(word,make_pair(0,0)));
    }

    int sc;
    string text;
    while(review>>sc) {
        getline(review,text);
        for(auto &w:dict) {
            if(text.find(w.first)!=string::npos) {
                w.second.first += sc;
                w.second.second++;
            }
        }
    }
    double avg=0.0;
    for(auto &w:dict) {
        avg += 1.0*w.second.first/w.second.second;
    }
    avg /= dict.size();
    string judge;
    if (avg>2.01) judge = "positive";
    else if(avg<1.99) judge = "negative";
    else judge = "neutral";
    printf("The average score of words in %s is %.16f\n",negname.c_str(),avg);
    printf("The overall sentiment of %s is %s\n",negname.c_str(),judge.c_str());
}

void func3() {
    unordered_map<string,pair<lld,lld>> dict;
    string filename;
    printf("Enter the name of the file with words you want to score: ");
    cin>>filename;
    ifstream wordlist(string(DATA_PATH)+filename), review(string(DATA_PATH)+string(DATA_NAME));
    string word;
    while(wordlist>>word) {
        dict.insert(make_pair(word,make_pair(0,0)));
    }

    int sc;
    string text;
    while(review>>sc) {
        getline(review,text);
        for(auto &w:dict) {
            if(text.find(w.first)!=string::npos) {
                w.second.first += sc;
                w.second.second++;
            }
        }
    }
    double minv=5,maxv=-1;
    string mins,maxs;
    for(auto &w:dict) {
        double s = 1.0*w.second.first/w.second.second;
        if(s>maxv) {
            maxv = s;
            maxs = w.first;
        }
        if(s<minv) {
            minv = s;
            mins = w.first;
        }
    }
    printf("The most positive word, with a score of %.16f is %s\n",maxv,maxs.c_str());
    printf("The most negative word, with a score of %.16f is %s\n",minv,mins.c_str());
}

void func4() {
    unordered_map<string,pair<lld,lld>> dict;
    string filename;
    printf("Enter the name of the file with words you want to separate: ");
    cin>>filename;
    ifstream wordlist(string(DATA_PATH)+filename), review(string(DATA_PATH)+string(DATA_NAME));
    string word;
    while(wordlist>>word) {
        dict.insert(make_pair(word,make_pair(0,0)));
    }

    int sc;
    string text;
    while(review>>sc) {
        getline(review,text);
        for(auto &w:dict) {
            if(text.find(w.first)!=string::npos) {
                w.second.first += sc;
                w.second.second++;
            }
        }
    }
    multiset<pair<double,string>> result;
    for(auto &w:dict) {
        double s = 1.0*w.second.first/w.second.second;
        result.insert(make_pair(s,w.first));
    }
    FILE *pos = fopen((string(DATA_PATH)+string(POS_NAME)).c_str(),"w");
    for(auto it = result.upper_bound(make_pair(2.1,""));it!=result.end();it++)
        fprintf(pos,"%s\n",it->second.c_str());
    fclose(pos);

    auto it = result.lower_bound(make_pair(1.9,""));
    FILE *neg = fopen((string(DATA_PATH)+string(NEG_NAME)).c_str(),"w");
    for(;it!=result.begin();) {
        it--;
        fprintf(neg,"%s\n",it->second.c_str());
    }
    fclose(neg);
}

void summary() {
    while(true) {
        int num;
        do {
            printf("What would you like to do?\n");
            printf("1: Get the score of a word\n");
            printf("2: Get the average score of words in a file (one word per line)\n");
            printf("3: Find the highest/lowest scoring words in a file\n");
            printf("4: Sort words from a file into positive.txt and negative.txt\n");
            printf("5: Exit the program\n");
            printf("Enter a number 1-5: ");
            cin >> num;
        }while(num<1 || num>5);
        printf("\n");
        switch(num) {
            case 1: func1();break;
            case 2: func2();break;
            case 3: func3();break;
            case 4: func4();break;
            case 5: return;
        }
        printf("\n");
    }
}

int main(int agrc, char** argv) {
//    func1();
//    func2();
//    func3();
//    func4();
    summary();
}
