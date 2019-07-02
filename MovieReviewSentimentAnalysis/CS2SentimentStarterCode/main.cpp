/* author: (your name here)
*  date: (today's date)
*  description:  provide a brief description of your program
*  proposed points (out of 10): a description of the number
*  of points you believe your assignment is worth
*/

#include "HashTable.h"
#include <iostream>
#include <fstream>
#include <cstdlib>

#define DATA_PATH "../CS2SentimentStarterCode/"
#define DATA_NAME "movieReviews.txt"

using namespace std;

int main()
{
    //declare a few needed variables for inputing the data
    string line;    
    int score;
    string message = " ";

    //open input file
    ifstream myfile (string(DATA_PATH)+string(DATA_NAME));
    if (myfile.fail() )
    {
        cout << "could not open file" <<endl;
        exit(0);
    }

    //create hash table
    HashTable table(20000);
  
    while (! myfile.eof() )
    {
        myfile >> score;  //get score
        myfile.get();    // get blank space
        getline(myfile,line);
        int len = line.size();
        while(len >0) {  //identify all individual strings
            string sub;
            len = line.find(" ");
            if (len > 0)
            {
                sub = line.substr(0,len);
                line = line.substr(len+1,line.size());
            }
            else {
                sub = line.substr(0,line.size()-1);
            }
            table.put(sub, score); //insert string with the score
        }
    }

    //after data is entered in hash function
    //prompt user for a new movie review
    while(message.length() > 0)
    {
        cout << "enter a review -- Press return to exit: "<<endl;
        getline(cin,message);

        //used for calculating the average
        double sum = 0;
        int count = 0;
        
        int len = message.size();
        //get each individual word from the input
        while(len != std::string::npos) 
        {
            string sub;
            len = message.find(" ");
            if (len != string::npos) 
            {
                sub = message.substr(0,len);
                message = message.substr(len+1,message.size());
            }
            else {
                sub = message;
            }
            //calculate the score of each word
            sum += table.getAverage(sub);
            count++;
        }

        if (message.size() > 0)
        {
            double s = sum/count;
            cout << "The review has an average value of " << s <<endl;
            if(s>2.1) printf("Positive Sentiment\n\n");
            else if(s<1.9) printf("Negative Sentiment\n\n");
            else printf("Neutral Sentiment\n\n");
        }
    }
    
    return 0;
}