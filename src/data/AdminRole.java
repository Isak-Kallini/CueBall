package data;

import net.dv8tion.jda.api.entities.Role;

import jakarta.persistence.*;

public class AdminRole {
    @Column(name = "role")
    private Role role;

    public AdminRole(){}


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
