package fr.simplon.minorease.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateUserForm
{
    @NotBlank
    @Size(min=1,max=255)
    private String login;
    @NotBlank
    @Size(min=1,max=255)
    private String password;
    @NotBlank
    @Size(min=1,max=255)
    private String confirmPassword;

    public CreateUserForm()
    {
    }

    public CreateUserForm(String pLogin, String pPassword, String pConfirmPassword)
    {
        login = pLogin;
        password = pPassword;
        confirmPassword = pConfirmPassword;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String pLogin)
    {
        login = pLogin;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String pPassword)
    {
        password = pPassword;
    }

    public String getConfirmPassword()
    {
        return confirmPassword;
    }

    public void setConfirmPassword(String pConfirmPassword)
    {
        confirmPassword = pConfirmPassword;
    }
}