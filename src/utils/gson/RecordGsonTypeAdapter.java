package utils.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.sun.org.apache.regexp.internal.RE;
import pojo.Location;
import pojo.Record;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Created by androidjp on 2017/3/4.
 */
public class RecordGsonTypeAdapter extends TypeAdapter<Record>{

    @Override
    public void write(JsonWriter jsonWriter, Record record) throws IOException {
        try {
            Field[] fields = Record.class.getDeclaredFields();
            jsonWriter.beginObject();
            for (Field field : fields) {
                if (field.getName().equals("location")) {

                    Location location = (Location) field.get(record);
                    jsonWriter.beginObject();
                    Field[] fields2 = Location.class.getDeclaredFields();
                    for (Field field2 : fields2) {
                        jsonWriter.name(field2.getName()).value((String) field2.get(location));
                    }
                    jsonWriter.endObject();

                } else {
                    jsonWriter.name(field.getName()).value((String) field.get(record));
                }
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
        @Override
    public Record read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
