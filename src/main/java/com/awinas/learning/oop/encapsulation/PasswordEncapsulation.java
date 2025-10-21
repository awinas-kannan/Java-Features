package com.awinas.learning.oop.encapsulation;
class User {
    private String password;

    // Setter encodes password internally
    public void setPassword(String password) {
        this.password = encode(password);
    }

    // Getter decodes password internally
    public String getPassword() {
        return decode(this.password);
    }

    private String encode(String pwd) {
        // simple example: reverse string
        return new StringBuilder(pwd).reverse().toString();
    }

    private String decode(String pwd) {
        return new StringBuilder(pwd).reverse().toString();
    }
}

public class PasswordEncapsulation {
    public static void main(String[] args) {
        User u = new User();
        u.setPassword("MySecret123");
        System.out.println(u.getPassword()); // Prints decoded password
    }
}
