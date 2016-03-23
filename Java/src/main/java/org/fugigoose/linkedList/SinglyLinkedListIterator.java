package org.fugigoose.linkedList;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator implementation for SinglyLinkedList
 */
public class SinglyLinkedListIterator<E> implements Iterator<E>
{
  private SingleLinkNode<E> tailSentinel;
  private boolean removeHasBeenCalled;
  protected boolean nextHasBeenCalled;
  protected SingleLinkNode<E> previousNode;
  protected SingleLinkNode<E> currentNode;
  protected SingleLinkNode<E> deletedNode;
  protected int index;

  public SinglyLinkedListIterator(SingleLinkNode<E> headSentinel, SingleLinkNode<E> tailSentinel)
  {
    this.currentNode = headSentinel;
    this.tailSentinel = tailSentinel;
    this.index = -1;
  }

  @Override
  public boolean hasNext()
  {
    // Advance the index, although it's only for internal use within the package class SinglyLinkedListIteratorInternal
    index++;

    // If the next node is the back sentinel, that means we're at the end of the list
    // If the list is empty, this will evaluate to false by virtue of the fronts sentinel being linked to the back sentinel
    return currentNode.getNextNode() != tailSentinel;
  }

  @Override
  public E next()
    throws NoSuchElementException
  {
    previousNode = currentNode;
    currentNode = currentNode.getNextNode();

    // If the next node is the tail sentinel, we've gone too far
    if (currentNode == tailSentinel)
    {
      throw new NoSuchElementException("Already reached end of list");
    }

    nextHasBeenCalled = true;
    removeHasBeenCalled = false;

    return currentNode.getElement();
  }

  @Override
  public void remove()
    throws IllegalStateException
  {
    if (!nextHasBeenCalled)
    {
      throw new IllegalStateException("Next must be called before remove");
    }

    if (removeHasBeenCalled)
    {
      throw new IllegalStateException("Remove has already been called once this iteration");
    }

    // Set the previous node's next equal to the current node's next, bypassing the current node
    previousNode.setNextNode(currentNode.getNextNode());
    currentNode.setNextNode(null);
    deletedNode = currentNode;

    // Set the current node back to the previous so that the subsequent call to next() returns the node after the deleted one
    currentNode = previousNode;

    removeHasBeenCalled = true;
  }
}