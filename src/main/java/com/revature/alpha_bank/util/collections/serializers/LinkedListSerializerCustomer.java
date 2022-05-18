package com.revature.alpha_bank.util.collections.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.revature.alpha_bank.models.Customer;

import java.io.IOException;
import java.util.LinkedList;

public class LinkedListSerializerCustomer extends StdSerializer<LinkedList> {

    public LinkedListSerializerCustomer() {
        this(null);
    }

    public LinkedListSerializerCustomer(Class<LinkedList> t) {
        super(t);
    }

    @Override
    public void serialize(LinkedList linkedList, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        for (int i = 0; i < linkedList.size(); i++) {
            Customer customer = (Customer) linkedList.get(i);
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("first_name", customer.getFname());
            jsonGenerator.writeStringField("last_name", customer.getLname());
            jsonGenerator.writeStringField("email", customer.getEmail());
            jsonGenerator.writeStringField("password", customer.getPassword());
            jsonGenerator.writeStringField("dob", customer.getDob());



            jsonGenerator.writeEndObject();
        }

    }
}
