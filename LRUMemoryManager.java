import java.util.ArrayList;
import java.util.LinkedList;

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

   // @TODO document, the p_physical memory represents hte columns in the readme
   public LRUMemoryManager() {
      super();
      _pcbStack = new LinkedList<>();
      for (int i = 0; i < NUM_PHYSICAL_MEMORY_FRAMES; i++) {
         _pcbStack.add(null);
      }
      _head = _pcbStack.getFirst(); // @TODO necessary?
      _tail = _pcbStack.getLast();
   }

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

      Everytime we add to the physical memory we need choose what to overwrite
      in the case of a page fault, and if not, just add it, and adjust the stack
       */



      p_memoryReferences++; // keep track of the number of page references

   } // touchPage

   LinkedList<PCB> _pcbStack;
   PCB _head;
   PCB _tail;

} // LRUMemoryManager
