/**
 *
 * Class LRUMemoryManager manages the physical memory in my virtual
 * memory simulation using an LRU paging algorithm. 
 *
 * @author 
 * @version 
 * @see MemoryManager
 */


class LRUMemoryManager extends MemoryManager
{


   /**
    * Finds a physical memory page to give to the requesting process.
    * We also keep track of the number of page faults.
    *
    * @param process the PCB requesting a page of memory
    * @return an int that is the number of the physical page replaced
    */
   public int handlePageFault(PCB process)
   {
      /* @TODO
      called by `translateAddress` in `PCB` where we asked to fault a page
      and return what the physical page we're assigned to
     */

      p_pageFaults++;            // keep track of the number of page faults
      printPageFault(process);   // everytime a page fault occurs, print out to System.out
      return 0;

   } // handlePageFault



   /**
    * Simulates a physical page being referenced by a process.
    * This allows the MemoryManager to keep track of the total 
    * number of page references.
    *
    * @param pageNum the physical page being referenced
    */
   public void touchPage(int pageNum)
   {
      /* @TODO we need to know when we are touched so that we can update the linked list stack
      called from `handleAddress()`
      We get a physical page number translated from the logical address
      by the page-table, then it's told whether it's a read or write so it can
      remember, then it asks us to touch that page
       */

      p_memoryReferences++; // keep track of the number of page references

   } // touchPage

} // LRUMemoryManager 
