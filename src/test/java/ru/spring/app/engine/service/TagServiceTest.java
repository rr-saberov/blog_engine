package ru.spring.app.engine.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.spring.app.engine.api.response.SingleTagResponse;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@SpringBootTest
@DisplayName("Testing the tag service: ")
class TagServiceTest {

    List<SingleTagResponse> expectedList;
    final TagService tagService;

    @Autowired
    TagServiceTest(TagService tagService) {
        this.tagService = tagService;
    }

    @BeforeEach
    void setUp() {
        expectedList = new ArrayList<>();
        expectedList.add(new SingleTagResponse("tag name", 1.0));
    }

    @AfterEach
    void tearDown() {
        expectedList = null;
    }

    @Test
    @DisplayName("get tags method")
    void getTags() {
        assertArrayEquals(expectedList.toArray(), tagService.getTags("tag").getTags().toArray());
    }
}