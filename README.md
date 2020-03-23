# Assignment
## Objective
Implement a way to (efficiently) count the number of times each unique word (token) occurs in all pages these urls refer to.


The main objective is to show how you would approach a problem like this, and to give an example of your python coding. So please include a short description of your reasoning, why you chose a certain
setup or approach.

## Data
A text file with 4255 wikipedia urls.

# Solution
## This is a Java implementation of the Python solution for this assignment
1. The text file with the urls is read and returned as a list with each line in the file being  an element.
1. The list of urls is being iterated over.
   1. The iteration is done in parallel using `parallelStream` method.
   1. This approach assigns a process with a task whenever it is free. This is done to achieve load balancing.
1. An object is created for each url and the following happens:
   1. Create a `set`.
   1. If page is not accessible (/does not exist), report it in consule. No words are added to the `set`.
   1. If page is accessible:
      1. Open url.
      1. Save the html code of the url.
      1. Strip content out of unnecessary html tags so that we are left with the words in the page.
      1. The characters in words are lowered and split by non alpha characters, and saved into a list of words.
      1. All non-alphabetical characters are removed from list of words.
      1. Add list of words to `set`.
1. Make a `union_set` of `set` in object and all previous object sets. This part is done in a `synchronized` block to avoid race condition.
      
## Conclusion
I tried two approaches:
* Append all words into a huge list, then make it a set.
* For each page, make a union set with the previous pages.

The second approach appears to be about roughly 3 times faster than the first approach.

It appears that making the machine deal with a huge amount of data makes it a lot slower, so it is better to perform operations to reduce the data to the necessary minimum in order to gain performances.

## Results
The program finds 170,412 tokens in 176.943 seconds on my machine.

### Explanation: Difference between Java and Python solutions
The number of tokens in the final set is smaller than the number of tokens in the Python solution.

The reason for that is that when stripping html code from page, some words are not separated when they should be. It happens in both solutions, but in Python it happens more frequently.

This is a known issue, and will be fixed in the future.

## Dependencies
* `java.io`
* `java.util`
* `java.net`
* `org.junit`
