package tm;

public class SystemUsersTM {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String nic;
    private String role;

    public SystemUsersTM() {
    }

    public SystemUsersTM(String id, String name, String email, String phone, String nic, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.nic = nic;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
