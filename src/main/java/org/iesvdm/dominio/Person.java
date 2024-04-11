package org.iesvdm.dominio;

import java.util.Date;
import java.util.List;

public class Person {



    private String name;

    private String lastName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean readAccess(String role, int securityLevel, List<String> permissions) {

        //Una logica caulquiera, es lo de menos..
        if ("user".equals(role) && securityLevel > 3 && permissions.contains("r")) {

            return true;
        } else if ( "admin".equals(role) && permissions.contains("r") ) {

            return true;
        }

        return false;

    }

}
