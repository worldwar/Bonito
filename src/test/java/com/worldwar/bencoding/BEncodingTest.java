package com.worldwar.bencoding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class BEncodingTest {

    private final String NORMORL_STRING = "worldwar";
    private final String BENCODING_NORMAL_STRING = "8:worldwar";
    private final String UTF8_STRING = "朱然";
    private final String BENCODING_UTF8_STRING = "6:朱然";
    private final int NORMAL_INTEGER = 45623;
    private final String BENCODING_NORMAL_INTEGER = "i45623e";
    private final List STRING_LIST = Lists.newArrayList("a", "ab", "abc");
    private final String BENCODING_STRING_LIST = "l1:a2:ab3:abce";
    private final List INTEGER_LIST = Lists.newArrayList(1, 23, 456, -78);
    private final String BENCODING_INTEGER_LIST = "li1ei23ei456ei-78ee";
    private final List MIXED_LIST = Lists.newArrayList(1, 23, "ab", 456, "cce");
    private final String BENCODING_MIXED_LIST = "li1ei23e2:abi456e3:ccee";
    private final List EMPTY_LIST = Lists.newArrayList();
    private final String BENCODING_EMPTY_LIST = "le";
    private final Map<String, String> STRING_MAP = new HashMap<>();
    private final String BENCODING_STRING_MAP = "d1:a2:ab1:b3:bcd1:c3:fffe";
    private final Map<String, Integer> INTEGER_MAP = new HashMap<>();
    private final String BENCODING_INTEGER_MAP = "d1:ai12e1:bi345e1:ci-67ee";
    private final Map<String, List> LIST_MAP = new HashMap<>();
    private final String BENCODING_LIST_MAP = "d1:ali12e3:abc2:99e1:bli45ei88ee1:cl3:abb3:cccee";
    private final String BENCODING_LONG_STRING = "22:I was born to love you";
    private final String LONG_STRING = "I was born to love you";
    private final String BENCODING_UTF8_ASCII_STRING = "12:朱然zhuran";
    private final String UTF8_ASCII_STRING = "朱然zhuran";
    private final String BENCODING_NEGATIVE_INTEGER = "i-123456e";
    private final int NEGATIVE_INTEGER = -123456;
    private final String BENCODING_ZERO = "i0e";
    private final int ZERO = 0;

    @Before
    public void before() {
        STRING_MAP.put("a", "ab");
        STRING_MAP.put("b", "bcd");
        STRING_MAP.put("c", "fff");

        INTEGER_MAP.put("a", 12);
        INTEGER_MAP.put("b", 345);
        INTEGER_MAP.put("c", -67);

        LIST_MAP.put("a", Lists.newArrayList(12, "abc", "99"));
        LIST_MAP.put("b", Lists.newArrayList(45, 88));
        LIST_MAP.put("c", Lists.newArrayList("abb", "ccc"));
    }

    @Test
    public void shouldEncodeString() {
        assertThat(BEncoding.encode(NORMORL_STRING), is(BENCODING_NORMAL_STRING));
    }
    @Test
    public void shouldEncodeStringEncodedInUTF8() {
        assertThat(BEncoding.encode(UTF8_STRING), is(BENCODING_UTF8_STRING));
    }
    @Test
    public void shouldEncodeInteger() {
        assertThat(BEncoding.encode(NORMAL_INTEGER), is(BENCODING_NORMAL_INTEGER));
    }
    @Test
    public void shouldEncodeListOfStrings() {
        assertThat(BEncoding.encode(STRING_LIST), is(BENCODING_STRING_LIST));
    }
    @Test
    public void shouldEncodeListOfIntegers() {
        assertThat(BEncoding.encode(INTEGER_LIST), is(BENCODING_INTEGER_LIST));
    }
    @Test
    public void shouldEncodeListOfIntegersAndStrings() {
        assertThat(BEncoding.encode(MIXED_LIST), is(BENCODING_MIXED_LIST));
    }
    @Test
    public void shouldEncodeEmptyList() {
        assertThat(BEncoding.encode(EMPTY_LIST), is(BENCODING_EMPTY_LIST));
    }
    @Test
    public void shouldEncodeDictionaryOfStrings() {
        assertThat(BEncoding.encode(STRING_MAP), is(BENCODING_STRING_MAP));
    }
    @Test
    public void shouldEncodeDictionaryOfIntegers() {
        assertThat(BEncoding.encode(INTEGER_MAP), is(BENCODING_INTEGER_MAP));
    }
    @Test
    public void shouldEncodeDictionaryOfLists() {
        assertThat(BEncoding.encode(LIST_MAP), is(BENCODING_LIST_MAP));
    }
    @Test
    public void shouldDecodeString() {
        assertThat(BEncoding.decode(BENCODING_NORMAL_STRING).getValue(), is(NORMORL_STRING));
    }
    @Test
    public void shouldDecodeStringOfLengthGreaterThanTen() {
        assertThat(BEncoding.decode(BENCODING_LONG_STRING).getValue(), is(LONG_STRING));
    }
    @Test
    public void shouldDecodeStringEncodedInUTF8() {
        assertThat(BEncoding.decode(BENCODING_UTF8_STRING).getValue(), is(UTF8_STRING));
    }
    @Test
    public void shouldDecodeStringEncodedInUTF8AndASCII() {
        assertThat(BEncoding.decode(BENCODING_UTF8_ASCII_STRING).getValue(), is(UTF8_ASCII_STRING));
    }
    @Test
    public void shouldDecodeInteger() {
        assertThat(BEncoding.decode(BENCODING_NORMAL_INTEGER).getValue(), is(NORMAL_INTEGER));
    }
    @Test
    public void shouldDecodeNegativeInteger() {
        assertThat(BEncoding.decode(BENCODING_NEGATIVE_INTEGER).getValue(), is(NEGATIVE_INTEGER));
    }
    @Test
    public void shouldDecodeZero() {
        assertThat(BEncoding.decode(BENCODING_ZERO).getValue(), is(ZERO));
    }
    @Test
    public void shouldDecodeListOfStrings() {
        List<BObject> value = (List<BObject>)BEncoding.decode(BENCODING_STRING_LIST).getValue();
        assertThat(value.get(0).getValue(), is(STRING_LIST.get(0)));
        assertThat(value.get(1).getValue(), is(STRING_LIST.get(1)));
        assertThat(value.get(2).getValue(), is(STRING_LIST.get(2)));
        assertThat(value.size(), is(STRING_LIST.size()));
    }
    @Test
    public void shouldDecodeListOfIntegers() {
        List<BObject> value = (List<BObject>)BEncoding.decode(BENCODING_INTEGER_LIST).getValue();
        assertThat(value.get(0).getValue(), is(INTEGER_LIST.get(0)));
        assertThat(value.get(1).getValue(), is(INTEGER_LIST.get(1)));
        assertThat(value.get(2).getValue(), is(INTEGER_LIST.get(2)));
        assertThat(value.size(), is(INTEGER_LIST.size()));
    }
    @Test
    public void shouldDecodeMixedList() {
        List<BObject> value = (List<BObject>)BEncoding.decode(BENCODING_MIXED_LIST).getValue();
        assertThat(value.get(0).getValue(), is(MIXED_LIST.get(0)));
        assertThat(value.get(1).getValue(), is(MIXED_LIST.get(1)));
        assertThat(value.get(2).getValue(), is(MIXED_LIST.get(2)));
        assertThat(value.get(3).getValue(), is(MIXED_LIST.get(3)));
        assertThat(value.get(4).getValue(), is(MIXED_LIST.get(4)));
        assertThat(value.size(), is(MIXED_LIST.size()));
    }
    @Test
    public void shouldDecodeDictionaryOfStrings() {
        BObject object = BEncoding.decode(BENCODING_STRING_MAP);
        Map<BObject, BObject> value = (Map<BObject, BObject>)object.getValue();
        for (Map.Entry<BObject, BObject> element : value.entrySet()) {
            assertThat(element.getValue().getValue(), is(STRING_MAP.get(element.getKey().getValue())));
        }
        assertThat(value.size(), is(STRING_MAP.size()));
    }
    @Test
    public void shouldDecodeDictionaryOfIntegers() {
        BObject object = BEncoding.decode(BENCODING_INTEGER_MAP);
        Map<BObject, BObject> value = (Map<BObject, BObject>)object.getValue();
        for (Map.Entry<BObject, BObject> element : value.entrySet()) {
            assertThat(element.getValue().getValue(), is(INTEGER_MAP.get(element.getKey().getValue())));
        }
        assertThat(value.size(), is(INTEGER_MAP.size()));
    }
    @Test
    public void shouldEncodeBObjectOfSringType() {
        BObject object = BEncoding.decode(BENCODING_UTF8_ASCII_STRING);
        assertThat(BEncoding.encode(object), is(BENCODING_UTF8_ASCII_STRING));
    }
    @Test
    public void shouldEncodeBObjectOfIntegerType() {
        BObject object = BEncoding.decode(BENCODING_NEGATIVE_INTEGER);
        assertThat(BEncoding.encode(object), is(BENCODING_NEGATIVE_INTEGER));
    }
    @Test
    public void shouldEncodeBObjectOfListType() {
        BObject object = BEncoding.decode(BENCODING_MIXED_LIST);
        assertThat(BEncoding.encode(object), is(BENCODING_MIXED_LIST));
    }
    @Test
    public void shouldEncodeBObjectOfDictionaryType() {
        BObject object = BEncoding.decode(BENCODING_LIST_MAP);
        String encode = BEncoding.encode(object);
        BObject anotherObject = BEncoding.decode(encode);
        assertThat(object, is(anotherObject));
    }
    @Test
    public void encodingOfDictionaryShouldSortedByKeys() {
        Map<String, String> map = new HashMap<>();
        map.put("b", "bcd");
        map.put("a", "ab");
        map.put("c", "fff");
        assertThat(BEncoding.encode(map), is(BENCODING_STRING_MAP));

        map.clear();

        map.put("c", "fff");
        map.put("b", "bcd");
        map.put("a", "ab");
        assertThat(BEncoding.encode(map), is(BENCODING_STRING_MAP));
    }
}