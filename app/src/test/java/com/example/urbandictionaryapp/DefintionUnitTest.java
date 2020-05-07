package com.example.urbandictionaryapp;

import com.example.urbandictionaryapp.model.Definition;
import com.example.urbandictionaryapp.util.SortingUtil;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DefintionUnitTest {

    @Mock
    Definition definition_a;

    @Mock
    Definition definition_b;

    @Mock
    Definition definition_c;

    @Mock
    List<Definition> definitionList;

    @Before
    public void setUP(){

        MockitoAnnotations.initMocks(this);

        Mockito.when(definition_a.getThumbsUp()).thenReturn(40);
        Mockito.when(definition_b.getThumbsUp()).thenReturn(100);
        Mockito.when(definition_c.getThumbsUp()).thenReturn(70);

        Mockito.when(definition_a.getThumbsDown()).thenReturn(40);
        Mockito.when(definition_b.getThumbsDown()).thenReturn(70);
        Mockito.when(definition_c.getThumbsDown()).thenReturn(100);

        definitionList = Arrays.asList(definition_a, definition_b, definition_c);

    }


    @Test
    public void testForSortThumbsUp(){

        assertEquals(SortingUtil.Companion.sortByThumbsUp(definitionList).get(0), definition_b);
        assertEquals(SortingUtil.Companion.sortByThumbsUp(definitionList).get(1), definition_c);
        assertEquals(SortingUtil.Companion.sortByThumbsUp(definitionList).get(2), definition_a);
        assertNotEquals(SortingUtil.Companion.sortByThumbsUp(definitionList).get(0), definition_a);

    }

    @Test
    public void testForSortThumbsDown(){
        assertEquals(SortingUtil.Companion.sortByThumbsDown(definitionList).get(0), definition_c);
        assertEquals(SortingUtil.Companion.sortByThumbsDown(definitionList).get(1), definition_b);
        assertNotEquals(SortingUtil.Companion.sortByThumbsDown(definitionList).get(0), definition_a);
    }
}
