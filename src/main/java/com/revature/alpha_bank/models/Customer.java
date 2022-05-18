package com.revature.alpha_bank.models;

// same as DBeaver
public class Customer {

    private String fname;
    private String lname;
    private String email;
    private String password;
    private String dob; // Think of changing it.






    public  Customer(String fname, String lname, String email, String password, String dob) {
        super();
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.dob = dob;

    }

    public Customer(String password){
        this.password = password;
    }

    public Customer() { }



    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }





    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String toFileString() {
        // StringBuilder, there is also a StringBuffer (it's thread-safe)
        // Is another class for Strings that allows them to be mutated
        StringBuilder mutableString = new StringBuilder();
        mutableString
                .append(fname).append(",")
                .append(lname).append(",")
                .append(password).append(",")
                .append(email).append(",")
                .append(dob);


        // Without changing the mutableString class from StringBuilder
        // we won't have an appropriate return type
        return mutableString.toString(); // We need the toString to return it to it's appropriate type
    }
    @Override
    public String toString() {
        return "Customer{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", email='" + email + '\'' +
                ", dob='" + dob + '\'' +
                '}';
    }
}







