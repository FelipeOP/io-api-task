package com.epam.rd.autocode.assessment.basics.service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CsvStorageImpl implements CsvStorage {

    private Map<String, String> props = new HashMap<>();

    public CsvStorageImpl() {
        props.put("encoding", "UTF-8");
        props.put("quoteCharacter", "\"");
        props.put("valuesDelimiter", ",");
        props.put("headerLine", "true");
    }

    /**
     * @param props configuration properties.<br>
     *              {@code String encoding} default: {@code 'UTF-8'} - The encoding
     *              to use when reading the CSV files; must be a valid charset.<br>
     *              {@code String quoteCharacter} default: {@code '"'} - The quote
     *              character to use for <em>quoted strings</em>. <br>
     *              {@code String valuesDelimiter} default: {@code ','} - The column
     *              delimiter character to use when reading the CSV file.<br>
     *              {@code boolean headerLine} default: {@code 'true'} - The first
     *              line considered as headers and should be ignored
     */
    public CsvStorageImpl(Map<String, String> props) {
        this.props.putAll(props);
    }

    private void setStringToObjectFeatures(String[] values) {
        String quoteCharacter = props.get("quoteCharacter");
        for (int i = 0; i < values.length; i++) {
            if (values[i] == null)
                continue;

            if (values[i].equals(String.format("%s%s", quoteCharacter, quoteCharacter))) {
                values[i] = "\"\"";
            } else if (values[i].length() > 2 &&
                    values[i].startsWith(quoteCharacter)
                    && values[i].endsWith(quoteCharacter)) {
                values[i] = values[i].substring(1, values[i].length() - 1);
            }
        }
    }

    @Override
    public <T> List<T> read(InputStream source, Function<String[], T> mapper) throws IOException {
        List<T> result = new ArrayList<>();
        String delimiter = props.get("valuesDelimiter");
        String quoteCharacter = props.get("quoteCharacter");
        String encoding = props.get("encoding");
        String regex = String.format("%s(?=(?:[^%s]*%s[^%s]*%s)*[^%s]*$)",
                delimiter, quoteCharacter, quoteCharacter, quoteCharacter, quoteCharacter, quoteCharacter);

        try (InputStreamReader reader = new InputStreamReader(source, encoding);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            if (Boolean.parseBoolean(props.get("headerLine"))) {
                bufferedReader.readLine();
            }
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(regex, -1);
                setStringToObjectFeatures(values);
                result.add(mapper.apply(values));
            }
        }
        return result;
    }

    private void setObjectToStringFeatures(String[] strings) {
        String delimiter = props.get("valuesDelimiter");
        String quoteCharacter = props.get("quoteCharacter");
        for (int i = 0; i < strings.length; i++) {
            if (strings[i] == null) {
                strings[i] = "";
            } else if (strings[i].isEmpty()) {
                strings[i] = String.format("%s%s", quoteCharacter, quoteCharacter);
            } else if (strings[i].contains(delimiter)) {
                strings[i] = String.format("%s%s%s",
                        quoteCharacter, strings[i], quoteCharacter);
            } else if (strings[i].equals("\"\"")) {
                strings[i] = String.format("%s%s", quoteCharacter, quoteCharacter);
            }
        }
    }

    @Override
    public <T> void write(OutputStream dest, List<T> values, Function<T, String[]> mapper) throws IOException {
        String delimiter = props.get("valuesDelimiter");
        String encoding = props.get("encoding");

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(dest, encoding))) {
            for (T value : values) {
                String[] strings = mapper.apply(value);
                setObjectToStringFeatures(strings);

                writer.write(String.join(delimiter, strings));
                writer.newLine();
            }
        }
    }

}
