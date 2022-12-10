package ch.brouchoud.androiddev_badmintoncourtreservation.database.entity;

import androidx.room.Ignore;

import com.google.firebase.firestore.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * This entity contains all the fields of the player.
 */

public class PlayerEntity {

    private String id;
    private String firstname;
    private String lastname;
    private String birthdate;
    private String gender;
    private String phone;
    private String address;

    @Ignore
    public PlayerEntity() {
    }

    public PlayerEntity(String firstname, String lastname, String birthdate, String gender, String phone, String address) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof PlayerEntity)) return false;
        PlayerEntity o = (PlayerEntity) obj;
        return o.getId().equals(this.getId());
    }

    @Override
    public String toString() {
        return firstname + " " + lastname;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("firstname", firstname);
        result.put("lastname", lastname);
        result.put("birthdate", birthdate);
        result.put("gender", gender);
        result.put("phone", phone);
        result.put("address", address);

        return result;
    }
}
