package com.revature.alpha_bank.util.collections;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.revature.alpha_bank.models.Customer;

import java.io.IOException;

public class ArrayListSerializer extends StdSerializer<ArrayList> {

    public ArrayListSerializer() {
        this(null);
    }

    public ArrayListSerializer(Class<ArrayList> t) {
        super(t);
    }

    @Override
    public void serialize(ArrayList arrayList, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        for(int i = 0; i < arrayList.size; i++) {
            Customer customer = (Customer) arrayList.get(i);
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("fname", customer.getFname());
            jsonGenerator.writeStringField("lname", customer.getLname());
            jsonGenerator.writeStringField("email", customer.getEmail());
            jsonGenerator.writeStringField("password", customer.getPassword());
            jsonGenerator.writeStringField("dob", customer.getDob());



            jsonGenerator.writeEndObject();
        }

    }
}
