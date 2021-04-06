#Virtual Memory Simulation Program

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
> Another approach to implementing `LRU` replacement is to keep a stack of page numbers.  Whenever a page is referenced, it is removed from the stack and put on the top.  In this way, the most recently used page is alwyas at the top of the stack and the least recently used page is always at the bottom (Figure 9.16).  Because entries must be removed from the middle of the stack, it is best to implement this approach by using a doubly linked list with a head pointer and a tail pointer.  Removing a page and putting it on top of the stack then requires changing six pointers at most.  Each update is a little more expensive, but there is no search for a replacement; the tail pointer points to the bottom of the stack, which is the `LRU` page.  This approach is particularly appropriate for software or microcode implmentations of the `LRU` replacement.

| 7 	| 0 	| 1 	| 2 	| 0    	| 3 	| 0    	| 4 	| 2 	| 3 	| 0 	| 3    	| 2 	| 2 	| 0 	|
|---	|---	|---	|---	|------	|---	|------	|---	|---	|---	|---	|------	|---	|---	|---	|
| 7 	| 7 	| 7 	| 2 	| 2    	| 2 	| 2    	| 4 	| 4 	| 4 	| 0 	| 0    	| 0 	| 2 	| 2 	|
|   	| 0 	| 0 	| 0 	| 0    	| 0 	| 0    	| 0 	| 0 	| 3 	| 3 	| 3    	| 3 	| 3 	| 0 	|
|   	|   	| 1 	| 1 	| 1    	| 3 	| 3    	| 3 	| 2 	| 2 	| 2 	| 2    	| 1 	| 1 	| 1 	|
| * 	| * 	| * 	| * 	| HIT! 	| * 	| HIT! 	| * 	| * 	| * 	| * 	| HIT! 	| * 	| * 	| * 	|
Page hit = 3
Page fault = 12
Hit rate = 1/4