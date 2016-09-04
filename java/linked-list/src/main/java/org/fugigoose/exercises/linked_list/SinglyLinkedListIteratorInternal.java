package org.fugigoose.exercises.linked_list;

/**
 * Adds advanced functions to the base iterator for internal use by the List class
 * NOTE: Only usable within the package
 */
class SinglyLinkedListIteratorInternal<E> extends SinglyLinkedListIterator<E>
{
  public SinglyLinkedListIteratorInternal(SingleLinkNode<E> previousNode, SingleLinkNode<E> tailSentinel)
  {
    super(previousNode, tailSentinel);
  }

  /**
   * Advances the iterator just past the node at the designated index, so that currentNode will return it
   * WARNING: The value returned by a subsequent call to next() will be the next value, not the target node value
   * @throws IllegalStateException if called after next() has already been called
   */
  public void advanceToIndex(int targetIndex)
    throws IllegalStateException
  {
    if (nextHasBeenCalled)
    {
      throw new IllegalStateException("Cannot be called, iteration has occurred");
    }

    index = -1;
    while (index < targetIndex)
    {
      next();
      index++;
    }
  }

  /**
   * Advances the iterator to just past the node at the target index, then returns that node
   * @throws IllegalStateException if called after next() has already been called
   */
  public SingleLinkNode<E> getNodeAtIndex(int index)
    throws IllegalStateException
  {
    advanceToIndex(index);
    return currentNode; 
  }
}
