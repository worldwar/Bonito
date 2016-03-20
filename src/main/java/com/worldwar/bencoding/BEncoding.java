package com.worldwar.bencoding;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import static com.worldwar.bencoding.BType.DICTIONARY;
import static com.worldwar.bencoding.BType.INTEGER;
import static com.worldwar.bencoding.BType.LIST;
import static com.worldwar.bencoding.BType.STRING;

public class BEncoding {
    private static final String INTEGER_PREFIX = "i";
    private static final String LIST_PREFIX = "l";
    private static final String DICTIONARY_PREFIX = "d";
    private static final String SUFFIX = "e";
    private static final String DELIMITER = ":";

    private static BEncoder choose(Object source) {
        if (source instanceof String) {
            return new BStringEncoder();
        } else if (source instanceof Integer) {
            return new BIntegerEncoder();
        } else if (source instanceof List) {
            return new BListEncoder();
        } else if (source instanceof Map) {
            return new BDictionaryEncoder();
        } else if (source instanceof BObject) {
            return new BObjectEncoder();
        } else if (source instanceof Long) {
            return new BLongEncoder();
        }
        else {
            return null;
        }
    }

    private static BDecoder decoder(char c) {
        switch (String.valueOf(c)) {
            case INTEGER_PREFIX: return new BIntegerDecoder();
            case LIST_PREFIX: return new BListDecoder();
            case DICTIONARY_PREFIX: return new BDictionaryDecoder();
            default: return new BStringDecoder(c);
        }
    }

    public static String encode(Object source) {
        BEncoder encoder = choose(source);
        if (encoder == null) {
            throw new RuntimeException("UNSUPPORTED DATA TYPE: " + source);
        }
        return encoder.encode(source);
    }

    public static BObject read(String filename) {
        try (InputStream stream = new FileInputStream(filename)){
            return decode(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BObject decode(String source) {
        InputStream stream = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
        return decode(stream);
    }

    public static BObject decode(InputStream stream) {
        try {
            int flag = stream.read();
            BDecoder decoder = decoder((char) flag);
            return decoder.decode(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void write(OutputStream outputStream, BObject source) {
        String target = encode(source);
        try {
            outputStream.write(target.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(String filename, BObject source) {
        try (FileOutputStream stream = new FileOutputStream(filename)) {
            write(stream, source);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    interface BEncoder<T> {
        String encode(T source);
    }

    static class BStringEncoder implements BEncoder<String> {
        @Override
        public String encode(String source) {
            String standard = Strings.nullToEmpty(source);
            byte[] bytes = standard.getBytes(StandardCharsets.UTF_8);
            int length = bytes.length;
            return length + DELIMITER + standard;
        }
    }

    static class BIntegerEncoder implements BEncoder<Integer> {
        @Override
        public String encode(Integer source) {
            return INTEGER_PREFIX + String.valueOf(source) + SUFFIX;
        }
    }

    static class BLongEncoder implements BEncoder<Long> {
        @Override
        public String encode(Long source) {
            return INTEGER_PREFIX + String.valueOf(source) + SUFFIX;
        }
    }

    static class BListEncoder implements BEncoder<List<Object>> {
        @Override
        public String encode(List<Object> source) {
            return  LIST_PREFIX + Joiner.on("").join(Iterables.transform(source, BEncoding::encode)) + SUFFIX;
        }
    }

    static class BDictionaryEncoder implements BEncoder<Map<Object, Object>> {
        @Override
        public String encode(Map<Object, Object> source) {
            List<Map.Entry<Object, Object>> entries = new ArrayList<>();
            entries.addAll(source.entrySet());
            Collections.sort(entries, new Comparator<Map.Entry<Object, Object>>() {
                @Override
                public int compare(Map.Entry<Object, Object> o1, Map.Entry<Object, Object> o2) {
                    if (o1.getKey() instanceof String) {
                        String k1 = (String)o1.getKey();
                        String k2 = (String)o2.getKey();
                        return k1.compareTo(k2);
                    } else if (o1.getKey() instanceof BObject) {
                        return ((BObject)o1.getKey()).compareTo((BObject)o2.getKey());
                    }
                    return 0;
                }
            });
            FluentIterable f = FluentIterable.from(entries).filter(new Predicate<Map.Entry<Object, Object>>() {
                @Override
                public boolean apply(Map.Entry<Object, Object> input) {
                    return input.getValue() != null;
                }
            }).transform(this::encode);
            ImmutableList transform = f.toList();
            return DICTIONARY_PREFIX + Joiner.on("").join(transform) + SUFFIX;
        }

        String encode(Map.Entry<Object, Object> element) {
            return BEncoding.encode(element.getKey()) + BEncoding.encode(element.getValue());
        }
    }

    static class BObjectEncoder implements BEncoder<BObject> {
        @Override
        public String encode(BObject source) {
            BEncoder encoder = choose(source.getType());
            return encoder.encode(source.getValue());
        }

        private BEncoder choose(BType type) {
            switch (type) {
                case STRING: return new BStringEncoder();
                case INTEGER: return new BIntegerEncoder();
                case LIST: return new BListEncoder();
                case DICTIONARY:return new BDictionaryEncoder();
            }
            return null;
        }
    }

    static boolean isDigital(int c) {
        return c >= '0' && c <= '9';
    }

    interface BDecoder {
        BObject decode(InputStream stream);
    }

    static class BStringDecoder implements BDecoder {
        int flag;

        BStringDecoder(int flag) {
            this.flag = flag;
        }

        @Override
        public BObject decode(InputStream stream) {
            try {
                if (!isDigital(flag)) {
                    throw new BadBEncodingException();
                }
                int length = flag - '0';
                int n;
                while ((n = stream.read()) != ':') {
                    if (!isDigital(n)) {
                        throw new BadBEncodingException();
                    }
                    length = length * 10 + (n - '0');
                }
                byte[] value = new byte[length];
                int read = stream.read(value, 0, length);
                if (read == length) {
                    BObject result = new BObject();
                    result.setType(STRING);
                    result.setValue(new String(value, StandardCharsets.UTF_8));
                    return result;
                } else {
                    throw new BadBEncodingException();
                }
            } catch (IOException | BadBEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    static class BIntegerDecoder implements BDecoder {
        @Override
        public BObject decode(InputStream stream) {
            try {
                int value = 0;
                boolean negative = false;
                int c;
                while ((c = stream.read()) != 'e') {
                    if (c == '-') {
                        negative = true;
                    } else if (isDigital(c)) {
                        value = value * 10 + c - '0';
                    } else {
                        throw new BadBEncodingException();
                    }
                }
                if (negative) {
                    value = -value;
                }
                BObject result = new BObject();
                result.setType(INTEGER);
                result.setValue(value);
                return result;
            } catch (IOException | BadBEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    static class BListDecoder implements BDecoder {
        @Override
        public BObject decode(InputStream stream) {
            List<BObject> value = new ArrayList<>();
            try {
                int c;
                while ((c = stream.read()) != 'e') {
                    BDecoder decoder = decoder((char)c);
                    if (decoder != null) {
                        value.add(decoder.decode(stream));
                    } else {
                        throw new BadBEncodingException();
                    }
                }
                BObject result = new BObject();
                result.setType(LIST);
                result.setValue(value);
                return result;
            } catch (IOException | BadBEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    static class BDictionaryDecoder implements BDecoder {
        @Override
        public BObject decode(InputStream stream) {
            Map<BObject, BObject> map = new HashMap<>();
            try {
                int c;
                BObject key, value;
                while ((c = stream.read()) != 'e') {
                    key = decoder((char)c).decode(stream);
                    c = stream.read();
                    value = decoder((char)c).decode(stream);
                    map.put(key, value);
                }
                BObject result = new BObject();
                result.setType(DICTIONARY);
                result.setValue(map);
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static Object javaObject(BObject bObject) {
        switch (bObject.getType()) {
            case INTEGER:
            case STRING:
                return bObject.getValue();
            case LIST:
                List<Object> v = new LinkedList<>();
                for (BObject element : (List<BObject>)bObject.getValue()) {
                    v.add(javaObject(element));
                }
                return v;
            case DICTIONARY:
                Map<Object, Object> map = new HashMap<>();
                for (Map.Entry<BObject, BObject> element : ((Map<BObject, BObject>) bObject.getValue()).entrySet()) {
                    map.put(javaObject(element.getKey()), javaObject(element.getValue()));
                }
                return map;
        }
        return null;
    }
}
