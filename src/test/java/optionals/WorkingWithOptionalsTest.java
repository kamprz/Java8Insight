package optionals;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;

public class WorkingWithOptionalsTest {


    @Test
    public void gettingValueFromOptionalByToString()
    {
        String name = "test";
        Optional<String> optional = Optional.of(name);
        assertEquals("Optional[test]", optional.toString());
    }

    @Test
    public void gettingValueFromOptionalByGet()
    {
        String name = "test";
        Optional<String> optional = Optional.of(name);
        assertEquals("test", optional.get());
    }

    @Test(expected = NullPointerException.class)
    public void throwingExceptionWhenCreatingOptionalOfNull()
    {
        Optional optional =  Optional.of(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void throwingExceptionWhenGetOnNull()
    {
        Optional optional =  Optional.ofNullable(null);
        optional.get();
    }

    @Test
    public void gettingValueFromEmptyOptionalByToString()
    {
        Optional optional = Optional.ofNullable(null);
        assertEquals("Optional.empty", optional.toString());
    }

    @Test
    public void whenCreatesEmptyOptionalThenIsPresentGivesFalse()
    {
        Optional<String> empty = Optional.empty();
        assertFalse(empty.isPresent());
    }

    @Test
    public void whenCreatesNonEmptyOptionalThenIsPresentGivesTrue()
    {
        Optional<String> optional = Optional.of("");
        assertTrue(optional.isPresent());
    }

    @Test
    public void usingIfPresentToDoSomeAction()
    {
        Optional<StringBuilder> optional = Optional.of(new StringBuilder("stringBuilder"));
        optional.ifPresent(str -> str.append(" appended"));
        assertEquals(optional.get().toString(),"stringBuilder appended");
    }

    @Test
    public void usingMapToDoSomeAction()
    {
        Optional<String> optional = Optional.of("6");
        Integer actual = optional.map(Integer::parseInt).get();
        Integer expected = 6;
        assertEquals(expected,actual);
    }

    @Test
    public void setDefaultWithOrElseGet()
    {
        String str1 = null;
        String name = Optional.ofNullable(str1).orElse("Kamil");
        assertEquals("Kamil", name);
    }

    private String checking()
    {
        return "default";
    }

    @Test
    public void orElseAndorElseGetWithNull()
    {
        String nullString = null;
        String expected = "default";
        String defaultString = Optional.ofNullable(nullString).orElse(checking());
        assertEquals(expected,defaultString);
        defaultString = Optional.ofNullable(nullString).orElseGet(this::checking);
        assertEquals(expected,defaultString);
    }

    private String throwException() throws RuntimeException
    {
        throw new RuntimeException("Exception thrown");
    }

    @Test(expected = RuntimeException.class)
    public void orElseInvokesMethodEvenWhenPresent()
    {
        String nullString = "not null";
        String defaultString = Optional.ofNullable(nullString).orElse(throwException());
    }

    @Test
    public void orElseInvokesMethodEvenWhenPresentButDoesntGetReturnedValue()
    {
        String notNull = "not null";
        String expected = "not null";
        String givenString = Optional.ofNullable(notNull).orElse(checking());
        assertEquals(expected,givenString);
    }
    @Test
    public void orElseGetDoesntInvokeMethodWhenPresent()
    {
        String defaultString = Optional.ofNullable("").orElseGet(this::throwException);
        assertTrue(true);
    }

    @Test(expected = RuntimeException.class)
    public void orElseThrow()
    {
        String nullString = null;
        Optional.ofNullable(nullString).orElseThrow(RuntimeException::new);
    }

    @Test
    public void usingPredicateInFilter()
    {
        assertTrue(Optional.of("test").filter(s -> s.equals("test")).isPresent());
        assertFalse(Optional.of("test").filter(s -> s.equals("otherValue")).isPresent());
    }

}
