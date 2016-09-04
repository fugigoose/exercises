package org.fugigoose.exercises.linked_list;

public class SingleLinkNode<E>
{
  private SingleLinkNode<E> nextNode;
  private E element;

  public SingleLinkNode<E> getNextNode()
  {
    return nextNode;
  }

  public void setNextNode(SingleLinkNode<E> nextNode)
  {
    this.nextNode = nextNode;
  }

  public E getElement()
  {
    return element;
  }

  public void setElement(E element)
  {
    this.element = element;
  }
}
