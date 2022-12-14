package ch.hevs.androiddev_badmintoncourtreservation.database.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * This entity contains all the fields of the player.
 */
@Entity(tableName = "players",
        indices = {
                @Index(
                        value = {"id"}
                )}
)
public class PlayerEntity {
    @PrimaryKey(autoGenerate = true)
    private Long id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
