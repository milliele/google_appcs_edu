cmake_minimum_required(VERSION 3.13)
project(movie)

set(CMAKE_CXX_STANDARD 11)

add_executable(cs1 CS1SentimentStarterCode/main.cpp)
add_executable(cs2 CS2SentimentStarterCode/main.cpp CS2SentimentStarterCode/hashtable.cpp CS2SentimentStarterCode/WordEntry.cpp)