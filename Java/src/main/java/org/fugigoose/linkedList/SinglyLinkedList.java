package org.fugigoose.linkedList;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

public class SinglyLinkedList<E> implements List<E>
{
  private SingleLinkNode<E> tail;
  private SingleLinkNode<E> tailSentinel;
  private SingleLinkNode<E> head;
  private SingleLinkNode<E> headSentinel;
  private int size = 0;

  public SinglyLinkedList()
  {
    headSentinel = new SingleLinkNode<>();
    tailSentinel = new SingleLinkNode<>();
    headSentinel.setNextNode(tailSentinel);
    tailSentinel.setNextNode(tailSentinel);
  }

  /**
   * Constructor for initializing the list with starting elements 
   */
  @SafeVarargs
  public SinglyLinkedList(E... elementArray)
  {
    this();
    for (E element : elementArray)
    {
      add(element);
    }
  }

  @Override
  public boolean add(E element)
  {
    // The new node's next should be the tail sentinel, since it's the new last real element
    SingleLinkNode<E> newNode = new SingleLinkNode<>();
    newNode.setElement(element);
    newNode.setNextNode(tailSentinel);

    // If the list is empty, we just add the element after the head sentinel
    // Otherwise, we should add it after tail so that it becomes the new last node
    SingleLinkNode<E> previousNode = (isEmpty()) ? headSentinel : tail;
    previousNode.setNextNode(newNode);

    updateHeadAndTail(newNode);
    size++;
    return true;
  }

  @Override
  public void add(int index, E element)
    throws IndexOutOfBoundsException
  {
    // If we're adding to the end of the list, just use the normal, faster add
    if (index == size)
    {
      add(element);
    }
    else
    {
      addAtIndexInternal(index, element);
    }
  }

  @Override
  public boolean addAll(Collection<? extends E> collection)
  {
    boolean listUpdated = false;

    // If collection is null, just don't do anything and return false (i.e. the list didn't change)
    if (collection != null)
    {
      for (E element : collection)
      {
        add(element);
        listUpdated = true;
      }
    }

    return listUpdated;
  }

  @Override
  public boolean addAll(int index, Collection<? extends E> collection)
  {
    boolean listUpdated = false;

    // If collection is null, just don't do anything and return false (i.e. the list didn't change)
    if (collection != null)
    {
      int currentIndex = index;
      for (E element : collection)
      {
        // We need to increment the supplied index each time so that each element is added after the
        // the previously added element
        add(currentIndex, element);
        currentIndex++;
        listUpdated = true;
      }
    }

    return listUpdated;
  }

  @Override
  public void clear()
  {
    // All we need to do is set the head sentinel's next to the tail sentinel, bypassing any actual nodes
    // The garbage collector will clean up everything in between due to reference deletion
    headSentinel.setNextNode(tailSentinel);
    size = 0;
  }

  @Override
  public boolean contains(Object object)
  {
    return indexOf(object) != -1;
  }

  @Override
  public boolean containsAll(Collection<?> collection)
  {
    boolean containsAll = false;

    // If collection is null, just don't do anything and return false)
    if (collection != null)
    {
      // Assume the list contains all until we find an entry that negates that
      containsAll = true;
      for (Object element : collection)
      {
        containsAll = containsAll && contains(element);
      }
    }

    return containsAll;
  }

  @Override
  public E get(int index)
    throws IndexOutOfBoundsException
  {
    validateIndex(index, size);
    SinglyLinkedListIteratorInternal<E> iterator = new SinglyLinkedListIteratorInternal<>(headSentinel, tailSentinel);
    return iterator.getNodeAtIndex(index).getElement();
  }

  public E getBack()
  {
    return tail.getElement();
  }

  public E getFront()
  {
    return head.getElement();
  }

  @Override
  public int indexOf(Object object)
  {
    boolean found = false;

    // Iterate until we run out of nodes
    SinglyLinkedListIteratorInternal<E> iterator = new SinglyLinkedListIteratorInternal<>(headSentinel, tailSentinel);
    while (iterator.hasNext())
    {
      Object value = iterator.next();

      // If we find it we don't need to iterate any further, so we break
      if (object == null ? (value == null) : (object.equals(value)))
      {
        found = true;
        break;
      }
    }

    return found ? iterator.index : -1;
  }

  @Override
  public boolean isEmpty()
  {
    return size == 0;
  }

  @Override
  public SinglyLinkedListIterator<E> iterator()
  {
    return new SinglyLinkedListIterator<>(headSentinel, tailSentinel);
  }

  @Override
  public int lastIndexOf(Object object)
  {
    int lastIndexOf = -1;

    // Iterate until we run out of nodes
    SinglyLinkedListIteratorInternal<E> iterator = new SinglyLinkedListIteratorInternal<>(headSentinel, tailSentinel);
    while (iterator.hasNext())
    {
      Object value = iterator.next();

      if (object == null ? (value == null) : (object.equals(value)))
      {
        lastIndexOf = iterator.index;
      }
    }

    return lastIndexOf;
  }

  @Override
  public ListIterator<E> listIterator()
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public ListIterator<E> listIterator(int index)
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public E remove(int index)
    throws IndexOutOfBoundsException
  {
    validateIndex(index, size);

    SinglyLinkedListIteratorInternal<E> iterator = new SinglyLinkedListIteratorInternal<>(headSentinel, tailSentinel);
    iterator.advanceToIndex(index);
    removeNodeInternal(iterator);

    return iterator.deletedNode.getElement();
  }

  @Override
  public boolean remove(Object object)
  {
    boolean found = false;

    SinglyLinkedListIteratorInternal<E> iterator = new SinglyLinkedListIteratorInternal<>(headSentinel, tailSentinel);
    while (iterator.hasNext())
    {
      E value = iterator.next();

      // If we found what we were looking for, we need to remove it
      // We don't need to iterate any further, so we break
      if (object == null ? (value == null) : (object.equals(value)))
      {
        found = true;
        removeNodeInternal(iterator);
        break;
      }
    }

    return found;
  }

  @Override
  public boolean removeAll(Collection<?> collection)
  {
    boolean listChanged = false;

    // If collection is null, just don't do anything and return false (i.e. the list didn't change)
    if (collection != null)
    {
      SinglyLinkedListIteratorInternal<E> iterator = new SinglyLinkedListIteratorInternal<>(headSentinel, tailSentinel);
      while (iterator.hasNext())
      {
        // If the value is in the collection, then we need to remove it
        if (collection.contains(iterator.next()))
        {
          removeNodeInternal(iterator);
          listChanged = listChanged || true;
        }
      }
    }

    return listChanged;
  }

  @Override
  public boolean retainAll(Collection<?> collection)
  {
    boolean listChanged = false;

    // If collection is null, just don't do anything and return false (i.e. the list didn't change)
    if (collection != null)
    {
      SinglyLinkedListIteratorInternal<E> iterator = new SinglyLinkedListIteratorInternal<>(headSentinel, tailSentinel);
      while (iterator.hasNext())
      {
        // If the value is NOT in the collection, then we need to remove it
        if (!collection.contains(iterator.next()))
        {
          removeNodeInternal(iterator);
          listChanged = listChanged || true;
        }
        else
        {
          System.out.println();
        }
      }
    }

    return listChanged;
  }

  @Override
  public E set(int index, E newElement)
  {
    validateIndex(index, size);
    SinglyLinkedListIteratorInternal<E> iterator = new SinglyLinkedListIteratorInternal<>(headSentinel, tailSentinel);
    SingleLinkNode<E> targetNode = iterator.getNodeAtIndex(index);
    E previousElement = targetNode.getElement();
    targetNode.setElement(newElement);
    return previousElement;
  }

  @Override
  public int size()
  {
    return size;
  }

  @Override
  public List<E> subList(int fromIndex, int toIndex)
  {
    validateIndex(fromIndex, size);
    validateIndex(toIndex, size);

    // This is obviously invalid
    if (fromIndex > toIndex)
    {
      throw new IndexOutOfBoundsException();
    }

    SinglyLinkedList<E> subList = new SinglyLinkedList<>();

    // Advance to the node right before the fromIndex so that the call to next() reutrns the target node
    SinglyLinkedListIteratorInternal<E> iterator = new SinglyLinkedListIteratorInternal<>(headSentinel, tailSentinel);
    iterator.advanceToIndex(fromIndex - 1);

    // Iterate through remaining nodes until we reach the toIndex
    while (iterator.hasNext() && iterator.index <= toIndex)
    {
      subList.add(iterator.next());
    }

    return subList;
  }

  @Override
  public Object[] toArray()
  {
    Object[] array = new Object[size];
    SinglyLinkedListIteratorInternal<E> iterator = new SinglyLinkedListIteratorInternal<>(headSentinel, tailSentinel);

    while (iterator.hasNext())
    {
      iterator.next();
      array[iterator.index] = iterator.currentNode.getElement();
    }

    return array;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T[] toArray(T[] destinationArray)
  {
    Object[] array;

    if (destinationArray.length < size)
    {
      array = Arrays.copyOf(toArray(), size, destinationArray.getClass());
    }
    else
    {
      System.arraycopy(toArray(), 0, destinationArray, 0, size);
      array = destinationArray;
    }

    if (destinationArray.length > size)
    {
      destinationArray[size] = null;
    }

    return (T[]) array;
  }

  /**
   * Adds an element at the specified position in the list
   */
  private void addAtIndexInternal(int index, E element)
    throws IndexOutOfBoundsException
  {
    // Designating an index equal to size is appropriate, as it means it will be added to the end of the list
    validateIndex(index, size + 1);

    // Get both the previous and current nodes so that we may perform an insertion
    SinglyLinkedListIteratorInternal<E> iterator = new SinglyLinkedListIteratorInternal<>(headSentinel, tailSentinel);
    SingleLinkNode<E> nodeCurrentlyAtIndex = iterator.getNodeAtIndex(index);
    SingleLinkNode<E> previousNode = iterator.previousNode;

    // Create the new node and set its next node equal to the node currently,
    // occupying the indexed position, effectively inserting it right before
    SingleLinkNode<E> newNode = new SingleLinkNode<>();
    newNode.setElement(element);
    newNode.setNextNode(nodeCurrentlyAtIndex);

    // Update the previous node's next to point to the new node to fix the chain
    previousNode.setNextNode(newNode);
    updateHeadAndTail(newNode);
    size++;
  }

  /**
   * Performs the actual remove of the specified node from the list, updates list info
   * WARNING: This method alters the iterator passed to it and the underlying list
   */
  private void removeNodeInternal(SinglyLinkedListIteratorInternal<E> iterator)
  {
    iterator.remove();

    // Update list info
    // If the deleted node is the tail, then we need to evaluate the previous node instead of the next node
    SingleLinkNode<E> nextNode = iterator.previousNode.getNextNode();
    updateHeadAndTail(iterator.deletedNode == tail ? iterator.previousNode : nextNode);
    size--;
  }

  /**
   * Updates the head and tail reference for this list based on the referenceNode
   */
  private void updateHeadAndTail(SingleLinkNode<E> referenceNode)
  {
    // If the reference node is the tail sentinel, that means the list is empty and there is no head 
    if (headSentinel.getNextNode() == referenceNode)
    {
      head = referenceNode != tailSentinel ? referenceNode : null;
    }

    // If the reference node is the head sentinel, that means the list is empty and there is no tail 
    if (tailSentinel == referenceNode.getNextNode())
    {
      tail = referenceNode != headSentinel ? referenceNode : null;
    }
  }

  /**
   * Validates that an index is within range, throwing an exception otherwise
   */
  private void validateIndex(int index, int maxIndex)
    throws IndexOutOfBoundsException
  {
    if (index < 0 || index >= maxIndex)
    {
      throw new IndexOutOfBoundsException();
    }
  }
}
