package tm;

public class SystemUserComboTM {
    private String role;

    public SystemUserComboTM() {
    }

    public SystemUserComboTM(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role ;
    }
}
