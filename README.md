# Virtual Memory Simulation Program

Complete my Java program that simulates virtual memory management.  The input to my program is a simulated set of virtual memory addresses froma  number of simulated processes.  This space-delimited file contains one triple perline: `process ID, memory address, read/write flag.`  Process ID's and memory addresses are integers for convenience and the read/write flag is a 0 (read) or 1 (write).

The program currently assumes the following:
* Processes will only generate legal addresses
* We use demand-paged virtual memory
* Memory is byte-addressable
* Memory addresses are 12-bits long, giving and address space of 4096 bytes
* We use a page size of 32 bytes (thus our address-space is composed of 128 pages meaning that our 12-bit address is composed of a 7-bit page number + a 5-bit offset)
* We have a 5 physical memory page frames (a total of 160 bytes)

In addition, processes may come and go during the simulation.  New processes appear in the input without warning and are added to the simulation dynamically.  Processes leave the siumulation by generating a negatice memory address (which does **not** show up in any output); process numbers might be reused. 

## Task
1. Complete this simulation by implementing subclasses of the `MemoryManager` class: `LRUMemoryManager.java` and `FIFOMemoryManager.java`; you may need to make minor modifications to the state of the `MemoryManager` class as well.


2. Instrument the program so that it prints a message to `System.out` **every time a *page-fault* occurs**; output should include the ID of the process that caused the page-fault and the physical page number that was given to that process (e.g., `PAGE-FAULT: Process 3 given page 0`)


3. Change the parameters of the simulation such that 
    1. The 12-bit address is composed of an 8-bit page number + a 40bit offset
    2. We have 4 physical memory page frames
    I believe my code is well documented enough that you should be able to make a couple of simple changes that alter the simulation parameters (you could argue that these should be passed to the program, but I have chosen not to do so).

Note that you should make a few changes to my code except those necessary to 1) change the parameters of the simulation and 2), to support you `MemoryManager` sub-class implementations.

Run the simulation twice, once with each page-replacement algorithm

## Submit

* Your functional and documented `FIFOMemoryManager.java` and `LRUManager.java` implementations along with other source-files; do not change the name of the main simulation class.
* Output of running your simulation for FIFO and LRU on the VM.dat input file.

---

## LRU implementation (stack)
> Another approach to implementing `LRU` replacement is to keep a stack of page numbers.  Whenever a page is referenced, it is removed from the stack and put on the top.  In this way, the most recently used page is always at the top of the stack, and the least recently used page is always at the bottom (Figure 9.16).  Because entries must be removed from the middle of the stack, it is best to implement this approach by using a doubly linked list with a head pointer and a tail pointer.  Removing a page and putting it on top of the stack then requires changing six pointers at most.  Each update is a little more expensive, but there is no search for a replacement; the tail pointer points to the bottom of the stack, which is the `LRU` page.  This approach is particularly appropriate for software or microcode implementations of the `LRU` replacement.

### tl;dr
* keep track of when a page is used.
* replace the page that has been used least recently
* keep a linked list of all pages
* on every memory reference, move that page to the front of the list
* the page at the tail of the list is replaced

| 7 	| 0 	| 1 	| 2 	| 0    	| 3 	| 0    	| 4 	| 2 	| 3 	| 0 	| 3    	| 2 	| 2 	| 0 	|
|---	|---	|---	|---	|------	|---	|------	|---	|---	|---	|---	|------	|---	|---	|---	|
| 7 	| 7 	| 7 	| 2 	| 2    	| 2 	| 2    	| 4 	| 4 	| 4 	| 0 	| 0    	| 0 	| 2 	| 2 	|
|   	| 0 	| 0 	| 0 	| 0    	| 0 	| 0    	| 0 	| 0 	| 3 	| 3 	| 3    	| 3 	| 3 	| 0 	|
|   	|   	| 1 	| 1 	| 1    	| 3 	| 3    	| 3 	| 2 	| 2 	| 2 	| 2    	| 1 	| 1 	| 1 	|
| * 	| * 	| * 	| * 	| HIT! 	| * 	| HIT! 	| * 	| * 	| * 	| * 	| HIT! 	| * 	| * 	| * 	|


Page hit = 3
Page fault = 12
Hit rate = 1/4

---

I guess I'm a little confused with how the read/write flag works
How does the logical address work in this situation?

So the way that I understand this, the simulation calls free page which for the LRU will make it remove the item from the stack, and the touch page method will be the algorithm that searches for the place to put the process, but if that causes a page fault then we call handle page fault in which we just print the page fault?