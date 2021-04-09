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
    * Simulates a physical page being referenced by a process.
    * This allows the MemoryManager to keep track of the total 
    * number of page references.
    *
    * @param pageNum the physical page being referenced
    */
   public void touchPage(int pageNum)
   {
      super.touchPage(pageNum);
      p_memCounter[pageNum] = 0;
   } // touchPage


} // LRUMemoryManager
