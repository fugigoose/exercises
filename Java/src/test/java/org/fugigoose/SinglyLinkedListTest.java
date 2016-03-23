package org.fugigoose;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.fugigoose.linkedList.SinglyLinkedList;
import org.fugigoose.linkedList.SinglyLinkedListIterator;
import org.junit.Test;

public class SinglyLinkedListTest
{
  @Test
  public void addTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>();
    assertList(list);

    list.add("Fugi");
    assertList(list, "Fugi");

    list.add("is");
    list.add("cool");
    assertList(list, "Fugi", "is", "cool");
  }

  @Test
  public void addWithIndexTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("Fugi", "is", "cool");

    list.add(1, "the");
    list.add(2, "goose");
    list.add(4, "really");
    assertList(list, "Fugi", "the", "goose", "is", "really", "cool");

    list.add(0, "Certainly");
    list.add(7, "and");
    list.add(8, "neat");
    assertList(list, "Certainly", "Fugi", "the", "goose", "is", "really", "cool", "and", "neat");
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void addAboveRangeExceptionTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("One", "Two", "Three");
    list.add(5, "Four");
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void addBelowRangeExceptionTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("One", "Two", "Three");
    list.add(-1, "Four");
  }

  @Test
  public void addAllTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>();
    list.addAll(Arrays.asList("in", "Spain"));
    assertList(list, "in", "Spain");

    List<String> collection = Arrays.asList("The", "rain");
    list.addAll(0, collection);
    assertList(list, "The", "rain", "in", "Spain");

    collection = Arrays.asList("on", "the", "plain");
    list.addAll(4, collection);
    assertList(list, "The", "rain", "in", "Spain", "on", "the", "plain");

    collection = Arrays.asList("falls", "mainly");
    list.addAll(4, collection);
    assertList(list, "The", "rain", "in", "Spain", "falls", "mainly", "on", "the", "plain");

    assertFalse(list.addAll(null));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void addAllAboveRangeExceptionTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("Fugi", "is", "cool");
    List<String> collection = Arrays.asList("and", "so", "are", "you");
    list.addAll(4, collection);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void addAllBelowRangeExceptionTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("Fugi", "is", "cool");
    List<String> collection = Arrays.asList("and", "so", "are", "you");
    list.addAll(-1, collection);
  }

  @Test
  public void clearTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("This", "is", "not", "a", "list");
    list.clear();
    assertList(list);
  }

  @Test
  public void containsTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("This", "is", "not", "not", "a", "list");
    list.add(null);
    assertTrue(list.contains("This"));
    assertTrue(list.contains("not"));
    assertTrue(list.contains(null));
    assertFalse(list.contains("c'est"));
  }

  @Test
  public void containsAllTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("This", "is", "not", "a", "list");
    List<String> collection = Arrays.asList("This", "is", "list");
    assertTrue(list.containsAll(collection));
    collection = Arrays.asList("This", "is", "Sparta");
    assertFalse(list.containsAll(collection));
    collection = new ArrayList<>();
    assertTrue(list.containsAll(collection));
    assertFalse(list.containsAll(null));
  }

  @Test
  public void getTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("One", "Two", "Three");
    assertEquals("One", list.get(0));
    assertEquals("Two", list.get(1));
    assertEquals("Three", list.get(2));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void getAboveRangeExceptionTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("One", "Two", "Three");
    list.get(5);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void getBelowRangeExceptionTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("One", "Two", "Three");
    list.get(-1);
  }

  @Test
  public void getBackTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("One", "Two", "Three");
    assertEquals("Three", list.getBack());
  }

  @Test
  public void getFrontTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("One", "Two", "Three");
    assertEquals("One", list.getFront());
  }

  @Test
  public void indexOfTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("This", "is", "not", "not", "a", "list");
    list.add(null);
    assertEquals(0, list.indexOf("This"));
    assertEquals(2, list.indexOf("not"));
    assertEquals(6, list.indexOf(null));
    assertEquals(-1, list.indexOf("c'est"));
  }

  @Test
  public void isEmptyTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>();
    assertTrue(list.isEmpty());
    list = new SinglyLinkedList<>("This", "is", "not", "not", "a", "list");
    assertFalse(list.isEmpty());
    list.clear();
    assertTrue(list.isEmpty());
    list = new SinglyLinkedList<>("List");
    assertFalse(list.isEmpty());
    list.remove(0);
    assertTrue(list.isEmpty());
    list.add("List");
    assertFalse(list.isEmpty());
  }

  public void iteratorTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("One", "Two", "Three");
    SinglyLinkedListIterator<String> iterator = list.iterator();
    assertNotNull(iterator);
    assertEquals(SinglyLinkedListIterator.class, iterator.getClass());
  }

  @Test
  public void lastIndexOfTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("red", "fish", "blue", "fish", "red", "fish", "blue", "fish");
    assertEquals(4, list.lastIndexOf("red"));
    assertEquals(7, list.lastIndexOf("fish"));
    assertEquals(6, list.lastIndexOf("blue"));
    assertEquals(-1, list.lastIndexOf("green"));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void listIteratorExceptionTest()
  {
    new SinglyLinkedList<String>().listIterator();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void listIteratorWithIndexExceptionTest()
  {
    new SinglyLinkedList<String>().listIterator(1);
  }

  @Test
  public void removeTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("Me", "and", "you", "are", "kinda", "awesome", "sometimes");

    String deletedElement = list.remove(0);
    assertEquals("Me", deletedElement);
    deletedElement = list.remove(0);
    assertEquals("and", deletedElement);
    assertList(list, "you", "are", "kinda", "awesome", "sometimes");

    deletedElement = list.remove(2);
    assertEquals("kinda", deletedElement);
    assertList(list, "you", "are", "awesome", "sometimes");

    deletedElement = list.remove(3);
    assertEquals("sometimes", deletedElement);
    assertList(list, "you", "are", "awesome");
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void removeAboveRangeExceptionTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("One", "Two", "Three");
    list.remove(5);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void removeBelowRangeExceptionTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("One", "Two", "Three");
    list.remove(-1);
  }

  @Test
  public void removeByValueTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("Me", "and", "you", "are", "kinda", "awesome", "sometimes", null);

    boolean listChanged = list.remove("Me");
    assertTrue(listChanged);
    listChanged = list.remove("and");
    assertTrue(listChanged);
    assertList(list, "you", "are", "kinda", "awesome", "sometimes", null);

    listChanged = list.remove("kinda");
    assertTrue(listChanged);
    assertList(list, "you", "are", "awesome", "sometimes", null);

    listChanged = list.remove("sometimes");
    assertTrue(listChanged);
    assertList(list, "you", "are", "awesome", null);

    listChanged = list.remove(null);
    assertTrue(listChanged);
    assertList(list, "you", "are", "awesome");
  }

  @Test
  public void removeAllTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("Me", "and", "you", "are", "sometimes", "kinda", "awesome", "sometimes",
                                                           null);
    boolean listChanged = list.removeAll(Arrays.asList("Me", "and", "sometimes", "kinda", null));
    assertTrue(listChanged);
    assertList(list, "you", "are", "awesome");
  }

  @Test
  public void retainAllTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("Me", "and", "you", "are", "sometimes", "kinda", "awesome", "sometimes",
                                                           null);
    boolean listChanged = list.retainAll(Arrays.asList("you", "are", "really", "awesome"));
    assertTrue(listChanged);
    assertList(list, "you", "are", "awesome");
  }

  @Test
  public void setTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("This", "is", "not", "a", "list");
    String previousElement = list.set(2, "totally");
    assertEquals("not", previousElement);
    assertList(list, "This", "is", "totally", "a", "list");

    previousElement = list.set(0, "That");
    assertEquals("This", previousElement);
    assertList(list, "That", "is", "totally", "a", "list");

    previousElement = list.set(4, "lie");
    assertEquals("list", previousElement);
    assertList(list, "That", "is", "totally", "a", "lie");
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void setAboveRangeExceptionTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("This", "is", "not", "a", "list");
    list.set(7, "Oops");
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void setBelowRangeExceptionTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("This", "is", "not", "a", "list");
    list.set(-1, "Oops");
  }

  @Test
  public void sizeTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("One", "Two", "Three");
    assertEquals(3, list.size());
    list.add("Four");
    assertEquals(4, list.size());
    list.clear();
    assertEquals(0, list.size());
    list = new SinglyLinkedList<>();
    assertEquals(0, list.size());
    list = new SinglyLinkedList<>("One");
    list.remove(0);
    assertEquals(0, list.size());
  }

  @Test
  public void subListTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("This", "is", "not", "a", "list");
    List<String> subList = list.subList(1, 3);
    assertList(subList, "is", "not", "a");

    list = new SinglyLinkedList<>("This", "is", "not", "a", "list");
    subList = list.subList(0, 1);
    assertList(subList, "This", "is");

    list = new SinglyLinkedList<>("This", "is", "not", "a", "list");
    subList = list.subList(2, 4);
    assertList(subList, "not", "a", "list");

    list = new SinglyLinkedList<>("This", "is", "not", "a", "list");
    subList = list.subList(0, 4);
    assertList(subList, "This", "is", "not", "a", "list");

    list = new SinglyLinkedList<>("This", "is", "not", "a", "list");
    subList = list.subList(1, 1);
    assertList(subList, "is");
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void subListFromIndexAboveRangeExceptionTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("This", "is", "not", "a", "list");
    list.subList(5, 5);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void subListFromIndexBelowRangeExceptionTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("This", "is", "not", "a", "list");
    list.subList(-1, -1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void subListToIndexAboveRangeExceptionTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("This", "is", "not", "a", "list");
    list.subList(1, 5);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void subListToIndexBelowRangeExceptionTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("This", "is", "not", "a", "list");
    list.subList(1, -1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void subListFromToIndexReversedExceptionTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("This", "is", "not", "a", "list");
    list.subList(4, 0);
  }

  @Test
  public void toArrayObjectTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("This", "is", "not", "a", "list");
    Object[] expectedArray = new Object[] { "This", "is", "not", "a", "list" };
    Object[] array = list.toArray();
    assertEquals(5, array.length);
    assertArrayEquals(expectedArray, array);

    list = new SinglyLinkedList<>();
    expectedArray = new Object[0];
    array = list.toArray();
    assertEquals(0, array.length);
  }

  @Test
  public void toArrayGenericTypeTest()
  {
    SinglyLinkedList<String> list = new SinglyLinkedList<>("This", "is", "not", "a", "list");
    String[] expectedArray = new String[] { "This", "is", "not", "a", "list" };

    String[] destinationArray = new String[0];
    String[] array = list.<String> toArray(destinationArray);
    assertEquals(5, array.length);
    assertEquals(0, destinationArray.length);
    assertArrayEquals(expectedArray, array);

    destinationArray = new String[5];
    array = list.<String> toArray(destinationArray);
    assertEquals(5, array.length);
    assertTrue(destinationArray == array);

    destinationArray = new String[] { "This", "is", "not", "a", "list", "This", "is", "Sparta" };
    expectedArray = new String[] { "This", "is", "not", "a", "list", null, "is", "Sparta" };
    array = list.<String> toArray(destinationArray);
    assertEquals(8, array.length);
    assertTrue(destinationArray == array);
    assertArrayEquals(expectedArray, array);
  }

  private void assertList(List<String> list, String... elementArray)
  {
    if (list.size() != elementArray.length)
    {
      String message = MessageFormat.format("List size is not expected value (expected: {0}, actual: {1})", elementArray.length,
                                            list.size());
      fail(message);
    }
    else
    {
      // At this point we know the list and array are the same size, so it's safe to iterate like this
      for (int i = 0; i < list.size(); i++)
      {
        assertEquals(elementArray[i], list.get(i));
      }
    }
  }
}
