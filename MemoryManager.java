import java.util.stream.IntStream;

/**
 *
 * Class MemoryManager manages the physical memory in my virtual
 * memory simulation. It handles page faults and maintains 
 * statistics about memory access.
 *
 * @author David M. Hansen
 * @version 1.5
 */


abstract class MemoryManager 
{
  
   // Class-wide constant defining how many physical pages we have 
   static protected final int NUM_PHYSICAL_MEMORY_FRAMES = 4;


   public MemoryManager()
   {
      // This is probably redundant since "null" is the default
      // value for an uninitialized object and we could have just
      // set the other counters to 0 when we declared them below,
      // but I prefer to be safe and explicit.
      
      for (int i=0; i<NUM_PHYSICAL_MEMORY_FRAMES; i++)
      {
         // Set the physical memory pages to refer to a null object
         // indicating that they are initially "free"
         p_physicalMemory[i] = (PCB) null;
         p_memCounter[i] = 0;
      }

      // Set our global page fault and memory-reference counters to 0
      p_pageFaults = 0;
      p_memoryReferences = 0;

   } // MemoryManager



   /**
    * Marks any memory pages owned by the given process as being
    * free and prints out information 
    *
    * @param process the PCB leaving the simulation
    */
   public void freePages(PCB process)
   {
      // A process has left the system. Search the set of
      // physical pages and if this process owned a page,
      // set that page to "null" to indicate that it is
      // a free page

      // Set any physical page owned by this process to null and reset
      // the counter 
      for (int i=0; i<NUM_PHYSICAL_MEMORY_FRAMES; i++)
      {
         if (p_physicalMemory[i] == process)
         {
            p_physicalMemory[i] = null;
            p_memCounter[i] = 0;
         }
      }

   } // freePages 



   /**
    * Finds a physical memory page to give to the requesting process.
    * We also keep track of the number of page faults.
    *
    * @param process the PCB requesting a page of memory
    * @return an int that is the number of the physical page replaced
    */
   public int handlePageFault(PCB process)
   {
      int victim = findVictim();

      // if there was something in it originally, invalidate then update counter
      if (p_physicalMemory[victim] != null) {
         p_physicalMemory[victim].invalidatePage(victim);
         p_memCounter[victim] = 0;
      }

      // overwrite with new process
      p_physicalMemory[victim] = process;

      // increment the counter
      IntStream.range(0, p_memCounter.length).forEach(i -> p_memCounter[i]++);

      printPageFault(process, victim);
      p_pageFaults++;

      return victim;
   } // handlePageFault


   /**
    * Simulates a physical page being referenced by a process.
    * This allows the MemoryManager to keep track of the total 
    * number of page references.
    *
    * @param pageNum the physical page being referenced
    */
   public void touchPage(int pageNum) { p_memoryReferences++; }


   /**
    * Searches physical memory to find a free page or a currently 
    * occupied page to replace.  
    *
    * @return an int specifying the number of the physical page to replace
    */
   protected int findVictim()
   {
      // Initially assume the victim page is page 0
      int victimPage = 0;
      // If we find a free page, we'll set this flag to true and quit
      // looking any further
      boolean foundFree = false;


      // Search physical memory. If we find a free frame, that's
      // our victim. Otherwise our victim will be the frame with
      // the highest p_memCounter value.  
      for (int i=0; i<NUM_PHYSICAL_MEMORY_FRAMES && !foundFree; i++)
      {
         if (p_physicalMemory[i] == null)
         {
            // Found a free frame - that's our victim!
            foundFree = true;
            victimPage = i;
         }
         else if (p_memCounter[i] > p_memCounter[victimPage])
         {
            // This page has a higher count than our current victim,
            // it's the new victim
            victimPage = i;
         }

      } // for

      return victimPage;

   } // findVictim



  /**
    * Print overall statistics about the simulation including the
    * total number of memory references, page faults, and the page
    * fault ratio.
    */
   protected void printStatistics()
   {
      // Iterate over the physical memory and see if the page is
      // free or owned by some process
      for (int i=0; i<NUM_PHYSICAL_MEMORY_FRAMES; i++)
      {
         if (p_physicalMemory[i] == null) // Not owned by any process
         {
            System.out.println("Page "+i+" is free");
         }
         else // Owned by a process
         {
            System.out.println("Page "+i+" is owned by process # "
                  + p_physicalMemory[i].getID());
         }
      } // for

      // How many page faults were there and what was the page fault ratio
      System.out.println("\n\n"+p_pageFaults + " page faults out of "
            + p_memoryReferences 
            + " total memory references for a page fault ratio of "
            + (int) (((float) p_pageFaults / (float) p_memoryReferences) * 100)
            + "%");

   } // printStatistics


   /**
    * Prints out a message to `System.out` **everytime a *page fault* occurs**.
    * Output should include the ID of the process that caused the page fault
    * and the physical page number that was given to that process
    * (e.g., `PAGE-FAULT: Process 3 given page 0`
    *
    * @param process the process that caused the page fault
    */
   public void printPageFault(PCB process, int pageNum) { System.out.println("PAGE-FAULT: Process " + process.getID() + " given page " + pageNum); } // printPageFault


   // I represent physical memory as an array of PCBs - the process
   // that owns that memory will be in that array slot. A null object
   // means the slot is currently free.
   protected PCB[] p_physicalMemory = new PCB[NUM_PHYSICAL_MEMORY_FRAMES];

   // memCounter keeps track of a count for each memory frame. Its
   // meaning depends on the page-replacement algorithm being used
   protected int[] p_memCounter = new int[NUM_PHYSICAL_MEMORY_FRAMES];


   // Two counters to track the number of page faults and total number
   // of memory references
   protected int p_pageFaults;
   protected int p_memoryReferences;


} // MemoryManager 
